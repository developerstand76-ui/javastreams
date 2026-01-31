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

        // STREAM APPROACH
        System.out.println("\nStream API:");
        // Multi-stage stream pipeline:
        // 1. filter() - keep only COMPLETED orders
        // 2. groupingBy() - group by customerId with summingDouble downstream collector
        //    summingDouble() - sums the amounts for each customer
        // 3. entrySet() - get map entries, then stream() to create new stream
        // 4. sorted() - sort entries by value in descending order
        //    comparingByValue() - compares map entries by value
        //    reversed() - reverses sort order (highest first)
        // 5. limit() - keeps only first N elements (top 2)
        // 6. forEach() - terminal operation to print each entry
        orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))  // step 1: filter
            .collect(Collectors.groupingBy(                          // step 2: group & sum
                Order::getCustomerId,
                Collectors.summingDouble(Order::getAmount)
            ))
            .entrySet()                                              // get entries
            .stream()                                                // step 3: new stream
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())  // step 4: sort desc
            .limit(2)                                                // step 5: top 2
            .forEach(entry ->                                        // step 6: print
                System.out.println(entry.getKey() + ": $" + String.format("%.2f", entry.getValue()))
            );
    }
}
