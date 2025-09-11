package model.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import model.entity.Category;

@WebServlet("/categories")
public class CategoryListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoryDAO dao = new CategoryDAO();
        List<Category> list = dao.findAll();

        // ★JSPに合わせて "categoryList" という名前で渡す
        request.setAttribute("categoryList", list);

        RequestDispatcher rd = request.getRequestDispatcher("/category-list.jsp");
        rd.forward(request, response);
    }
}

