package model.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.ProductDAO;
import model.entity.Product;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // 1) 一覧データ取得
            List<Product> products = new ProductDAO().findAllWithCategory();
            req.setAttribute("products", products);

            // 2) クエリパラメータ(success/updated/deleted/error) からフラッシュをセット
            setFlashFromQuery(req);

            // 3) セッションにあるフラッシュメッセージを request に移動（1回表示）
            moveFlashToRequest(req.getSession(), req);

            // 4) 表示
            req.getRequestDispatcher("/WEB-INF/product-list.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "商品一覧の取得でエラーが発生しました：" + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }

    // 一覧表示に統一（POSTもGETへ）
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    // ------ ここからユーティリティ ------

    /** クエリ (success/updated/deleted/error) に応じてフラッシュメッセージをセッションに積む */
    private void setFlashFromQuery(HttpServletRequest req) {
        if ("1".equals(req.getParameter("success"))) {
            setFlash(req.getSession(), "商品を登録しました。");
        }
        if ("1".equals(req.getParameter("updated"))) {
            setFlash(req.getSession(), "商品を更新しました。");
        }
        if ("1".equals(req.getParameter("deleted"))) {
            setFlash(req.getSession(), "商品を削除しました。");
        }
        String err = req.getParameter("error");
        if (err != null && !err.isBlank()) {
            setFlash(req.getSession(), err);
        }
    }

    /** セッションにフラッシュメッセージをセット（次のリクエストで1回だけ表示） */
    private void setFlash(HttpSession session, String message) {
        session.setAttribute("flashMessage", message);
    }

    /** セッションのフラッシュメッセージを request へ移し、セッションから削除する */
    private void moveFlashToRequest(HttpSession session, HttpServletRequest req) {
        Object msg = session.getAttribute("flashMessage");
        if (msg != null) {
            req.setAttribute("flashMessage", msg);
            session.removeAttribute("flashMessage");
        }
    }
}
