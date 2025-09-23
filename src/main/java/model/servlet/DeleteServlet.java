package model.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ProductDAO;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            request.getSession().setAttribute("flashError", "商品IDが指定されていません。");
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            boolean ok = new ProductDAO().deleteById(id); // DAOにdeleteByIdが必要
            if (ok) {
                request.getSession().setAttribute("flashMessage", "商品（ID=" + id + "）を削除しました。");
            } else {
                request.getSession().setAttribute("flashError", "商品が見つからない、または削除できませんでした。（ID=" + id + "）");
            }
            response.sendRedirect(request.getContextPath() + "/products");

        } catch (Exception e) {
            request.setAttribute("error", "削除処理でエラーが発生しました：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    // 誤った場合一覧へ
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/products");
    }
}

