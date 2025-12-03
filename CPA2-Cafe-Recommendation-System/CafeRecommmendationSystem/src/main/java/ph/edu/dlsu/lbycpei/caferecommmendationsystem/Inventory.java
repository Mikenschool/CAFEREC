package ph.edu.dlsu.lbycpei.caferecommmendationsystem;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<InventoryItem> items = new ArrayList<>();

    public void addItem(InventoryItem item) {
        items.add(item);
    }

    public List<InventoryItem> getItems() {
        return items;
    }

    public InventoryItem getItemByName(String name) {
        for (InventoryItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public boolean removeItem(InventoryItem toRemove) {
        return items.remove(toRemove);
    }

    public boolean reduceStock(String name) {
        InventoryItem item = getItemByName(name);
        if (item == null) return false;

        int updated = item.getQuantity() - 1;
        if (updated <= 0) {
            items.remove(item);
        } else {
            item.setQuantity(updated);
        }
        return true;
    }
}
