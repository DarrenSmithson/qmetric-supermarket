package kata.supermarket;

import java.math.BigDecimal;

public class ItemByWeight implements Item {

    private final WeighedProduct weighedProduct;
    private final BigDecimal weightInKilos;

    ItemByWeight(final WeighedProduct product, final BigDecimal weightInKilos) {
        this.weighedProduct = product;
        this.weightInKilos = weightInKilos;
    }

    @Override
    public DiscountStrategy discountStrategy() {
        return weighedProduct.getDiscountStrategy();
    }

    @Override
    public BigDecimal price() {
        return weighedProduct.pricePerKilo().multiply(weightInKilos).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal weight(){
        return weightInKilos;
    }

    @Override
    public Product product(){
        return weighedProduct;
    }
}
