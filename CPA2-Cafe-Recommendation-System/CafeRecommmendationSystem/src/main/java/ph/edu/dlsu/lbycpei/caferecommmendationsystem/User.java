package ph.edu.dlsu.lbycpei.caferecommmendationsystem;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Order> orderHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.orderHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void addOrderToHistory(Order order) {
        this.orderHistory.add(order);
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }
}