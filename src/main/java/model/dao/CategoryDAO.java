package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.Category;

public class CategoryDAO {

    /** すべてのカテゴリをID昇順で取得 */
    public List<Category> findAll() {
        String sql = "SELECT id, name FROM categories ORDER BY id";
        List<Category> list = new ArrayList<>();

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                list.add(c);
            }
        } catch (SQLException e) {
            // 学習用：コンソールに出力（本番はロガー推奨）
            e.printStackTrace();
        }
        return list;
    }

    /**
     * カテゴリを登録する（ID・名前とも明示指定）
     * 成功すれば true。
     * ※ ここでは SQLException を握りつぶさず「投げる」→ 呼び出し側で重複IDなどを判定できる
     */
    public boolean insert(Category c) throws SQLException {
        String sql = "INSERT INTO categories (id, name) VALUES (?, ?)";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getId());
            ps.setString(2, c.getName());
            return ps.executeUpdate() == 1;
        }
    }

    // もし「IDは自動採番（AUTO_INCREMENT）で、名前だけ入れたい」場合は別メソッドを用意してもOK
    // public boolean insertAuto(Category c) throws SQLException {
    //     String sql = "INSERT INTO categories (name) VALUES (?)";
    //     try (Connection con = ConnectionManager.getConnection();
    //          PreparedStatement ps = con.prepareStatement(sql)) {
    //         ps.setString(1, c.getName());
    //         return ps.executeUpdate() == 1;
    //     }
    // }
}
