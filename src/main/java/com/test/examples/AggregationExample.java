package com.test.examples;

import com.test.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class AggregationExample {

    private AggregationExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Aggregation Example - Run Independently");
        System.out.println("========================================");
        
        // Sample input data
        List<Order> orders = createSampleOrders();
        
        // Run the aggregation example
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

    // EXAMPLE 3: Calculate total revenue from completed orders
    public static void run(List<Order> orders) {
        // Input: List<Order> orders
        //   O1: C1, $150.50, COMPLETED
        //   O2: C2, $89.99, PENDING
        //   O3: C1, $200.00, COMPLETED
        //   O4: C3, $45.00, CANCELLED
        //   O5: C2, $320.75, COMPLETED
        // Output: Prints total revenue from COMPLETED orders using loop, stream sum, and reduce
        // Expected Output:
        //   Total Revenue: $671.25 (150.50 + 200.00 + 320.75)
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
        // stream() - converts collection to stream
        // filter() - keeps only COMPLETED orders
        // mapToDouble() - extracts amount as primitive double (more efficient than boxed Double)
        // sum() - terminal operation that adds all double values
        double totalStream = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))
            .mapToDouble(Order::getAmount)       // extract as primitive double
            .sum();                              // sum all values
        System.out.println("Total Revenue: $" + String.format("%.2f", totalStream));

        // BONUS: Using reduce
        System.out.println("\nStream with reduce():");
        // reduce() - combines elements using accumulator function (identity, accumulator)
        // identity: initial value (0.0), accumulator: how to combine two values (Double::sum)
        double totalReduce = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))
            .map(Order::getAmount)               // extract amount as Double
            .reduce(0.0, Double::sum);           // reduce: (0.0 + a1 + a2 + a3...)
        System.out.println("Total Revenue: $" + String.format("%.2f", totalReduce));
    }
}
