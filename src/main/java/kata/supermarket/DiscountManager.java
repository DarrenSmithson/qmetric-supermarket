package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;

public class DiscountManager {

    public static BigDecimal totalDiscount(List<Item> items) {

        return ItemManager.itemsByProdCode(items)
                            .values()
                            .stream()
                            .map(i -> discountStrategy(i).applyDiscounts(i))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    private static DiscountStrategy discountStrategy(List<Item> items) {
        return items.get(0).product().getDiscountStrategy();
    }

}
