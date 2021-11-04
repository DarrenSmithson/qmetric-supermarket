package kata.supermarket;

public class Product {
    private final DiscountStrategy discountStrategy;
    private final PRODUCT_TYPE type;
    private final String code;

    public Product(DiscountStrategy discountStrategy, PRODUCT_TYPE type, String code) {
        this.discountStrategy = discountStrategy;
        this.type = type;
        this.code = code;
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    public PRODUCT_TYPE getType() {
        return type;
    }

    public String getCode() {
        return code;
    }
}
