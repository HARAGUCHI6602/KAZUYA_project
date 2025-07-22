package basic;

import java.util.Scanner;

import db.PracticeDB;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("―― メニュー ――");
            System.out.println("1: 商品追加");
            System.out.println("2: 商品更新（価格・在庫）");
            System.out.println("3: 商品削除（カテゴリID指定）");
            System.out.println("0: 終了");
            System.out.print("メニューから操作を選択してください：");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 改行消し

            switch (choice) {
                case 1:
                    System.out.println("-- 商品の登録 --");
                    System.out.print("商品名を入力してください：");
                    String name = scanner.nextLine();
                    System.out.print("価格を入力してください：");
                    int price = scanner.nextInt();
                    System.out.print("在庫数を入力してください：");
                    int stock = scanner.nextInt();
                    System.out.print("カテゴリIDを入力してください：");
                    int categoryId = scanner.nextInt();

                    Product product = new Product(name, price, stock, categoryId);
                    boolean inserted = PracticeDB.insertProduct(product);
                    if (inserted) {
                        System.out.println("登録成功");
                        System.out.println("商品名: " + name + "、価格: " + price + "、在庫数: " + stock + "、カテゴリID: " + categoryId);
                    } else {
                        System.out.println("登録失敗");
                    }
                    break;

                case 2:
                    System.out.println("-- 商品の価格と在庫を更新 --");
                    System.out.print("商品IDを入力してください：");
                    int updateId = scanner.nextInt();
                    System.out.print("価格を入力してください：");
                    int newPrice = scanner.nextInt();
                    System.out.print("在庫数を入力してください：");
                    int newStock = scanner.nextInt();

                    boolean updated = PracticeDB.updateProductById(updateId, newPrice, newStock);
                    if (updated) {
                        System.out.println("更新成功");
                        System.out.println("商品ID: " + updateId + "、価格: " + newPrice + "、在庫数: " + newStock);
                    } else {
                        System.out.println("更新失敗");
                    }
                    break;

                case 3:
                    System.out.println("-- 商品の削除（カテゴリID指定） --");
                    System.out.print("削除するカテゴリIDを入力してください：");
                    int delCategoryId = scanner.nextInt();
                    int deletedCount = PracticeDB.deleteProductsByCategoryId(delCategoryId);
                    System.out.println("削除成功件数：" + deletedCount + "件");
                    break;

                case 0:
                    System.out.println("終了します。");
                    scanner.close();
                    return;

                default:
                    System.out.println("無効な選択です。もう一度お試しください。");
                    break;
            }

            System.out.println(); // 改行
        }
    }
}
