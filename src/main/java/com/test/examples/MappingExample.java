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
        // Output: Prints unique customer IDs from orders (traditional vs stream)
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
        Set<String> customerIdsStream = orders.stream()
            .map(Order::getCustomerId)
            .collect(Collectors.toSet());
        System.out.println("Customer IDs: " + customerIdsStream);
    }
}
