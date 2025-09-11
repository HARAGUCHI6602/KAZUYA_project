package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.Product;

public class ProductDAO {

    /** 商品一覧（カテゴリ名付き）を新しい順で返す */
    public List<Product> findAllWithCategory() {
        String sql =
            "SELECT p.id, p.name, p.price, p.stock, p.category_id, c.name AS category_name " +
            "FROM products p JOIN categories c ON p.category_id = c.id " +
            "ORDER BY p.id DESC";

        List<Product> list = new ArrayList<>();

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getInt("stock"),
                    rs.getInt("category_id"),
                    rs.getString("category_name")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 学習用
        }
        return list;
    }

    /** 主キー検索（確認画面で使う：カテゴリ名付き） */
    public Product findByIdWithCategory(int id) {
        String sql =
            "SELECT p.id, p.name, p.price, p.stock, p.category_id, c.name AS category_name " +
            "FROM products p JOIN categories c ON p.category_id = c.id " +
            "WHERE p.id = ?";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 商品を登録する（成功なら true） */
    public boolean insert(Product p) {
        String sql = "INSERT INTO products (name, price, stock, category_id) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setInt(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getCategoryId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 商品を削除する（1件削除で true／見つからない等で false） */
    public boolean deleteById(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            // 例：外部キー制約違反（他テーブルが参照中）でもここに来る
            e.printStackTrace();
            return false;
        }
    }
}
