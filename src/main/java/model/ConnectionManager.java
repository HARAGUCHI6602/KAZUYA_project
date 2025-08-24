package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String URL =
        "jdbc:mysql://localhost:3306/product_management"
      + "?useUnicode=true&characterEncoding=utf8"
      + "&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo";
    private static final String USER = "root";          
    private static final String PASSWORD = "Kazuya0219";

    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { throw new RuntimeException(e); }
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("✅ Connection URL 確認: " + URL);
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}



