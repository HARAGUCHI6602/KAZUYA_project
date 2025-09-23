package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HelloJUnitTest {

    @Test
    void testAddition() {
        int result = 2 + 3;
        assertEquals(5, result, "2+3 は 5 のはず");
    }

    @Test
    void testFailExample() {
        int result = 10 - 3;
        assertEquals(6, result, "失敗するテスト");
    }
}
