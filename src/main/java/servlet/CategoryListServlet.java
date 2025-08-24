package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import model.entity.CategoryBean;

public class CategoryListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // DAOでカテゴリ一覧を取得
        CategoryDAO dao = new CategoryDAO();
        List<CategoryBean> categoryList = dao.findAll();

        // デバッグ出力（任意）
        System.out.println("カテゴリ件数: " + categoryList.size());

        // JSPへ渡す
        request.setAttribute("categoryList", categoryList);

        // 表示ページにフォワード
        request.getRequestDispatcher("/category-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
