package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum DiscountStrategy {
    BUYONEGETONEFREE {
        public BigDecimal applyDiscounts(List<Item> items) {

            return BigDecimal.valueOf(count(unitItems(items)))
                             .divide(BigDecimal.valueOf(2), RoundingMode.DOWN)
                             .multiply(price(unitItems(items)))
                             .setScale(2, RoundingMode.HALF_UP);
        }
    },
    BUYONEKILOHALFPRICE {
        public BigDecimal applyDiscounts(List<Item> items) {

            BigDecimal totalWeight = totalWeight(items, ItemByWeight.class);
            BigDecimal price = price(items);
            int compare = totalWeight.compareTo(BigDecimal.valueOf(1));
            return compare >= 0 ?
                   price.multiply(BigDecimal.valueOf(count(items)))
                        .divide(BigDecimal.valueOf(2)) : BigDecimal.ZERO;
        }
    },
    BUYTHREEFORTWO {
        public BigDecimal applyDiscounts(List<Item> items) {

            return BigDecimal.valueOf(count(unitItems(items)))
                             .divide(BigDecimal.valueOf(3), RoundingMode.DOWN)
                             .multiply(price(unitItems(items))).setScale(2,RoundingMode.HALF_UP);
        }
    },
    BUYTWOFORONEPOUND {
        public BigDecimal applyDiscounts(List<Item> items) {

            BigDecimal setPrice = BigDecimal.valueOf(1.00);

            BigDecimal remainder = price(items).multiply(BigDecimal.valueOf(count(items)%2));

            BigDecimal originalPrice = BigDecimal.valueOf(count(items))
                                                 .multiply(price(items))
                                                 .setScale(2,RoundingMode.HALF_UP);

            BigDecimal newPrice = BigDecimal.valueOf(count(items))
                                            .divide(BigDecimal.valueOf(2), RoundingMode.DOWN)
                                            .multiply(setPrice).setScale(2,RoundingMode.HALF_UP);

            return originalPrice.subtract(newPrice.add(remainder));
        }
    },
    NODISCOUNT{
        public BigDecimal applyDiscounts(List<Item> items) {
            return BigDecimal.ZERO;
        }
    };

    public abstract BigDecimal applyDiscounts(List<Item> items);


    private static long count(List<Item> items){
        return items.stream()
                    .count();
    }

    private static BigDecimal totalWeight(List<Item> items, Class<ItemByWeight> type) {
        return items.stream()
                    .filter(i -> i.product().getType().equals(PRODUCT_TYPE.WEIGHT))
                    .map(type::cast)
                    .map(iw -> iw.weight())
                    .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    private static BigDecimal price(List<Item> items){
        Optional<Item> item = items.stream()
                                   .findFirst();
        return item.isPresent() ? item.get().price():BigDecimal.ZERO;
    }

    private static List<Item> unitItems(List<Item> items){
        return items.stream()
                    .filter(i -> i.product().getType().equals(PRODUCT_TYPE.UNIT))
                    .collect(Collectors.toList());
    }

}
