package basic;

public class DiscountedProduct extends Product {
    private double discountRate;

    public DiscountedProduct(int id, String name, int price, int stock, double discountRate) {
        super(id, name, price, stock);
        this.discountRate = discountRate;
    }
    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        int discountedPrice = (int) (getPrice() * (1 - discountRate));
        return super.toString() + ", 割引率=" + discountRate + ", 割引後価格=" + discountedPrice;
    }
}
