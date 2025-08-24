package method.q09;

public class Even {

    public static boolean checkEven(int number) {
        return number % 2 == 0;
    }

    // テスト用 main メソッド（必要に応じて削除してOK）
    public static void main(String[] args) {
        int num1 = 10;
        int num2 = 5;

        if (checkEven(num1)) {
            System.out.println(num1 + "は偶数です。");
        } else {
            System.out.println(num1 + "は奇数です。");
        }

        if (checkEven(num2)) {
            System.out.println(num2 + "は偶数です。");
        } else {
            System.out.println(num2 + "は奇数です。");
        }
    }
}
