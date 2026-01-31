package com.test.examples;

import com.test.model.Order;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class MappingExample {

    private MappingExample() {
    }

    // EXAMPLE 2: Extracting customer IDs
    public static void run(List<Order> orders) {
        // Input: List<Order> orders
        //   O1: C1, $150.50, COMPLETED
        //   O2: C2, $89.99, PENDING
        //   O3: C1, $200.00, COMPLETED
        //   O4: C3, $45.00, CANCELLED
        //   O5: C2, $320.75, COMPLETED
        // Output: Prints unique customer IDs from orders (traditional vs stream)
        // Expected Output:
        //   Customer IDs: [C3, C1, C2] (order may vary due to Set)
        System.out.println("\n--- Example 2: MAPPING (Extract unique customer IDs) ---");

        // TRADITIONAL APPROACH
        System.out.println("\nTraditional Loop:");
        Set<String> customerIdsTraditional = new HashSet<>();
        for (Order order : orders) {
            customerIdsTraditional.add(order.getCustomerId());
        }
        System.out.println("Customer IDs: " + customerIdsTraditional);

        // STREAM APPROACH
        System.out.println("\nStream API:");
        // stream() - converts collection to stream
        // map() - transforms each element using the provided function (Order -> String)
        // collect() - gathers results into a Set (automatically removes duplicates)
        Set<String> customerIdsStream = orders.stream()
            .map(Order::getCustomerId)           // extract customerId from each order
            .collect(Collectors.toSet());        // collect to Set (deduplicates)
        System.out.println("Customer IDs: " + customerIdsStream);
    }
}
