package model.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import model.entity.Category;

//@WebServlet("/products/register")
public class ProductRegisterFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // ① カテゴリ一覧を取得
            List<Category> categories = new CategoryDAO().findAll();
            // ② JSP で使えるようにセット
            req.setAttribute("categories", categories);
            // ③ フォーム JSP へ転送
            req.getRequestDispatcher("/product-register.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("message", "フォーム表示でエラーが発生しました: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }

    // POSTからォームを出す
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}

