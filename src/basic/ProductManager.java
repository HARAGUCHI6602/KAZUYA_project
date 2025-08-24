package basic;

import java.util.ArrayList;
import java.util.List;

public class ProductManager implements Searchable {

    private List<Product> products;

    public ProductManager() {
        products = new ArrayList<>();
    }

    // 商品を追加するメソッド
    public void addProduct(Product product) {
        products.add(product);
    }

    // キーワードで商品名を検索する
    @Override
    public List<Product> search(String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains(keyword)) {
                result.add(product);
            }
        }
        return result;
    }
}
