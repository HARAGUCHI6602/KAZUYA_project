package basic;

public class Main {
    public static void main(String[] args) {
        // DiscountedProductのインスタンスを作成
        DiscountedProduct dp = new DiscountedProduct(2, "ソファ", 30000, 5, 0.3);

        // 通常のProductも作成（比較用）
        Product p = new Product(5, "Tシャツ", 1500, 5);

        // 商品情報を表示
        System.out.println("ーー商品名「ソファ」の情報と割引率30％の情報を表示するーー");
        System.out.println(dp);

        System.out.println("\nーー商品名「Tシャツ」を検索して表示するーー");
        System.out.println(p);
    }
}
