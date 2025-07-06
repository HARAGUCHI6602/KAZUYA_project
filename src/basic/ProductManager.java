package basic;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private List<Product> productList = new ArrayList<>();

    // 商品を追加
    public void addProduct(Product product) {
        productList.add(product);
        System.out.println(product + " を登録しました。");
    }

    // 商品名で完全一致で1件取得
    public Product getProductByName(String name) {
        for (Product p : productList) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    // 商品名で部分一致検索（Searchableで対応）
    public List<Product> searchProducts(String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product p : productList) {
            if (p instanceof Searchable) {
                if (((Searchable) p).containsName(keyword)) {
                    result.add(p);
                }
            }
        }
        return result;
    }

    // 商品IDで削除
    public boolean deleteProductById(int id) {
        for (Product p : productList) {
            if (p.id == id) {
                productList.remove(p);
                return true;
            }
        }
        return false;
    }

    // 商品すべてを表示
    public void showAllProducts() {
        for (Product p : productList) {
            System.out.println(p);
        }
    }
}
