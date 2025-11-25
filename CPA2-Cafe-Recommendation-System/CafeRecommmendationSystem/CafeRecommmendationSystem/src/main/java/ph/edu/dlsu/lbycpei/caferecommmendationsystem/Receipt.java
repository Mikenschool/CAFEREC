package ph.edu.dlsu.lbycpei.caferecommmendationsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt {

    public static void printReceipt(Order order) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("\n===============================");
        System.out.println("          CAFE RECEIPT         ");
        System.out.println("===============================");
        System.out.println("Date: " + dtf.format(LocalDateTime.now()));
        System.out.println();

        double total = 0;

        for (MenuItem item : order.getItems()) {
            System.out.printf("%-25s ₱%.2f%n", item.getName(), item.getPrice());
            total += item.getPrice();
        }

        System.out.println("-------------------------------");
        System.out.printf("%-25s ₱%.2f%n", "TOTAL:", total);
        System.out.println("===============================");
        System.out.println("     Thank you for ordering!   ");
        System.out.println("===============================\n");
    }
}