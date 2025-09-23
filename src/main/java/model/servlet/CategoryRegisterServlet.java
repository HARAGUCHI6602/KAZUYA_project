package model.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import model.entity.Category;

@WebServlet("/category/register")
public class CategoryRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 動作確認用
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        resp.getWriter().println("OK: CategoryRegisterServlet is alive");
    }

    // フォーム送信を受け取る
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("categoryId");
        String name  = request.getParameter("categoryName");

        List<String> errors = new ArrayList<>();

        // --- 入力チェック ---
        int id = 0;
        if (idStr == null || idStr.isBlank()) {
            errors.add("カテゴリIDを入力してください。");
        } else {
            try {
                id = Integer.parseInt(idStr);
                if (id <= 0) errors.add("カテゴリIDは1以上の整数で入力してください。");
            } catch (NumberFormatException e) {
                errors.add("カテゴリIDは数字で入力してください。");
            }
        }

        if (name == null || name.isBlank()) {
            errors.add("カテゴリ名を入力してください。");
        } else if (name.length() > 100) {
            errors.add("カテゴリ名は100文字以内で入力してください。");
        }

        // 入力値の保持（エラー時の戻し用）
        request.setAttribute("categoryId", idStr);
        request.setAttribute("categoryName", name);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            RequestDispatcher rd = request.getRequestDispatcher("/category-register.jsp");
            rd.forward(request, response);
            return;
        }

        // --- 登録処理 ---
        try {
            CategoryDAO dao = new CategoryDAO();

            // ★ 方式A：DAOの戻り値 boolean に合わせる
            boolean inserted = dao.insert(new Category(id, name));

            if (!inserted) {
                errors.add("登録に失敗しました。もう一度お試しください。");
                request.setAttribute("errors", errors);
                RequestDispatcher rd = request.getRequestDispatcher("/category-register.jsp");
                rd.forward(request, response);
                return;
            }

            // 成功：PRGで一覧へ
            response.sendRedirect(request.getContextPath() + "/categories");

        } catch (SQLException e) {
            // 重複キー　エラー
            if ("23000".equals(e.getSQLState()) && e.getErrorCode() == 1062) {
                errors.add("そのカテゴリIDはすでに使われています。別のIDにしてください。");
            } else {
                errors.add("サーバー内部エラーが発生しました。");
            }
            request.setAttribute("errors", errors);
            RequestDispatcher rd = request.getRequestDispatcher("/category-register.jsp");
            rd.forward(request, response);
        }
    }
}



