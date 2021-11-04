package kata.supermarket;

import java.math.BigDecimal;

public class UnitProduct extends Product{

    private final BigDecimal pricePerUnit;

    public UnitProduct(final BigDecimal pricePerUnit, DiscountStrategy discountStrategy, String code) {
        super(discountStrategy, PRODUCT_TYPE.UNIT, code);
        this.pricePerUnit = pricePerUnit;
    }

    BigDecimal pricePerUnit() {
        return pricePerUnit;
    }

    public Item oneOf() {
        return new ItemByUnit(this);
    }

}
