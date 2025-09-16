package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.Product;

/**
 * products テーブル用 DAO
 * - ConnectionManager.getConnection() を使用する前提
 * - 価格/在庫は int 型（あなたの Product エンティティに合わせています）
 */
public class ProductDAO {

    /** 商品一覧（カテゴリ名付き）を新しい順で返す */
    public List<Product> findAllWithCategory() {
        String sql =
            "SELECT p.id, p.name, p.price, p.stock, p.category_id, c.name AS category_name " +
            "FROM products p " +
            "LEFT JOIN categories c ON p.category_id = c.id " + // ← 未設定カテゴリも表示
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

    /** 主キー検索（編集フォームで使う：カテゴリ名なし／IDのみ保持） */
    public Product findById(int id) {
        String sql =
            "SELECT id, name, price, stock, category_id " +
            "FROM products WHERE id = ?";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                // category_name は不要だが、あなたのコンストラクタに合わせて null を渡す
                return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getInt("stock"),
                    rs.getInt("category_id"),
                    null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 主キー検索（確認画面で使う：カテゴリ名付き） */
    public Product findByIdWithCategory(int id) {
        String sql =
            "SELECT p.id, p.name, p.price, p.stock, p.category_id, c.name AS category_name " +
            "FROM products p LEFT JOIN categories c ON p.category_id = c.id " +
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

    /** 商品を更新する（成功なら true）— 編集機能で使用 */
    public boolean update(Product p) {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ?, category_id = ? WHERE id = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setInt(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getCategoryId());
            ps.setInt(5, p.getId());
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
            // 外部キー制約違反 等でもここに来る
            e.printStackTrace();
            return false;
        }
    }

    /** 件数（デバッグ用） */
    public int count() {
        String sql = "SELECT COUNT(*) FROM products";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

