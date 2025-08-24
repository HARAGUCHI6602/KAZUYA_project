package method.q04;

public class SumNumbers {

    public static int calculateSum(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        int num1 = 3;
        int num2 = 2;
        int result = calculateSum(num1, num2);

        System.out.println("第一引数：" + num1);
        System.out.println("第二引数：" + num2);
        System.out.println("加算結果：" + result);
    }
}
