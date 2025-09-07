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

        List<Product> products = new ProductDAO().findAllWithCategory();
        req.setAttribute("products", products);

        if ("1".equals(req.getParameter("success"))) {
            req.setAttribute("flash", "商品を登録しました。");
        }

        req.getRequestDispatcher("/product-list.jsp").forward(req, resp);
    }
}
