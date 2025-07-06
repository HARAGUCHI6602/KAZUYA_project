package basic;

public class Product implements Searchable {

    protected int id;
    protected String name;
    protected int price;
    protected int stock;

    public Product(int id, String name, int price, int stock) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new Exception("無効な入力です。商品名を正しく入力してください。");
        }
        if (price < 0) {
            throw new Exception("無効な入力です。価格を正しく入力してください。");
        }
        if (stock < 0) {
            throw new Exception("無効な入力です。在庫数を正しく入力してください。");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String toString() {
        return "Product: id=" + id + ", name=" + name + ", price=" + price + ", stock=" + stock;
    }
    @Override
    public boolean containsName(String keyword) {
        return name.contains(keyword);
    }

}
