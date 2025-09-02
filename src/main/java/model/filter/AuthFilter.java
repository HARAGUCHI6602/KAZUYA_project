package model.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();

        // ログイン画面・処理、および静的ファイルは素通り
        boolean isLoginPath = path.endsWith("/login") || path.endsWith("/login.jsp");
        boolean isStatic = path.endsWith(".css") || path.endsWith(".js")
                         || path.endsWith(".png") || path.endsWith(".jpg")
                         || path.contains("/assets/");
        if (isLoginPath || isStatic) {
            chain.doFilter(req, res);
            return;
        }

        // セッション確認
        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if (loggedIn) {
            chain.doFilter(req, res);           // ログイン済み → 通過
        } else {
            // 未ログイン → ログイン画面に戻す（メッセージ付き）
            request.setAttribute("error", "ログインしてください。");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 今回は何もしない
    }

    @Override
    public void destroy() {
        // 今回は何もしない
    }
}

