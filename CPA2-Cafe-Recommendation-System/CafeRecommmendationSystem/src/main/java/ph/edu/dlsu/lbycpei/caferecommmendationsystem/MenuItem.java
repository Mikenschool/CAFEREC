package ph.edu.dlsu.lbycpei.caferecommmendationsystem;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private String name;
    private double price;
    private List<String> recommendedItems = new ArrayList<>();

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return name + " - ₱" + price;
    }

    public List<String> getRecommendedItems() {
        return recommendedItems;
    }

    public void setRecommendedItems(List<String> list) {
        this.recommendedItems = list != null ? list : new ArrayList<>();
    }
}
