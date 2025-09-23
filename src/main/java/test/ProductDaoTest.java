package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.dao.ProductDAO;
import model.entity.Product;

class ProductDaoTest extends BaseH2Test {

    static ProductDAO dao;

    @BeforeAll
    static void 用意() {
        // H2 の接続を DAO に挿入
        dao = new ProductDAO(BaseH2Test.con);
    }

    // 1) 商品登録（正常）
    @Test
    void 商品登録_正常() {
        int before = dao.count();

        // BaseH2Test の categories に合わせる
        Product p = new Product(0, "Laptop", 100000, 10, 1, null);
        assertTrue(dao.insert(p));

        assertEquals(before + 1, dao.count());

        // 直近のレコードを確認
        Product newest = dao.findAllWithCategory().get(0);
        assertEquals("Laptop", newest.getName());
        assertEquals(100000, newest.getPrice());
        assertEquals(10, newest.getStock());
        assertEquals(1, newest.getCategoryId());
    }

    // 2) 商品登録（異常：名前null → NOT NULL制約で失敗）
    @Test
    void 商品登録_名前null_異常() {
        Product bad = new Product(0, null, 100, 1, 1, null);
        assertFalse(dao.insert(bad));
    }

    // 3) 商品削除（正常）
    @Test
    void 商品削除_正常() {
        // BaseH2Test 初期データ： id=1 'Chair'
        assertTrue(dao.deleteById(1));
        assertNull(dao.findById(1));
    }

    // 4) 商品削除（異常：存在しないID）
    @Test
    void 商品削除_存在しないID_異常() {
        assertFalse(dao.deleteById(9999));
    }

    // 5) 商品一覧（カテゴリでフィルタ）
    @Test
    void 商品一覧_カテゴリフィルタ() {
        // BaseH2Test 初期データ： category_id=1 に 'Laptop' が入っている
        List<Product> pcs = dao.findByCategory(1);
        assertTrue(pcs.size() >= 1);
        assertTrue(pcs.stream().allMatch(p -> p.getCategoryId() == 1));
    }

    // 6) 商品編集（更新）
    @Test
    void 商品編集_正常() {
        // id=2 'Laptop' を書き換える（BaseH2Test初期データ）
        Product upd = new Product(2, "Laptop Pro", 120000, 8, 1, null);
        assertTrue(dao.update(upd));

        Product got = dao.findById(2);
        assertNotNull(got);
        assertEquals("Laptop Pro", got.getName());
        assertEquals(120000, got.getPrice());
        assertEquals(8, got.getStock());
        assertEquals(1, got.getCategoryId());
    }
}
