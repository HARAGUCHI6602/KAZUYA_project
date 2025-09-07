package model.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ProductDAO;
import model.entity.Product;

@WebServlet("/products/store")
public class ProductRegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        String stockStr = req.getParameter("stock");
        String categoryIdStr = req.getParameter("categoryId");

        List<String> errors = new ArrayList<>();
        int price = 0, stock = 0, categoryId = 0;

        // 入力チェック
        if (name == null || name.trim().isEmpty()) errors.add("商品名を入力してください。");
        try { price = Integer.parseInt(priceStr); if (price < 0) errors.add("価格は0以上で入力してください。"); }
        catch (Exception e) { errors.add("価格は整数で入力してください。"); }
        try { stock = Integer.parseInt(stockStr); if (stock < 0) errors.add("在庫数は0以上で入力してください。"); }
        catch (Exception e) { errors.add("在庫数は整数で入力してください。"); }
        try { categoryId = Integer.parseInt(categoryIdStr); if (categoryId <= 0) errors.add("カテゴリを選択してください。"); }
        catch (Exception e) { errors.add("カテゴリを選択してください。"); }

        if (!errors.isEmpty()) {
            // 入力値を戻す
            req.setAttribute("errors", errors);
            req.setAttribute("old_name", name);
            req.setAttribute("old_price", priceStr);
            req.setAttribute("old_stock", stockStr);
            req.setAttribute("old_categoryId", categoryIdStr);
            // フォームへ戻す（/products/register が ProductRegisterFormServlet にマッピング）
            req.getRequestDispatcher("/products/register").forward(req, resp);
            return;
        }

        // 登録
        Product p = new Product();
        p.setName(name.trim());
        p.setPrice(price);
        p.setStock(stock);
        p.setCategoryId(categoryId);

        boolean ok = new ProductDAO().insert(p);
        if (ok) {
            // 二重送信防止 & フラッシュ
            resp.sendRedirect(req.getContextPath() + "/products?success=1");
        } else {
            req.setAttribute("message", "登録に失敗しました。");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
