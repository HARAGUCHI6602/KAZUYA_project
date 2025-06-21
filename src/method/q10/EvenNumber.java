package method.q10;

import java.util.Arrays;

public class EvenNumber {

    public static int getEvenNumbers(int[] numbers) {
        int count = 0;
        for (int num : numbers) {
            if (num % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    // テスト用 main メソッド（必要に応じて削除）
    public static void main(String[] args) {
        int[] data = {3, 2, 5, 6, 7, 25, 10, 51, 88, 98};
        int evenCount = getEvenNumbers(data);
        System.out.println(Arrays.toString(data) + "には、偶数が" + evenCount + "個あります。");
    }
}
