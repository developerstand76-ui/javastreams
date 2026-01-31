package com.test.examples;

import com.test.model.Order;
import java.util.List;
import java.util.stream.Collectors;

public final class AggregationExample {

    private AggregationExample() {
    }

    // EXAMPLE 3: Calculate total revenue from completed orders
    public static void run(List<Order> orders) {
        // Input: List<Order> orders
        // Output: Prints total revenue from COMPLETED orders using loop, stream sum, and reduce
        System.out.println("\n--- Example 3: AGGREGATION (Total revenue from COMPLETED orders) ---");

        // TRADITIONAL APPROACH
        System.out.println("\nTraditional Loop:");
        double totalTraditional = 0.0;
        for (Order order : orders) {
            if ("COMPLETED".equals(order.getStatus())) {
                totalTraditional += order.getAmount();
            }
        }
        System.out.println("Total Revenue: $" + String.format("%.2f", totalTraditional));

        // STREAM APPROACH
        System.out.println("\nStream API:");
        double totalStream = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))
            .mapToDouble(Order::getAmount)
            .sum();
        System.out.println("Total Revenue: $" + String.format("%.2f", totalStream));

        // BONUS: Using reduce
        System.out.println("\nStream with reduce():");
        double totalReduce = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))
            .map(Order::getAmount)
            .reduce(0.0, Double::sum);
        System.out.println("Total Revenue: $" + String.format("%.2f", totalReduce));
    }
}
