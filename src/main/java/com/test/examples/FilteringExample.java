package com.test.examples;

import com.test.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class FilteringExample {

    private FilteringExample() {
    }

    // EXAMPLE 1: Filtering completed orders with amount > 100
    public static void run(List<Order> orders) {
        // Input: List<Order> orders
        // Output: Prints orders with status COMPLETED and amount > 100 using loop and stream
        System.out.println("\n--- Example 1: FILTERING (Orders: COMPLETED & amount > 100) ---");

        // TRADITIONAL APPROACH
        System.out.println("\nTraditional Loop:");
        List<Order> filteredTraditional = new ArrayList<>();
        for (Order order : orders) {
            if ("COMPLETED".equals(order.getStatus()) && order.getAmount() > 100) {
                filteredTraditional.add(order);
            }
        }
        filteredTraditional.forEach(System.out::println);

        // STREAM APPROACH
        System.out.println("\nStream API:");
        List<Order> filteredStream = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))
            .filter(order -> order.getAmount() > 100)
            .collect(Collectors.toList());
        filteredStream.forEach(System.out::println);
    }
}
