package kata.supermarket;

import java.math.BigDecimal;

public interface Item {
    DiscountStrategy discountStrategy();
    BigDecimal price();
    Product product();
}
