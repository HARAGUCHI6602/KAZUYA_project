package model.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ProductDAO;
import model.entity.Product;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // 一覧取得
            List<Product> products = new ProductDAO().findAllWithCategory();
            req.setAttribute("products", products);

            // 例：登録直後に /products?success=1 で来たときにフラッシュ
            if ("1".equals(req.getParameter("success"))) {
                req.getSession().setAttribute("flashMessage", "商品を登録しました。");
            }

            // 構成に合わせて WEB-INF 配下へ forward
            req.getRequestDispatcher("/WEB-INF/product-list.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "商品一覧の取得でエラーが発生しました：" + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }
}

