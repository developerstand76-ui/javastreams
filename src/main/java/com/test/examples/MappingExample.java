package com.test.examples;

import com.test.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class MappingExample {

    private MappingExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Mapping Example - Run Independently");
        System.out.println("========================================");
        
        // Sample input data
        List<Order> orders = createSampleOrders();
        
        // Run the mapping example
        run(orders);
    }

    private static List<Order> createSampleOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("O1", "C1", 150.50, "COMPLETED", List.of("Item1")));
        orders.add(new Order("O2", "C2", 89.99, "PENDING", List.of("Item3")));
        orders.add(new Order("O3", "C1", 200.00, "COMPLETED", List.of("Item4")));
        orders.add(new Order("O4", "C3", 45.00, "CANCELLED", List.of("Item7")));
        return orders;
    }

    // EXAMPLE 2: Extracting customer IDs
    public static void run(List<Order> orders) {
        Set<String> customerIdsStream = orders.stream()
            .map(Order::getCustomerId)
            .collect(Collectors.toSet());
        System.out.println("Customer IDs: " + customerIdsStream);
    }
}
