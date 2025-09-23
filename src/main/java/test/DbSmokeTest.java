package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

class DbSmokeTest extends BaseH2Test {

    @Test
    void categoriesに2件入ってる() throws Exception {
        try (Statement st = BaseH2Test.con.createStatement();   // ← ココ！
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM categories")) {
            rs.next();
            assertEquals(2, rs.getInt(1));
        }
    }
}

