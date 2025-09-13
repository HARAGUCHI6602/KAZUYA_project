package basic;

import java.sql.Connection;
import java.util.Scanner;

import db.PracticeDB;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = PracticeDB.getConnection()) {
            conn.setAutoCommit(false);

            Product[] products = new Product[2];

            for (int i = 0; i < 2; i++) {
                System.out.println("--商品" + (i + 1) + "の価格と在庫を更新--");
                System.out.print("商品IDを入力してください：");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("価格を入力してください：");
                int price = Integer.parseInt(scanner.nextLine());
                System.out.print("在庫数を入力してください：");
                int stock = Integer.parseInt(scanner.nextLine());

                products[i] = new Product(id, price, stock);
            }

            int successCount = 0;
            for (Product p : products) {
                if (PracticeDB.updateProduct(conn, p)) {
                    successCount++;
                } else {
                    break;
                }
            }

            if (successCount == products.length) {
                conn.commit();
                System.out.println("コミット成功");
                System.out.println("更新成功件数：" + successCount + "件");

                for (int i = 0; i < successCount; i++) {
                    Product p = products[i];
                    System.out.println("更新内容" + (i + 1) + "：");
                    System.out.println("商品ID: " + p.getId() + ", 価格: " + p.getPrice() + ", 在庫数: " + p.getStock());
                }
            } else {
                conn.rollback();
                System.out.println("全ての更新に失敗しました。");
                System.out.println("更新成功件数：" + successCount + "件");
                System.out.println("ロールバックしました。");
            }

        } catch (Exception e) {
            System.out.println("エラーが発生しました：" + e.getMessage());
        }
    }
}
