package basic;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductManager manager = new ProductManager();

        while (true) {
            System.out.println("\n---メニュー---");
            System.out.println("1: 商品追加");
            System.out.println("2: 商品情報取得");
            System.out.println("3: 商品検索");
            System.out.println("4: 商品全て表示");
            System.out.println("5: 商品削除");
            System.out.println("0: 終了");
            System.out.print("メニューから操作を選択してください: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 改行消し

            try {
                switch (choice) {
                    case 1: // 商品追加
                        System.out.print("商品IDを入力してください: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("商品名を入力してください: ");
                        String name = scanner.nextLine();
                        if (name.isEmpty()) throw new Exception("無効な入力です。商品名を正しく入力してください。");

                        System.out.print("価格を入力してください: ");
                        int price = scanner.nextInt();
                        if (price < 0) throw new Exception("無効な入力です。価格を正しく入力してください。");

                        System.out.print("在庫数を入力してください: ");
                        int stock = scanner.nextInt();
                        if (stock < 0) throw new Exception("無効な入力です。在庫を正しく入力してください。");

                        Product p = new Product(id, name, price, stock);
                        manager.addProduct(p);
                        break;

                    case 2: // 商品情報取得（完全一致）
                        System.out.print("商品名を入力してください: ");
                        String searchName = scanner.nextLine();
                        Product found = manager.getProductByName(searchName);
                        if (found != null) {
                            System.out.println("取得した商品: " + found);
                        } else {
                            System.out.println("商品が見つかりませんでした。");
                        }
                        break;

                    case 3: // 商品検索（部分一致）
                        System.out.print("検索する商品名を入力してください: ");
                        String keyword = scanner.nextLine();
                        List<Product> results = manager.searchProducts(keyword);
                        for (Product prod : results) {
                            System.out.println(prod);
                        }
                        if (results.isEmpty()) {
                            System.out.println("一致する商品が見つかりませんでした。");
                        }
                        break;

                    case 4: // 全て表示
                        manager.showAllProducts();
                        break;

                    case 5: // 商品削除
                        System.out.print("削除する商品IDを入力してください: ");
                        int deleteId = scanner.nextInt();
                        boolean deleted = manager.deleteProductById(deleteId);
                        if (deleted) {
                            System.out.println("商品IDが" + deleteId + "の情報を削除しました。");
                        } else {
                            System.out.println("該当する商品が見つかりません。");
                        }
                        break;

                    case 0: // 終了
                        System.out.println("終了します");
                        scanner.close();
                        return;

                    default:
                        System.out.println("正しいメニュー番号を入力してください。");
                }
            } catch (Exception e) {
                System.out.println("エラー: " + e.getMessage());
                scanner.nextLine(); // 失敗した入力をクリア
            }
        }
    }
}
