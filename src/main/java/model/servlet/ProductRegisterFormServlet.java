package model.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import model.entity.Category;

@WebServlet("/products/register")
public class ProductRegisterFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ① カテゴリ一覧をDBから取得
        List<Category> categories = new CategoryDAO().findAll();

        // ② JSPで使えるようにセット
        req.setAttribute("categories", categories);

        // ③ フォームJSPへ転送
        req.getRequestDispatcher("/product-register.jsp").forward(req, resp);
    }
}
