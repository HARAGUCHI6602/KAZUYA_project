package model.servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import model.dao.ProductDAO;
import model.entity.Product;

/**
 * 商品の更新を受け付けるサーブレット（編集フォームの保存ボタン）
 * POST /products/update
 * バリデーションに失敗したら編集フォームへ戻し、成功したら一覧にPRGでリダイレクト
 */
@WebServlet("/products/update")
public class ProductUpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 文字化け対策（POST）
        req.setCharacterEncoding("UTF-8");

        // --- 1) 入力値の取得 ---
        String idStr       = req.getParameter("id");
        String name        = req.getParameter("name");
        String priceStr    = req.getParameter("price");
        String stockStr    = req.getParameter("stock");
        String categoryStr = req.getParameter("categoryId");

        Map<String, String> errors = new LinkedHashMap<>();

        // --- 2) 型変換 & バリデーション ---
        // id
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
            if (id <= 0) {
                errors.put("id", "不正なリクエストです。");
            }
        } catch (Exception e) {
            errors.put("id", "不正なリクエストです。");
        }

        // name
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "商品名を入力してください。");
        } else {
            name = name.trim();
        }

        // price
        int price = 0;
        try {
            price = Integer.parseInt(priceStr);
            if (price < 0) {
                errors.put("price", "価格は0以上の整数で入力してください。");
            }
        } catch (Exception e) {
            errors.put("price", "価格は数字で入力してください。");
        }

        // stock
        int stock = 0;
        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                errors.put("stock", "在庫は0以上の整数で入力してください。");
            }
        } catch (Exception e) {
            errors.put("stock", "在庫は数字で入力してください。");
        }

        // category（※ あなたのエンティティが int 型なので、カテゴリは必須として扱います）
        int categoryId = 0;
        try {
            categoryId = Integer.parseInt(categoryStr);
            if (categoryId <= 0) {
                errors.put("categoryId", "カテゴリを選択してください。");
            } else {
                // 実在チェック
                CategoryDAO cDao = new CategoryDAO();
                if (!cDao.exists(categoryId)) {
                    errors.put("categoryId", "存在しないカテゴリが指定されました。");
                }
            }
        } catch (Exception e) {
            errors.put("categoryId", "カテゴリを正しく選択してください。");
        }

        // 画面に差し戻す用の一時オブジェクト（カテゴリ名はフォームで使わないので null）
        Product formValue = new Product(id, name, price, stock, categoryId, null);

        // --- 3) エラーがあれば編集フォームへ戻す（forward） ---
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("product", formValue);
            req.setAttribute("categories", new CategoryDAO().findAll());
            req.getRequestDispatcher("/WEB-INF/product-edit.jsp").forward(req, resp);
            return;
        }

        // --- 4) 更新実行 ---
        boolean ok = new ProductDAO().update(formValue);

        if (ok) {
            // PRG（Post/Redirect/Get）で一覧へ。クエリでフラッシュ表示を指示
            resp.sendRedirect(req.getContextPath() + "/products?updated=1");
        } else {
            // 失敗時はフォームに戻し、エラーを出す
            errors.put("general", "更新に失敗しました。時間をおいて再度お試しください。");
            req.setAttribute("errors", errors);
            req.setAttribute("product", formValue);
            req.setAttribute("categories", new CategoryDAO().findAll());
            req.getRequestDispatcher("/WEB-INF/product-edit.jsp").forward(req, resp);
        }
    }

    // 誤ってGETした場合は一覧へ
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/products");
    }
}
