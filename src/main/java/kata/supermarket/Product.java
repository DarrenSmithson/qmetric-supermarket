package kata.supermarket;

public class Product {
    private final DiscountStrategy discountStrategy;
    private final PRODUCT_TYPE type;

    public Product(DiscountStrategy discountStrategy, PRODUCT_TYPE type) {
        this.discountStrategy = discountStrategy;
        this.type = type;
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    public PRODUCT_TYPE getType() {
        return type;
    }
}
