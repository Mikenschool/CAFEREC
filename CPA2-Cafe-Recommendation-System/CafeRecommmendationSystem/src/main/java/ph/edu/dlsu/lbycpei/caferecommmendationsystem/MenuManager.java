package ph.edu.dlsu.lbycpei.caferecommmendationsystem;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    private static MenuManager instance;

    private final List<MenuItem> menuItems = new ArrayList<>();

    private MenuManager() {}

    public static MenuManager getInstance() {
        if (instance == null) {
            instance = new MenuManager();
        }
        return instance;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void addItem(MenuItem item) {
        menuItems.add(item);
    }

    public void removeItem(MenuItem item) {
        menuItems.remove(item);
    }

    public MenuItem getItemByName(String name) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}
