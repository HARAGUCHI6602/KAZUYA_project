package method.q05;

public class Triangle {

    public static int getTriangleArea(int base, int height) {
        return (base * height) / 2;
    }

    public static void main(String[] args) {
        int base = 8;
        int height = 5;
        int area = getTriangleArea(base, height);

        System.out.println("底辺：" + base);
        System.out.println("高さ：" + height);
        System.out.println("三角形の面積：" + area);
    }
}
