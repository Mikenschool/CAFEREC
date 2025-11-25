package ph.edu.dlsu.lbycpei.caferecommmendationsystem;

public class MenuItem {
    private String name;
    private double price;
    private String category;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " - ₱" + price;
    }
}