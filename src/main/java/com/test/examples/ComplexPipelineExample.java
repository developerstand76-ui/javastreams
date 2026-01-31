package com.test.examples;

import com.test.model.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ComplexPipelineExample {

    private ComplexPipelineExample() {
    }

    // EXAMPLE 5: Complex pipeline - Top 2 customers by total spending
    public static void run(List<Order> orders) {
        // Input: List<Order> orders
        // Output: Prints top 2 customers by completed-order spending (traditional vs stream)
        System.out.println("\n--- Example 5: COMPLEX PIPELINE (Top 2 customers by spending) ---");

        // TRADITIONAL APPROACH
        System.out.println("\nTraditional Loop:");
        Map<String, Double> customerSpendingTraditional = new HashMap<>();
        for (Order order : orders) {
            if ("COMPLETED".equals(order.getStatus())) {
                customerSpendingTraditional.put(
                    order.getCustomerId(),
                    customerSpendingTraditional.getOrDefault(order.getCustomerId(), 0.0) + order.getAmount()
                );
            }
        }
        List<Map.Entry<String, Double>> sortedTraditional = new ArrayList<>(customerSpendingTraditional.entrySet());
        sortedTraditional.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        for (int i = 0; i < Math.min(2, sortedTraditional.size()); i++) {
            Map.Entry<String, Double> entry = sortedTraditional.get(i);
            System.out.println(entry.getKey() + ": $" + String.format("%.2f", entry.getValue()));
        }

        // STREAM APPROACH
        System.out.println("\nStream API:");
        orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))
            .collect(Collectors.groupingBy(
                Order::getCustomerId,
                Collectors.summingDouble(Order::getAmount)
            ))
            .entrySet()
            .stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(2)
            .forEach(entry ->
                System.out.println(entry.getKey() + ": $" + String.format("%.2f", entry.getValue()))
            );
    }
}
