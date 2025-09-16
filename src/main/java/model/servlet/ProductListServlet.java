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
            // 1) 一覧取得
            List<Product> products = new ProductDAO().findAllWithCategory();
            req.setAttribute("products", products);

            // 2) クエリパラメータからフラッシュメッセージ設定（登録/更新/削除 成功など）
            setFlashFromQuery(req);

            // 3) 直前のフラッシュメッセージがあれば request に移してからセッションから消す
            moveFlashToRequest(req.getSession(), req);

            // 4) 表示
            req.getRequestDispatcher("/WEB-INF/product-list.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "商品一覧の取得でエラーが発生しました：" + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }

    // POSTで来ても一覧表示に統一
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    // ------ helpers ------

    /** /products?success=1&updated=1&deleted=1&error=メッセージ のような通知クエリをセッションへ */
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
        // 任意のエラーメッセージを渡したい場合 /products?error=... で
        String err = req.getParameter("error");
        if (err != null && !err.isBlank()) {
            setFlash(req.getSession(), err);
        }
    }

    /** セッションにフラッシュメッセージを積む */
    private void setFlash(HttpSession session, String message) {
        session.setAttribute("flashMessage", message);
    }

    /** セッションのフラッシュを request に移し、セッションからは削除 */
    private void moveFlashToRequest(HttpSession session, HttpServletRequest req) {
        Object msg = session.getAttribute("flashMessage");
        if (msg != null) {
            req.setAttribute("flashMessage", msg);
            session.removeAttribute("flashMessage");
        }
    }
}
