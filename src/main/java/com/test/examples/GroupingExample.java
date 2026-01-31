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

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Grouping Example - Run Independently");
        System.out.println("========================================");
        
        // Sample input data
        List<Order> orders = createSampleOrders();
        
        // Run the grouping example
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

    // EXAMPLE 4: Group orders by status
    public static void run(List<Order> orders) {
        // Input: List<Order> orders
        //   O1: C1, $150.50, COMPLETED
        //   O2: C2, $89.99, PENDING
        //   O3: C1, $200.00, COMPLETED
        //   O4: C3, $45.00, CANCELLED
        //   O5: C2, $320.75, COMPLETED
        // Output: Prints order counts grouped by status (traditional vs stream)
        // Expected Output:
        //   CANCELLED: 1 orders
        //   COMPLETED: 3 orders
        //   PENDING: 1 orders
        //   {CANCELLED=1, COMPLETED=3, PENDING=1}
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
        // stream() - converts collection to stream
        // collect() - terminal operation with Collector
        // groupingBy() - groups elements by classifier function (status -> List<Order>)
        Map<String, List<Order>> groupedStream = orders.stream()
            .collect(Collectors.groupingBy(Order::getStatus));  // group by status field
        groupedStream.forEach((status, orderList) -> {
            System.out.println(status + ": " + orderList.size() + " orders");
        });

        // BONUS: Count by status
        System.out.println("\nStream with counting:");
        // groupingBy() with downstream collector
        // counting() - downstream collector that counts elements in each group
        Map<String, Long> countByStatus = orders.stream()
            .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
        System.out.println(countByStatus);
    }
}
