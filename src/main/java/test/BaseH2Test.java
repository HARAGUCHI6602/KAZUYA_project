package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseH2Test {
    public static Connection con;  // ← public static 

    @BeforeAll
    static void 起動して表を作る() throws Exception {
        Class.forName("org.h2.Driver");
        con = DriverManager.getConnection(
            "jdbc:h2:mem:pm;MODE=MySQL;DB_CLOSE_DELAY=-1", "sa", "");
        try (Statement st = con.createStatement()) {
            st.execute("DROP TABLE IF EXISTS products");
            st.execute("DROP TABLE IF EXISTS categories");
            st.execute("CREATE TABLE categories(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL)");
            st.execute("CREATE TABLE products(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, price INT NOT NULL, stock INT NOT NULL, category_id INT, FOREIGN KEY (category_id) REFERENCES categories(id))");
        }
    }

    @BeforeEach
    void 毎回初期化() throws Exception {
        try (Statement st = con.createStatement()) {
            st.execute("DELETE FROM products");
            st.execute("DELETE FROM categories");
            st.execute("ALTER TABLE products ALTER COLUMN id RESTART WITH 1");
            st.execute("ALTER TABLE categories ALTER COLUMN id RESTART WITH 1");
            st.execute("INSERT INTO categories(name) VALUES ('PC'),('Furniture')");
            st.execute("INSERT INTO products(name,price,stock,category_id) VALUES ('Chair',5000,20,2),('Laptop',100000,10,1)");
        }
    }

    @AfterAll
    static void 終了() throws Exception {
        if (con != null && !con.isClosed()) con.close();
    }
}

