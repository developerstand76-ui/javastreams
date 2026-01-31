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
        // Input: List<Order> orders
        //   O1: C1, $150.50, COMPLETED
        //   O2: C2, $89.99, PENDING
        //   O3: C1, $200.00, COMPLETED
        //   O4: C3, $45.00, CANCELLED
        //   O5: C2, $320.75, COMPLETED
        // Output: Prints top 2 customers by completed-order spending (traditional vs stream)
        // Expected Output:
        //   C1: $350.50 (150.50 + 200.00)
        //   C2: $320.75
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

        // STREAM APPROACH WITH STEP-BY-STEP OUTPUT
        System.out.println("\nStream API (with step-by-step output):");
        
        // Step 1: Filter - keep only COMPLETED orders
        System.out.println("\nStep 1: Filter COMPLETED orders");
        List<Order> completedOrders = orders.stream()
            .filter(order -> {
                boolean isCompleted = "COMPLETED".equals(order.getStatus());
                if (isCompleted) {
                    System.out.println("  ✓ " + order.getId() + " -> Customer: " + order.getCustomerId() + ", Amount: $" + order.getAmount());
                }
                return isCompleted;
            })
            .collect(Collectors.toList());
        System.out.println("  Result: " + completedOrders.size() + " completed orders");

        // Step 2: Group by Customer ID and Sum Amounts
        System.out.println("\nStep 2: Group by Customer & Sum Amounts");
        Map<String, Double> customerSpending = completedOrders.stream()
            .collect(Collectors.groupingBy(
                Order::getCustomerId,
                Collectors.summingDouble(Order::getAmount)
            ));
        System.out.println("  Grouped Map:");
        customerSpending.forEach((customer, total) ->
            System.out.println("    " + customer + " -> $" + String.format("%.2f", total))
        );

        // Step 3: Why entrySet()? - Convert Map to Set of Entries
        System.out.println("\nStep 3: Convert Map to entrySet()");
        System.out.println("  Why entrySet()? Maps are NOT streams. We need entrySet() to convert");
        System.out.println("  the Map<String, Double> into a Set<Map.Entry<String, Double>>");
        System.out.println("  Then .stream() converts that Set into a Stream we can use!");
        System.out.println("  Entries created:");
        customerSpending.entrySet().forEach(entry ->
            System.out.println("    Entry[key=" + entry.getKey() + ", value=$" + String.format("%.2f", entry.getValue()) + "]")
        );

        // Step 4: Sort by Amount (Descending)
        System.out.println("\nStep 4: Sort by Amount (Highest First)");
        List<Map.Entry<String, Double>> sortedEntries = customerSpending.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .collect(Collectors.toList());
        System.out.println("  Sorted result:");
        for (int i = 0; i < sortedEntries.size(); i++) {
            Map.Entry<String, Double> entry = sortedEntries.get(i);
            System.out.println("    [" + (i + 1) + "] " + entry.getKey() + " -> $" + String.format("%.2f", entry.getValue()));
        }

        // Step 5: Limit to Top 2
        System.out.println("\nStep 5: Limit to Top 2");
        List<Map.Entry<String, Double>> topTwo = customerSpending.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(2)
            .collect(Collectors.toList());
        System.out.println("  Top 2 customers:");
        for (int i = 0; i < topTwo.size(); i++) {
            Map.Entry<String, Double> entry = topTwo.get(i);
            System.out.println("    [" + (i + 1) + "] " + entry.getKey() + " -> $" + String.format("%.2f", entry.getValue()));
        }

        // Final Result: Print Top 2
        System.out.println("\nFinal Result (Top 2 Customers by Spending):");
        customerSpending.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(2)
            .forEach(entry ->
                System.out.println("  " + entry.getKey() + ": $" + String.format("%.2f", entry.getValue()))
            );
    }
}
