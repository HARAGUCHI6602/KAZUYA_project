package model.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import model.dao.ProductDAO;
import model.entity.Product;

/**
 * 商品編集フォームを表示するサーブレット
 * GET /products/edit?id={productId}
 * - 指定IDの商品を取得し、カテゴリ一覧と一緒に編集フォームJSPへ
 * - 見つからない/ID不正なら一覧にリダイレクト
 */
@WebServlet("/products/edit")
public class ProductEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1) パラメータ id を取得＆チェック
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            // id なし → 一覧へ
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            // 数字でない → 一覧へ
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        // 2) 指定商品の取得
        ProductDAO pDao = new ProductDAO();
        Product product = pDao.findById(id); // カテゴリ名なしでOK（フォームに現在値を表示する用途）
        if (product == null) {
            // 指定IDが存在しない → 一覧へ
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        // 3) カテゴリ一覧の取得（ドロップダウン表示用）
        CategoryDAO cDao = new CategoryDAO();
        req.setAttribute("product", product);
        req.setAttribute("categories", cDao.findAll());

        // 4) 編集フォームへ forward
        req.getRequestDispatcher("/WEB-INF/product-edit.jsp").forward(req, resp);
    }

    // POSTで来たらGETに流す（誤送信対策）
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
