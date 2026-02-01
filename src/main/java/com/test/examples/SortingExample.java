package com.test.examples;

import com.test.model.Order;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class SortingExample {

    private SortingExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Sorting Example - Run Independently");
        System.out.println("========================================");
        
        // Sample input data
        List<Order> orders = createSampleOrders();
        
        // Run the sorting examples
        run(orders);
    }

    private static List<Order> createSampleOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("O1", "C1", 150.50, "COMPLETED", List.of("Item1", "Item2")));
        orders.add(new Order("O2", "C2", 89.99, "PENDING", List.of("Item3")));
        orders.add(new Order("O3", "C1", 200.00, "COMPLETED", List.of("Item4")));
        orders.add(new Order("O4", "C3", 45.00, "CANCELLED", List.of("Item7")));
        orders.add(new Order("O5", "C2", 320.75, "COMPLETED", List.of("Item8")));
        return orders;
    }

    // EXAMPLE: Various sorting scenarios
    public static void run(List<Order> orders) {
        // Scenario 1: Sort by amount (ascending)
        System.out.println("\n--- Scenario 1: Sort by Amount (Ascending) ---");
        orders.stream()
            .sorted(Comparator.comparingDouble(Order::getAmount))
            .forEach(order -> System.out.println(order.getId() + ": $" + order.getAmount()));

        // Scenario 2: Sort by amount (descending)
        System.out.println("\n--- Scenario 2: Sort by Amount (Descending) ---");
        orders.stream()
            .sorted(Comparator.comparingDouble(Order::getAmount).reversed())
            .forEach(order -> System.out.println(order.getId() + ": $" + order.getAmount()));

        // Scenario 3: Sort by status (alphabetically)
        System.out.println("\n--- Scenario 3: Sort by Status (Alphabetically) ---");
        orders.stream()
            .sorted(Comparator.comparing(Order::getStatus))
            .forEach(order -> System.out.println(order.getId() + ": " + order.getStatus()));

        // Scenario 4: Sort by customer ID, then by amount (descending)
        System.out.println("\n--- Scenario 4: Sort by Customer ID, then by Amount (Descending) ---");
        orders.stream()
            .sorted(Comparator.comparing(Order::getCustomerId)
                .thenComparingDouble(Order::getAmount).reversed())
            .forEach(order -> System.out.println(order.getCustomerId() + " - " + order.getId() + ": $" + order.getAmount()));

        // Scenario 5: Sort by status, then by customer ID
        System.out.println("\n--- Scenario 5: Sort by Status, then by Customer ID ---");
        orders.stream()
            .sorted(Comparator.comparing(Order::getStatus)
                .thenComparing(Order::getCustomerId))
            .forEach(order -> System.out.println(order.getStatus() + " - " + order.getCustomerId() + " (" + order.getId() + ")"));

        // Scenario 6: Sort by number of items in order (descending)
        System.out.println("\n--- Scenario 6: Sort by Number of Items (Descending) ---");
        orders.stream()
            .sorted(Comparator.comparingInt((Order o) -> o.getItems().size()).reversed())
            .forEach(order -> System.out.println(order.getId() + ": " + order.getItems().size() + " items"));

        // Scenario 7: Sort COMPLETED orders by amount (descending), then PENDING by customer
        System.out.println("\n--- Scenario 7: Sort COMPLETED Orders by Amount, PENDING by Customer ---");
        orders.stream()
            .sorted((o1, o2) -> {
                if ("COMPLETED".equals(o1.getStatus()) && "COMPLETED".equals(o2.getStatus())) {
                    return Double.compare(o2.getAmount(), o1.getAmount()); // Descending by amount
                } else if ("PENDING".equals(o1.getStatus()) && "PENDING".equals(o2.getStatus())) {
                    return o1.getCustomerId().compareTo(o2.getCustomerId()); // Ascending by customer
                }
                return o1.getStatus().compareTo(o2.getStatus()); // Default: sort by status
            })
            .forEach(order -> System.out.println(order.getId() + " (" + order.getStatus() + "): $" + order.getAmount() + " - " + order.getCustomerId()));
    }
}
