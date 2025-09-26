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

    // 許可するパターン
    private static final String USER1 = "admin";
    private static final String PASS1 = "password";
    private static final String USER2 = "user@example.com";
    private static final String PASS2 = "pass1234";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // テストは username を使うが、画面実装では email の場合もあるため両方許可
        String user = nz(firstNonBlank(
                request.getParameter("username"),
                request.getParameter("email")
        ));
        String pass = nz(request.getParameter("password"));

        System.out.println("[LOGIN][REQ] user=" + user + " pass?=" + (!pass.isEmpty()));

        // 入力確認
        if (user.isEmpty() || pass.isEmpty()) {
            request.setAttribute("error", "メール（またはユーザー名）とパスワードを入力してください。");
            request.setAttribute("email", user);
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        boolean ok = (USER1.equals(user) && PASS1.equals(pass))
                  || (USER2.equals(user) && PASS2.equals(pass));

        System.out.println("[LOGIN][RESULT] " + ok);

        if (ok) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60);

            // 一覧へ
            request.getRequestDispatcher("/products").forward(request, response);
        } else {
            request.setAttribute("error", "メール（またはユーザー名）またはパスワードが違います。");
            request.setAttribute("email", user);
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    private static String nz(String s) { return s == null ? "" : s; }

    private static String firstNonBlank(String... vals) {
        if (vals == null) return "";
        for (String v : vals) {
            if (v != null && !v.isBlank()) return v;
        }
        return "";
    }
}
