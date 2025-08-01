package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import model.entity.CategoryBean;

@WebServlet("/category-list")
public class CategoryListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // カテゴリ情報を取得
        CategoryDAO dao = new CategoryDAO();
        List<CategoryBean> categoryList = dao.findAllCategories();
        System.out.println("✅ DAOから取得した件数：" + categoryList.size());


        // ここで件数をログ出力
        System.out.println("カテゴリ件数: " + categoryList.size());

        // JSP にデータを渡す
        request.setAttribute("categoryList", categoryList);

        // 表示ページに転送
        request.getRequestDispatcher("/category-list.jsp").forward(request, response);
    }

}
