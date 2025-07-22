package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import basic.Product;

public class PracticeDB {

    // データベース接続情報
    private static final String URL = "jdbc:mysql://localhost:3306/product_management";
    private static final String USER = "root";
    private static final String PASSWORD = "Kazuya0219"; // ←ここは実際のパスワードに直してください

    // 商品登録
    public static boolean insertProduct(Product product) {
        String sql = "INSERT INTO products (name, price, stock, category_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getPrice());
            pstmt.setInt(3, product.getStock());
            pstmt.setInt(4, product.getCategoryId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println("登録エラー: " + e.getMessage());
            return false;
        }
    }

    // 商品更新（価格・在庫）
    public static boolean updateProductById(int id, int newPrice, int newStock) {
        String sql = "UPDATE products SET price = ?, stock = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newPrice);
            pstmt.setInt(2, newStock);
            pstmt.setInt(3, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println("更新エラー: " + e.getMessage());
            return false;
        }
    }

    // 商品削除（カテゴリID指定）
    public static int deleteProductsByCategoryId(int categoryId) {
        String sql = "DELETE FROM products WHERE category_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("カテゴリ削除エラー: " + e.getMessage());
            return 0;
        }
    }
}

