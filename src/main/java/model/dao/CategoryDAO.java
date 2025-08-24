// src/main/java/model/dao/CategoryDAO.java
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionManager;
import model.entity.CategoryBean;

public class CategoryDAO {

    // DBのカラムに合わせて Id を別名 category_id で受ける
    private static final String SQL_FIND_ALL =
        "SELECT Id AS category_id, category_name FROM categories ORDER BY Id";

    public List<CategoryBean> findAll() {
        List<CategoryBean> list = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("category_name");
                list.add(new CategoryBean(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException("カテゴリ一覧の取得に失敗", e);
        }
        return list;
    }
}
