package method.q06;

public class Circle {

    public static double getCircleArea(double radius) {
        return radius * radius * 3.14;
    }

    public static void main(String[] args) {
        double radius = 5.0;
        double area = getCircleArea(radius);

        System.out.println("半径：" + radius);
        System.out.println("円の面積：" + area);
    }
}
