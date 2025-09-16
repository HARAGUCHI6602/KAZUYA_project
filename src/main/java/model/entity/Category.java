package model.entity;

public class Category {

    // 元の命名ルール
    private int categoryId;
    private String categoryName;

    public Category() {}

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // --- 元のgetter / setter ---
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    // --- 互換getter（JSPで ${cat.id}, ${cat.name} を使えるように）---
    public int getId() { return categoryId; }
    public String getName() { return categoryName; }

    // --- 互換setter（DAO側が setId/setName を呼んでも通るように）---
    public void setId(int id) { this.categoryId = id; }
    public void setName(String name) { this.categoryName = name; }
}



