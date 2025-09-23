package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PracticeDB {

    // ★ここをあなたの環境に合わせて書き換えます
    private static final String URL =
        "jdbc:mysql://localhost:3306/productdb"
      + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo";
    private static final String USER = "root";      // MySQLのユーザー名
    private static final String PASS = "password";  // そのパスワード

    // 最初に一回だけドライバを読み込む（MySQL8）
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQLドライバが見つかりません。WEB-INF/lib に JAR を置いたか確認してね。", e);
        }
    }

    // 使う側はこれを呼ぶだけでOK
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

