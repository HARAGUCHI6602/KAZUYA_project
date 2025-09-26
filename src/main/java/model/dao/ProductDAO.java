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
 * - 本番： new ProductDAO() … 各メソッドで ConnectionManager を使う
 * - テスト： new ProductDAO(con) …  Connection を使う
 */
public class ProductDAO {

    /** テストで外から接続。null のときは ConnectionManager を使う */
    private final Connection injectedCon;

    /** 本番用 */
    public ProductDAO() {
        this.injectedCon = null;
    }

    /** テスト用（H2 の接続） */
    public ProductDAO(Connection con) {
        this.injectedCon = con;
    }

    // ====== 共通 ======
    private Connection getConnection() throws SQLException {
        if (injectedCon != null) return injectedCon;
        return ConnectionManager.getConnection();
    }

    /** 挿入が無い場合　close する（挿入あり＝テスト　閉じない） */
    private void closeIfLocal(Connection con) {
        if (injectedCon == null && con != null) {
            try { con.close(); } catch (SQLException ignore) {}
        }
    }

    // ====== SELECT ======
    /** 商品一覧（カテゴリ名付き）*/
    public List<Product> findAllWithCategory() {
        String sql =
            "SELECT p.id, p.name, p.price, p.stock, p.category_id, c.name AS category_name " +
            "FROM products p " +
            "LEFT JOIN categories c ON p.category_id = c.id " +
            "ORDER BY p.id DESC";

        List<Product> list = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeIfLocal(con);
        }
        return list;
    }

    /** 主キー検索（カテゴリ名なし） */
    public Product findById(int id) {
        String sql = "SELECT id, name, price, stock, category_id FROM products WHERE id = ?";
        Connection con = null;
        try {
            con = getConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) return null;
                    return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id"),
                        null
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeIfLocal(con);
        }
    }

    /** 主キー検索（カテゴリ名付き） */
    public Product findByIdWithCategory(int id) {
        String sql =
            "SELECT p.id, p.name, p.price, p.stock, p.category_id, c.name AS category_name " +
            "FROM products p LEFT JOIN categories c ON p.category_id = c.id " +
            "WHERE p.id = ?";
        Connection con = null;
        try {
            con = getConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) return null;
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
            return null;
        } finally {
            closeIfLocal(con);
        }
    }

    /** ★ 追加：カテゴリで絞り込み */
    public List<Product> findByCategory(int categoryId) {
        String sql =
            "SELECT p.id, p.name, p.price, p.stock, p.category_id, c.name AS category_name " +
            "FROM products p LEFT JOIN categories c ON p.category_id = c.id " +
            "WHERE p.category_id = ? ORDER BY p.id DESC";

        List<Product> list = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, categoryId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("price"),
                            rs.getInt("stock"),
                            rs.getInt("category_id"),
                            rs.getString("category_name")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeIfLocal(con);
        }
        return list;
    }

    // ====== INSERT / UPDATE / DELETE ======
    public boolean insert(Product p) {
        String sql = "INSERT INTO products (name, price, stock, category_id) VALUES (?, ?, ?, ?)";
        Connection con = null;
        try {
            con = getConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, p.getName());
                ps.setInt(2, p.getPrice());
                ps.setInt(3, p.getStock());
                ps.setInt(4, p.getCategoryId());
                return ps.executeUpdate() == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeIfLocal(con);
        }
    }

    public boolean update(Product p) {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ?, category_id = ? WHERE id = ?";
        Connection con = null;
        try {
            con = getConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, p.getName());
                ps.setInt(2, p.getPrice());
                ps.setInt(3, p.getStock());
                ps.setInt(4, p.getCategoryId());
                ps.setInt(5, p.getId());
                return ps.executeUpdate() == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeIfLocal(con);
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        Connection con = null;
        try {
            con = getConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                return ps.executeUpdate() == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeIfLocal(con);
        }
    }

    /** 件数（デバッグ用） */
    public int count() {
        String sql = "SELECT COUNT(*) FROM products";
        Connection con = null;
        try {
            con = getConnection();
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeIfLocal(con);
        }
    }
    /** 最新(最大)ID を返す。無ければ null */
    public Integer findLatestId() {
        String sql = "SELECT id FROM products ORDER BY id DESC LIMIT 1";
        Connection con = null;
        try {
            con = getConnection();
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeIfLocal(con);
        }
    }

}


