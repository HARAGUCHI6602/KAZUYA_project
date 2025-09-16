package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.Category;

/**
 * categories テーブル用 DAO
 * 使い方メモ：
 *  - 取得系（findAll / findById / exists）は内部で例外を握って RuntimeException に包まず、
 *    失敗時は空や null を返す簡易仕様。必要に応じてアプリ側でハンドリングしてください。
 *  - 変更系（insert / update / delete）は SQLException を「投げる」ので、
 *    呼び出し側で重複・外部キー制約などの判定ができます。
 */
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

    /** 主キーで1件取得（なければ null） */
    public Category findById(int id) {
        String sql = "SELECT id, name FROM categories WHERE id = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** そのIDのカテゴリが存在するか？ */
    public boolean exists(int id) {
        String sql = "SELECT 1 FROM categories WHERE id = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * カテゴリを登録する（ID・名前とも明示指定）
     * 成功すれば true
     * 例：IDを自分で採番して管理している場合など
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

    /**
     * カテゴリを登録する（IDはAUTO_INCREMENT、名前だけ指定）
     * 成功すれば true
     */
    public boolean insertAuto(Category c) throws SQLException {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * カテゴリ名を更新（id で指定）
     * 成功すれば true
     */
    public boolean update(Category c) throws SQLException {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setInt(2, c.getId());
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * 1件削除（外部キー制約に注意：products などが参照していると失敗します）
     * 成功すれば true
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    /** 件数を返す（管理/デバッグ用） */
    public int count() {
        String sql = "SELECT COUNT(*) FROM categories";
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
