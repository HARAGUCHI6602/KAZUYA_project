package model.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String VALID_USER = "user@example.com";
    private static final String VALID_PASS = "pass1234";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ▼デバッグ：Tomcatが見ている実パスを出力（配備場所の確認用）
        System.out.println("webappRoot=" + request.getServletContext().getRealPath("/"));
        System.out.println("realPath=" + request.getServletContext().getRealPath("/WEB-INF/login.jsp"));

        // JSP は WEB-INF 配下にあるので forward で表示
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 入力チェック
        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            request.setAttribute("error", "メールとパスワードを入力してください。");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        // 簡易認証（ハードコード）
        if (VALID_USER.equals(email) && VALID_PASS.equals(password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", email);       // セッションにユーザーを保存
            session.setMaxInactiveInterval(30 * 60);   // 30分で自動ログアウト

            // ログイン後は商品一覧へ
            response.sendRedirect(request.getContextPath() + "/products");
        } else {
            request.setAttribute("error", "メールまたはパスワードが違います。");
            request.setAttribute("email", email); // 入力保持
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}

