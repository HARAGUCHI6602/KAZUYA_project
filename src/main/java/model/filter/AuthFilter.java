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

/**
 * 認証フィルタ
 * - /login と静的ファイルは素通り
 * - /products は誰でも閲覧可（要件に合わせて）
 * - /protected は未ログイン→ログイン画面、ログイン済み→一覧へフォワード
 */
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String ctx  = request.getContextPath();               // 例: /ProductManage
        String path = request.getRequestURI().substring(ctx.length()); // 例: /products

        boolean isLoginPath   = path.startsWith("/login") || path.equals("/login.jsp");
        boolean isProducts    = path.startsWith("/products");          // 一覧・登録系
        boolean isProtected   = path.startsWith("/protected");         // 保護ページ（テストで使用）
        boolean isStatic      = path.endsWith(".css") || path.endsWith(".js")
                             || path.endsWith(".png") || path.endsWith(".jpg")
                             || path.contains("/assets/");

        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        // --- 素通り対象 ---
        if (isLoginPath || isStatic || isProducts) {
            chain.doFilter(req, res);
            return;
        }

        // --- /protected のみここで握る ---
        if (isProtected) {
            if (loggedIn) {
                // ログイン済みなら商品一覧へフォワード（HTTP 200）
                request.getRequestDispatcher("/products").forward(request, response);
            } else {
                // 未ログインならログイン画面へフォワード（HTTP 200）
                request.setAttribute("error", "ログインしてください。");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
            return;
        }

        // それ以外のパスは、ログイン済みなら通過、未ログインならログインへ
        if (loggedIn) {
            chain.doFilter(req, res);
        } else {
            request.setAttribute("error", "ログインしてください。");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    @Override public void init(FilterConfig filterConfig) throws ServletException {}
    @Override public void destroy() {}
}
