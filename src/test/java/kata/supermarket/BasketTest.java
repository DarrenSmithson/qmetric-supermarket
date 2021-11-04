package kata.supermarket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketTest {

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, String expectedTotal, Iterable<Item> items) {
        final Basket basket = new Basket();
        items.forEach(basket::add);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
                noItems(),
                aSingleItemPricedPerUnit(),
                multipleItemsPricedPerUnit(),
                aSingleItemPricedByWeight(),
                multipleItemsPricedByWeight(),
                buyFourGetTwoFreeDiscount(),
                buyFiveGetTwoFreeDiscount(),
                buyOneGetNoneFreeDiscount(),
                buyTwoForOnePoundDiscount(),
                buyTwoForOnePoundPlusExtraDiscount(),
                buyThreeForTwoForTwoDiscount(),
                buyThreeForTwoForThreeDiscount(),
                buyThreeForTwoForFourDiscount(),
                buyOneKiloHalfPriceLessThanKiloDiscount(),
                buyOneKiloHalfPriceMoreThanKiloDiscount(),
                buyOneKiloHalfPriceMoreThanKiloOneItemDiscount(),
                buyMixedUnitProducts(),
                buyCrispsAndPopcorn(),
                buyCrispsAndPopcornRev(),
                buyMealDeal()

        );
    }

    private static Arguments buyMealDeal() {
        return Arguments.of("buy 1 meal deal", "4.00",
                            Arrays.asList(aDrink(), aSandwich(), anApple()));
    }

    private static Arguments buyCrispsAndPopcorn() {
        return Arguments.of("buy 1 bag of crisps and 1 bag of popcorn", "3.40",
                            Arrays.asList(aBagOfCrisps(), aBagOfPopcorn()));
    }

    private static Arguments buyCrispsAndPopcornRev() {
        return Arguments.of("buy 1 bag of popcorn and 1 bag of crisps", "3.40",
                            Arrays.asList(aBagOfPopcorn(), aBagOfCrisps()));
    }

    private static Arguments buyMixedUnitProducts() {
        return Arguments.of("buy 2 bag of crisps and 2 bottles of wine", "12.20",
                            Arrays.asList(aBagOfCrisps(), aBagOfCrisps(), aBottleOfWine(), aBottleOfWine()));
    }

    private static Arguments buyOneKiloHalfPriceMoreThanKiloDiscount() {
        return Arguments.of("buy 1 kilo for half price for more than kilo weight", "0.96",
                            Arrays.asList(aBagOfCarrots(), aBagOfCarrots()));
    }

    private static Arguments buyOneKiloHalfPriceLessThanKiloDiscount() {
        return Arguments.of("buy 1 kilo for half price for less than kilo weight", "0.96",
                            Collections.singleton(aBagOfCarrots()));
    }

    private static Arguments buyOneKiloHalfPriceMoreThanKiloOneItemDiscount() {
        return Arguments.of("buy 1 kilo for half price for less than kilo weight", "1.80",
                            Collections.singleton(aBagOfTomatoes()));
    }

    private static Arguments buyThreeForTwoForTwoDiscount() {
        return Arguments.of("buy 3 for 2 discount for 2", "11.00",
                            Arrays.asList(aBottleOfWine(), aBottleOfWine()));
    }

    private static Arguments buyThreeForTwoForThreeDiscount() {
        return Arguments.of("buy 3 for 2 discount for 3", "11.00",
                            Arrays.asList(aBottleOfWine(), aBottleOfWine(), aBottleOfWine()));
    }

    private static Arguments buyThreeForTwoForFourDiscount() {
        return Arguments.of("buy 3 for 2 discount for 4", "16.50",
                            Arrays.asList(aBottleOfWine(), aBottleOfWine(), aBottleOfWine(), aBottleOfWine()));
    }

    private static Arguments buyTwoForOnePoundDiscount() {
        return Arguments.of("buy 2 for 1 pound", "1.00",
                            Arrays.asList(aMarsBar(), aMarsBar()));
    }


    private static Arguments buyTwoForOnePoundPlusExtraDiscount() {
        return Arguments.of("buy 2 for 1 pound plus extra 1", "0.80",
                            Collections.singleton((aMarsBar())));
    }

    private static Arguments buyOneGetNoneFreeDiscount() {
        return Arguments.of("buy 1 get 0 free", "1.20",Collections.singleton(aBagOfCrisps()));
    }

    private static Arguments buyFourGetTwoFreeDiscount() {
        return Arguments.of("buy 4 get 2 free", "2.40",
                            Arrays.asList(aBagOfCrisps(), aBagOfCrisps(), aBagOfCrisps(), aBagOfCrisps()));
    }

    private static Arguments buyFiveGetTwoFreeDiscount() {
        return Arguments.of("buy 5 get 2 free", "3.60",
                            Arrays.asList(aBagOfCrisps(), aBagOfCrisps(), aBagOfCrisps(), aBagOfCrisps(),
                                          aBagOfCrisps()));
    }

    private static Arguments aSingleItemPricedByWeight() {
        return Arguments.of("a single weighed item", "1.25", Collections.singleton(twoFiftyGramsOfAmericanSweets()));
    }

    private static Arguments multipleItemsPricedByWeight() {
        return Arguments.of("multiple weighed items", "1.85",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), twoHundredGramsOfPickAndMix())
        );
    }

    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "2.04",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()));
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.49", Collections.singleton(aPintOfMilk()));
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        return new UnitProduct(new BigDecimal("0.49"), DiscountStrategy.NODISCOUNT, "milk").oneOf();
    }

    private static Item aPackOfDigestives() {
        return new UnitProduct(new BigDecimal("1.55"), DiscountStrategy.NODISCOUNT, "digestives").oneOf();
    }

    private static Item aBagOfCarrots() {
        return new WeighedProduct(new BigDecimal("1.20"),
                                  DiscountStrategy.BUYONEKILOHALFPRICE, "carrots").weighing(BigDecimal.valueOf(0.8));
    }

    private static Item aBagOfTomatoes() {
        return new WeighedProduct(new BigDecimal("1.20"),
                                  DiscountStrategy.BUYONEKILOHALFPRICE, "tomatoes").weighing(BigDecimal.valueOf(3.0));
    }

    private static Item aBagOfCrisps() {
        return new UnitProduct(new BigDecimal("1.20"), DiscountStrategy.BUYONEGETONEFREE, "crisps").oneOf();
    }

    private static Item aDrink() {
        return new UnitProduct(new BigDecimal("2.00"), DiscountStrategy.BUYMEALDEAL, "drink").oneOf();
    }

    private static Item aSandwich() {
        return new UnitProduct(new BigDecimal("3.00"), DiscountStrategy.BUYMEALDEAL, "sandwich").oneOf();
    }

    private static Item anApple() {
        return new UnitProduct(new BigDecimal("0.80"), DiscountStrategy.BUYMEALDEAL, "apple").oneOf();
    }

    private static Item aBagOfPopcorn() {
        return new UnitProduct(new BigDecimal("2.20"), DiscountStrategy.BUYONEGETONEFREE, "popcorn").oneOf();
    }

    private static Item aBottleOfWine() {
        return new UnitProduct(new BigDecimal("5.50"), DiscountStrategy.BUYTHREEFORTWO, "wine").oneOf();
    }

    private static Item aMarsBar() {
        return new UnitProduct(new BigDecimal("0.80"), DiscountStrategy.BUYTWOFORONEPOUND, "mars").oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        return new WeighedProduct(new BigDecimal("4.99"), DiscountStrategy.NODISCOUNT, "sweets");
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static WeighedProduct aKiloOfPickAndMix() {
        return new WeighedProduct(new BigDecimal("2.99"), DiscountStrategy.NODISCOUNT, "pandm");
    }

    private static Item twoHundredGramsOfPickAndMix() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".2"));
    }
}
