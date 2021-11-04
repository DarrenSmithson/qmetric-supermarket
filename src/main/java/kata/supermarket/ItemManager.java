package kata.supermarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {

    public static Map<String, List<Item>> itemsByProdCode(List<Item> items) {
        Map<String, List<Item>> prodItems = new HashMap<>();

        for(Item item : items) {
            prodItems.computeIfAbsent(item.product().getCode(), c -> new ArrayList<>()).add(item);
        }

        return prodItems;
    }

}
