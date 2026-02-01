package com.test.examples;

import com.test.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ComplexPipelineExample {

    private ComplexPipelineExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Complex Pipeline Example - Run Independently");
        System.out.println("========================================");
        
        // Sample input data
        List<Order> orders = createSampleOrders();
        
        // Run the complex pipeline example
        run(orders);
        
    }

    private static List<Order> createSampleOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("O1", "C1", 150.50, "COMPLETED", List.of("Item1")));
        orders.add(new Order("O2", "C2", 89.99, "PENDING", List.of("Item3")));
        orders.add(new Order("O3", "C1", 200.00, "COMPLETED", List.of("Item4")));
        orders.add(new Order("O4", "C3", 45.00, "CANCELLED", List.of("Item7")));
        orders.add(new Order("O5", "C2", 320.75, "COMPLETED", List.of("Item8")));
        return orders;
    }

    // EXAMPLE 5: Complex pipeline - Top 2 customers by total spending
    public static void run(List<Order> orders) {
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
