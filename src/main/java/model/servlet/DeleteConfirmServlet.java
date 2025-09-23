package model.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.ProductDAO;
import model.entity.Product;

@WebServlet("/delete-confirm")
public class DeleteConfirmServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        try {
            int id = Integer.parseInt(req.getParameter("id"));

            Product product = new ProductDAO().findByIdWithCategory(id);
            if (product == null) {
                session.setAttribute("flashError", "指定した商品が見つかりません。（ID=" + id + "）");
                resp.sendRedirect(req.getContextPath() + "/products");
                return;
            }

            req.setAttribute("product", product);
            req.getRequestDispatcher("/WEB-INF/delete-confirm.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            session.setAttribute("flashError", "商品IDが不正です。");
            resp.sendRedirect(req.getContextPath() + "/products");
        } catch (Exception e) {
            session.setAttribute("flashError", "削除確認の表示でエラー: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/products");
    }
}




