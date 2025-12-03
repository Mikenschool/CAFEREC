//hellooooo changes madeeee

package ph.edu.dlsu.lbycpei.caferecommmendationsystem;

import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;

public class CafeSystem {

    private Menu menu = new Menu();
    private RecommendationGraph graph = new RecommendationGraph();
    private Similarity similarity = new Similarity();
    private Scanner sc = new Scanner(System.in);

    public CafeSystem() {
        loadSampleMenu();
    }

    private void loadSampleMenu() {
        menu.addItem(new MenuItem("Matcha Latte", 150));
        menu.addItem(new MenuItem("Espresso", 120));
        menu.addItem(new MenuItem("Dubai Chocolate Brownie", 95));
        menu.addItem(new MenuItem("Iced Tea", 100));
        menu.addItem(new MenuItem("Lemonade", 80));
    }

    public void run() {
        while (true) {
            System.out.println("\n1. View Menu");
            System.out.println("2. Place Order");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> menu.displayMenu();
                case 2 -> placeOrder();
                case 3 -> { System.out.println("Goodbye!"); return; }
            }
        }
    }

    private void placeOrder() {
        Order order = new Order();
        menu.displayMenu();

        while (true) {
            System.out.print("Enter item name (or 'done'): ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("done")) break;

            MenuItem item = menu.getItemByName(input);
            if (item != null) {
                order.addItem(item);
                System.out.println("Added: " + item.getName());
            } else {
                System.out.println("Item not found.");
            }
        }

        graph.updateGraph(order.getItems());

        addRecommendedItems(order);

        Receipt.printReceipt(order);
    }

    private void addRecommendedItems(Order order) {

        while (true) {
            System.out.println("\n--- Recommended Items ---");

            HashSet<String> recommendations = new HashSet<>();

            for (MenuItem item : order.getItems()) {
                String itemName = item.getName();

                String paired = graph.getTopRecommendation(itemName);
                if (paired != null
                        && menu.getItemByName(paired) != null
                        && !order.containsItem(paired)) {
                    recommendations.add(paired);
                }

                var list = similarity.getSimilarItems(itemName);
                for (String sim : list) {

                    if (menu.getItemByName(sim) != null
                            && !order.containsItem(sim)) {
                        recommendations.add(sim);
                    }
                }
            }

            if (recommendations.isEmpty()) {
                System.out.println("No recommendations available.");
                return;
            }

            int index = 1;
            ArrayList<String> recList = new ArrayList<>(recommendations);
            for (String r : recList) {
                System.out.println(index + ". " + r);
                index++;
            }

            System.out.print("Would you like to add any recommended item? (name or 'done'): ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("done")) break;

            MenuItem recItem = menu.getItemByName(input);

            if (recItem != null && recList.contains(recItem.getName())) {
                order.addItem(recItem);
                System.out.println("Added: " + recItem.getName());

                graph.updateGraph(order.getItems());
            } else {
                System.out.println("Invalid recommendation.");
            }
        }
    }

}
