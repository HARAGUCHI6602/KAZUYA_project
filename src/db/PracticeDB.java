package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PracticeDB {
    public static void main(String[] args) {
        // データベース接続に使う情報
        String url = "jdbc:mysql://localhost:3306/product_management"; 
        String user = "root";                               
        String password = "Kazuya0219?";                       

        try {
            // 接続を試みる
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("DB接続成功");

            // データを取得して表示
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM products";  
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");

                System.out.println("id: " + id);
                System.out.println("name: " + name);
                System.out.println("price: " + price);
                System.out.println("stock: " + stock);
                System.out.println("------------");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("DB接続失敗");
            e.printStackTrace();
        }
    }
}
