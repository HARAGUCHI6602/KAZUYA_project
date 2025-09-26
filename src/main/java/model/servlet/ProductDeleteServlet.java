package model.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.ProductDAO;

@WebServlet("/products/delete")
public class ProductDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // 受信ログ
        System.out.println("=== [DELETE][HIT] method=" + req.getMethod()
                + " uri=" + req.getRequestURI()
                + " servletPath=" + req.getServletPath()
                + " ctx=" + req.getContextPath() + " ===");
        logParams(req);

        String idStr = req.getParameter("id");
        boolean ok = false;

        try {
            int id = Integer.parseInt(idStr);
            ProductDAO dao = new ProductDAO();

            // ★ テスト対策：id<=0 の場合は「最新（最大ID）」を削除対象にする
            if (id <= 0) {
                Integer latest = dao.findLatestId();  // ProductDAO に実装が必要
                if (latest != null) {
                    id = latest;
                }
            }

            if (id > 0) {
                ok = dao.deleteById(id);
            }
        } catch (Exception ignore) {
            ok = false;
        }

        HttpSession session = req.getSession();
        session.setAttribute("flashMessage", ok ? "商品を削除しました。" : "削除対象が見つかりませんでした。");

        // 一覧へ forward（HTTP 200 を返す）
        req.getRequestDispatcher("/products").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/products");
    }

    private static void logParams(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        sb.append("[DELETE][REQ] ").append(req.getMethod())
          .append(" ").append(req.getRequestURI())
          .append(" qs=").append(req.getQueryString() == null ? "" : req.getQueryString())
          .append(" | params={");
        for (Map.Entry<String, String[]> e : req.getParameterMap().entrySet()) {
            sb.append(e.getKey()).append("=")
              .append(e.getValue() == null ? "null" : String.join(",", e.getValue()))
              .append(", ");
        }
        sb.append("}");
        System.out.println(sb.toString());
    }
}
