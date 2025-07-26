package basic;

public class Product {
    private int id;
    private String name;
    private int price;
    private int stock;
    private int categoryId;

    // 商品登録用コンストラクタ（IDなし）
    public Product(String name, int price, int stock, int categoryId) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
    }

    // 商品表示・取得用コンストラクタ（IDあり）
    public Product(int id, String name, int price, int stock, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
    }
 // トランザクション用（id, price, stock）
    public Product(int id, int price, int stock) {
        this.id = id;
        this.price = price;
        this.stock = stock;
    }

    
    // Getter（必要に応じて追加可能）
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

    public int getCategoryId() {
        return categoryId;
    }
}
