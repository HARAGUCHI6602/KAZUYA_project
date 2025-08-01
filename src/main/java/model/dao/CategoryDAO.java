package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.entity.CategoryBean;

public class CategoryDAO {

    public List<CategoryBean> findAllCategories() {
        List<CategoryBean> categoryList = new ArrayList<>();

        String sql = "SELECT id, name FROM category";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                CategoryBean category = new CategoryBean();
                category.setCategoryId(rs.getInt("id"));
                category.setCategoryName(rs.getString("name"));
                categoryList.add(category);
            }

            // ログ出力で確認（任意）
            System.out.println("カテゴリ件数：" + categoryList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoryList;
    }
}

