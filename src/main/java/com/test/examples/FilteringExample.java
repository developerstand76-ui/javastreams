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
        //   O1: C1, $150.50, COMPLETED
        //   O2: C2, $89.99, PENDING
        //   O3: C1, $200.00, COMPLETED
        //   O4: C3, $45.00, CANCELLED
        //   O5: C2, $320.75, COMPLETED
        // Output: Prints orders with status COMPLETED and amount > 100 using loop and stream
        // Expected Output:
        //   Order{id='O1', customer='C1', amount=150.5, status='COMPLETED'}
        //   Order{id='O3', customer='C1', amount=200.0, status='COMPLETED'}
        //   Order{id='O5', customer='C2', amount=320.75, status='COMPLETED'}
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
        // stream() - converts collection to stream for processing
        // filter() - keeps only elements matching the predicate (returns Stream<Order>)
        // collect() - terminal operation that gathers stream elements into a collection
        List<Order> filteredStream = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))  // filter by status
            .filter(order -> order.getAmount() > 100)                // filter by amount
            .collect(Collectors.toList());                           // collect to List
        filteredStream.forEach(System.out::println);
    }
}
