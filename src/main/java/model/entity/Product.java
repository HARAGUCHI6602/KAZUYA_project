package model.entity;

public class Product {
    private int id;
    private String name;
    private int price;
    private int stock;
    private int categoryId;
    private String categoryName; // 一覧表示用（JOINしたカテゴリ名を入れる）

    public Product() {}

    // 一覧用（JOINでカテゴリ名も一緒に持たせる想定）
    public Product(int id, String name, int price, int stock, int categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // --- getters ---
    public int getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getStock() { return stock; }
    public int getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }

    // --- setters ---
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(int price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}

