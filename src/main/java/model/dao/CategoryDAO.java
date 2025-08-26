package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.Category;

public class CategoryDAO {

	// 一覧取得
	private static final String SQL_FIND_ALL =
	    "SELECT Id AS category_id, category_name FROM categories ORDER BY Id";

	// 追加（登録）　
	private static final String SQL_INSERT =
	    "INSERT INTO categories (Id, category_name) VALUES (?, ?)";


    /** すべてのカテゴリを取得 */
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("category_name");
                list.add(new Category(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException("カテゴリ一覧の取得に失敗しました。", e);
        }
        return list;
    }

    /** カテゴリを1件登録する */
    public int insert(Category c) throws SQLException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
            ps.setInt(1, c.getCategoryId());
            ps.setString(2, c.getCategoryName());
            return ps.executeUpdate();
        }
    }
}
