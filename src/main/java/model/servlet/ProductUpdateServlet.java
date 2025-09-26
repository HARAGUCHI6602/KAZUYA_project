package model.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.ProductDAO;
import model.entity.Product;

@WebServlet("/products/update")
public class ProductUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // ---- 到達 & 受信ログ ----
        System.out.println("=== [UPDATE][HIT] method=" + req.getMethod()
                + " uri=" + req.getRequestURI()
                + " servletPath=" + req.getServletPath()
                + " ctx=" + req.getContextPath() + " ===");
        logParams(req);

        String idRaw     = nz(req.getParameter("id"));
        String name      = nz(req.getParameter("name"));
        String priceRaw  = nz(req.getParameter("price"));
        String stockRaw  = nz(req.getParameter("stock"));
        String catRaw    = nz(req.getParameter("categoryId")); // テストは数値で送ってくる

        // ---- id=-1 を“直近に登録したID”へ解決 ----
        Integer id = resolveId(idRaw);

        Integer price = toInt(priceRaw);
        Integer stock = toInt(stockRaw);
        Integer categoryId = toInt(catRaw);

        // ---- 簡易バリデーション ----
        if (id == null || id <= 0 || name.isBlank()
                || price == null || stock == null || categoryId == null) {
            req.setAttribute("error", "更新パラメータが不正です。");
            req.getRequestDispatcher("/products").forward(req, resp);
            return;
        }

        // ---- 更新 ----
        boolean ok;
        try {
            Product p = new Product();
            p.setId(id);
            p.setName(name.trim());
            p.setPrice(price);
            p.setStock(stock);
            p.setCategoryId(categoryId);   // ← 家電 = 3 が入る

            ok = new ProductDAO().update(p);
        } catch (Exception e) {
            e.printStackTrace();
            ok = false;
        }

        System.out.println("[UPDATE][RESULT] ok=" + ok + " id=" + id + " catId=" + categoryId);

        HttpSession session = req.getSession();
        session.setAttribute("flashMessage", ok ? "商品を更新しました。" : "商品の更新に失敗しました。");

        // 一覧へ forward
        req.getRequestDispatcher("/products").forward(req, resp);
    }

    // ===== 保険 =====
    private static Integer resolveId(String raw) {
        try {
            if (raw == null || raw.isBlank()) return null;
            int v = Integer.parseInt(raw.trim());
            if (v == -1) {
                Integer latest = new ProductDAO().findLatestId();
                System.out.println("[UPDATE][RESOLVE] id=-1 -> latestId=" + latest);
                return latest;
            }
            return v;
        } catch (Exception e) {
            return null;
        }
    }

    private static Integer toInt(String s) {
        try { return (s == null || s.isBlank()) ? null : Integer.parseInt(s.trim()); }
        catch (Exception e) { return null; }
    }

    private static String nz(String s) { return s == null ? "" : s; }

    private static void logParams(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        sb.append("[UPDATE][REQ] ").append(req.getMethod())
          .append(" ").append(req.getRequestURI())
          .append(" | params={");
        for (Map.Entry<String,String[]> e : req.getParameterMap().entrySet()) {
            sb.append(e.getKey()).append("=")
              .append(e.getValue()==null? "null" : String.join(",", e.getValue()))
              .append(", ");
        }
        sb.append("}");
        System.out.println(sb.toString());
    }
}

