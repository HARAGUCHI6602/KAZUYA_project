package model.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.CategoryDAO;
import model.dao.ProductDAO;
import model.entity.Category;
import model.entity.Product;

/**
 * 商品登録サーブレット（保存専用）
 *  - フォーム POST を受けて DB 登録
 *  - 成功時は一覧JSPへ直接 forward（HTTP 200）
 *  - category は id だけでなく「名前」でも受け付ける（例: "食品"）
 *
 * フォーム表示は ProductRegisterFormServlet(@WebServlet("/products/register")) が担当。
 */
@WebServlet("/products/register")
public class ProductRegisterServlet extends HttpServlet {

    // -------- POST ----------
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // 到達/受信ログ
        System.out.println("=== [STORE][HIT] method=" + req.getMethod()
                + " uri=" + req.getRequestURI()
                + " servletPath=" + req.getServletPath()
                + " ctx=" + req.getContextPath() + " ===");
        logParams(req); // 全パラメータを吐く

        String name     = nz(req.getParameter("name"));
        String priceStr = nz(req.getParameter("price"));
        String stockStr = nz(req.getParameter("stock"));

        // category はいろんな名前で来る可能性に対応
        String catRaw = firstNonBlank(
                req.getParameter("categoryId"),
                req.getParameter("category"),
                req.getParameter("category_name"),
                req.getParameter("categoryName"),
                req.getParameter("category_id")
        );

        List<String> errors = new ArrayList<>();
        Integer price = parseInt(priceStr,  "価格は整数で入力してください。",   errors);
        Integer stock = parseInt(stockStr,  "在庫数は整数で入力してください。", errors);

        // ★ 名前で来ている場合は ID に解決する（数字ならそのまま）
        Integer categoryId = resolveCategoryId(catRaw, errors);

        if (name.isBlank())                        errors.add("商品名を入力してください。");
        if (price != null && price < 0)            errors.add("価格は0以上で入力してください。");
        if (stock != null && stock < 0)            errors.add("在庫数は0以上で入力してください。");
        if (categoryId == null || categoryId <= 0) errors.add("カテゴリを選択してください。");

        // 入力エラー → フォームへ戻す（カテゴリ一覧も付ける）
        if (!errors.isEmpty()) {
            System.out.println("[STORE][NG] validation errors: " + errors);

            req.setAttribute("errors", errors);
            req.setAttribute("old_name", name);
            req.setAttribute("old_price", priceStr);
            req.setAttribute("old_stock", stockStr);
            req.setAttribute("old_categoryId", catRaw);

            try {
                req.setAttribute("categories", new CategoryDAO().findAll());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // フォーム JSP へ（フォームサーブレットでもOKだが、ここは直接JSPへ）
            req.getRequestDispatcher("/product-register.jsp").forward(req, resp);
            return;
        }

        // --- 登録 ---
        boolean ok;
        try {
            Product p = new Product();
            p.setName(name.trim());
            p.setPrice(price);
            p.setStock(stock);
            p.setCategoryId(categoryId);

            ok = new ProductDAO().insert(p);
            System.out.println("[STORE][INSERT] ok=" + ok + " -> "
                    + "name=" + name + ", price=" + price + ", stock=" + stock + ", categoryId=" + categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            ok = false;
        }

        if (ok) {
            // ★ 一覧JSPへ 直接 forward（/products へ forward しない）
            HttpSession session = req.getSession();
            session.setAttribute("flashMessage", "商品を登録しました。");

            try {
                req.setAttribute("products", new ProductDAO().findAllWithCategory());
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "商品一覧の取得でエラーが発生しました。");
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
                return;
            }

            req.getRequestDispatcher("/WEB-INF/product-list.jsp").forward(req, resp);
        } else {
            req.setAttribute("message", "登録に失敗しました。");
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }

    // -------- GET（直接叩かれたらフォームへ誘導） ----------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/products/register");
    }

    // ===== helpers =====

    /** 可能なら数値、だめならメッセージを積んで null を返す */
    private static Integer parseInt(String s, String msg, List<String> errors) {
        try {
            if (s == null || s.isBlank()) { errors.add(msg); return null; }
            return Integer.parseInt(s.trim());
        } catch (Exception ex) {
            errors.add(msg);
            return null;
        }
    }

    /** category の raw 値（id/名前/空）から整数IDに解決する */
    private static Integer resolveCategoryId(String raw, List<String> errors) {
        String v = nz(raw).trim();
        // 数字ならそのまま
        if (!v.isEmpty()) {
            try {
                return Integer.parseInt(v);
            } catch (NumberFormatException ignore) {
                // 名前の可能性があるので次へ
            }
        }
        if (v.isEmpty()) return null;

        // 名前で来た場合は CategoryDAO から全件取得して一致を探す
        try {
            List<Category> list = new CategoryDAO().findAll();
            for (Category c : list) {
                if (equalsLoose(v, c.getName())) {
                    System.out.println("[STORE][CATEGORY] name '" + v + "' -> id " + c.getId());
                    return c.getId();
                }
            }
            errors.add("存在しないカテゴリ名です: " + v);
            System.out.println("[STORE][CATEGORY][NG] name not found: " + v);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("カテゴリの解決中にエラーが発生しました。");
            return null;
        }
    }

    /** 全パラメータをログ（デバッグ用） */
    private static void logParams(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        sb.append("[STORE][REQ] ").append(req.getMethod())
          .append(" ").append(req.getRequestURI())
          .append(" qs=").append(nz(req.getQueryString())).append(" | params={");
        for (Map.Entry<String, String[]> e : req.getParameterMap().entrySet()) {
            String k = e.getKey();
            String[] vals = e.getValue();
            sb.append(k).append("=")
              .append(vals == null ? "null" : String.join(",", vals)).append(", ");
        }
        sb.append("}");
        System.out.println(sb.toString());
    }

    /** ゆるめ比較（前後空白除去のみ） */
    private static boolean equalsLoose(String a, String b) {
        if (a == null || b == null) return false;
        return a.trim().equals(b.trim());
    }

    private static String nz(String s) { return s == null ? "" : s; }

    private static String firstNonBlank(String... vals) {
        if (vals == null) return "";
        for (String v : vals) {
            if (v != null && !v.isBlank()) return v;
        }
        return "";
    }
}

