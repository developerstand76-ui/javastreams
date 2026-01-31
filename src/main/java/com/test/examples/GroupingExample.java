package com.test.examples;

import com.test.model.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class GroupingExample {

    private GroupingExample() {
    }

    // EXAMPLE 4: Group orders by status
    public static void run(List<Order> orders) {
        // Input: List<Order> orders
        // Output: Prints order counts grouped by status (traditional vs stream)
        System.out.println("\n--- Example 4: GROUPING (Group orders by status) ---");

        // TRADITIONAL APPROACH
        System.out.println("\nTraditional Loop:");
        Map<String, List<Order>> groupedTraditional = new HashMap<>();
        for (Order order : orders) {
            if (!groupedTraditional.containsKey(order.getStatus())) {
                groupedTraditional.put(order.getStatus(), new ArrayList<>());
            }
            groupedTraditional.get(order.getStatus()).add(order);
        }
        groupedTraditional.forEach((status, orderList) -> {
            System.out.println(status + ": " + orderList.size() + " orders");
        });

        // STREAM APPROACH
        System.out.println("\nStream API:");
        Map<String, List<Order>> groupedStream = orders.stream()
            .collect(Collectors.groupingBy(Order::getStatus));
        groupedStream.forEach((status, orderList) -> {
            System.out.println(status + ": " + orderList.size() + " orders");
        });

        // BONUS: Count by status
        System.out.println("\nStream with counting:");
        Map<String, Long> countByStatus = orders.stream()
            .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
        System.out.println(countByStatus);
    }
}
