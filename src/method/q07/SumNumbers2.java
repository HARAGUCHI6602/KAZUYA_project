package method.q07;

public class SumNumbers2 {

    public static int calculateSum(int num1, double num2) {
        // 加算して四捨五入でint型に変換
        return (int)Math.round(num1 + num2);
    }

    public static void main(String[] args) {
        int intVal = 5;
        double doubleVal = 3.3;

        int result = calculateSum(intVal, doubleVal);

        System.out.println("第一引数（整数）: " + intVal);
        System.out.println("第二引数（実数）: " + doubleVal);
        System.out.println("加算結果: " + result);
    }
}
