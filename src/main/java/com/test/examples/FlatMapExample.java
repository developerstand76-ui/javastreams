package com.test.examples;

import com.test.model.Order;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FlatMapExample {

    private FlatMapExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("FlatMap Example - Run Independently");
        System.out.println("========================================");
        
        run();
    }

    public static void run() {
        // WHAT IS FLATMAP?
        // flatMap "flattens" nested structures (like lists within lists) into a single stream
        // Think of it as: map + flatten
        
        System.out.println("\n--- 1. MAP vs FLATMAP ---");
        List<List<Integer>> nestedNumbers = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5),
            Arrays.asList(6, 7, 8, 9)
        );
        
        System.out.println("Original nested list: " + nestedNumbers);
        
        // Using MAP - returns Stream<List<Integer>>
        // Each inner list stays as a list
        System.out.println("\nUsing map() - keeps nested structure:");
        List<List<Integer>> mappedResult = nestedNumbers.stream()
            .map(list -> list)  // Returns Stream<List<Integer>>
            .collect(Collectors.toList());
        System.out.println("Result: " + mappedResult);
        System.out.println("Type: List<List<Integer>>");
        
        // Using FLATMAP - returns Stream<Integer>
        // Flattens all inner lists into single stream
        System.out.println("\nUsing flatMap() - flattens to single list:");
        List<Integer> flatMappedResult = nestedNumbers.stream()
            .flatMap(list -> list.stream())  // Flattens to Stream<Integer>
            .collect(Collectors.toList());
        System.out.println("Result: " + flatMappedResult);
        System.out.println("Type: List<Integer>");

        // EXAMPLE 2: Extract all items from multiple orders
        System.out.println("\n--- 2. EXTRACT ALL ITEMS FROM ORDERS ---");
        List<Order> orders = Arrays.asList(
            new Order("O1", "C1", 100.0, "COMPLETED", Arrays.asList("Laptop", "Mouse", "Keyboard")),
            new Order("O2", "C2", 200.0, "COMPLETED", Arrays.asList("Monitor", "HDMI Cable")),
            new Order("O3", "C3", 150.0, "COMPLETED", Arrays.asList("Desk", "Chair"))
        );
        
        System.out.println("Orders:");
        orders.forEach(o -> System.out.println("  " + o.getId() + ": " + o.getItems()));
        
        // Using MAP - gives us List<List<String>>
        System.out.println("\nUsing map() - List<List<String>>:");
        List<List<String>> allItemsNested = orders.stream()
            .map(Order::getItems)
            .collect(Collectors.toList());
        System.out.println("  " + allItemsNested);
        
        // Using FLATMAP - gives us List<String>
        System.out.println("\nUsing flatMap() - List<String>:");
        List<String> allItemsFlat = orders.stream()
            .flatMap(order -> order.getItems().stream())
            .collect(Collectors.toList());
        System.out.println("  " + allItemsFlat);

        // EXAMPLE 3: Split sentences into words
        System.out.println("\n--- 3. SPLIT SENTENCES INTO WORDS ---");
        List<String> sentences = Arrays.asList(
            "Java Streams are powerful",
            "FlatMap flattens nested structures",
            "Learn functional programming"
        );
        
        System.out.println("Sentences:");
        sentences.forEach(s -> System.out.println("  \"" + s + "\""));
        
        // Using MAP - gives List<String[]> (array of words per sentence)
        System.out.println("\nUsing map() - List<String[]>:");
        List<String[]> wordsArrays = sentences.stream()
            .map(sentence -> sentence.split(" "))
            .collect(Collectors.toList());
        System.out.println("  Result: " + wordsArrays.size() + " arrays");
        wordsArrays.forEach(arr -> System.out.println("    " + Arrays.toString(arr)));
        
        // Using FLATMAP - gives List<String> (all words in single list)
        System.out.println("\nUsing flatMap() - List<String>:");
        List<String> allWords = sentences.stream()
            .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
            .collect(Collectors.toList());
        System.out.println("  " + allWords);

        // EXAMPLE 4: Get unique items across all orders
        System.out.println("\n--- 4. UNIQUE ITEMS ACROSS ALL ORDERS ---");
        List<String> uniqueItems = orders.stream()
            .flatMap(order -> order.getItems().stream())
            .distinct()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Unique items (sorted): " + uniqueItems);

        // EXAMPLE 5: Count total items across all orders
        System.out.println("\n--- 5. TOTAL ITEM COUNT ---");
        long totalItemCount = orders.stream()
            .flatMap(order -> order.getItems().stream())
            .count();
        System.out.println("Total items across all orders: " + totalItemCount);

        // EXAMPLE 6: Filter and flatMap combined
        System.out.println("\n--- 6. ITEMS FROM COMPLETED ORDERS ONLY ---");
        List<Order> mixedOrders = Arrays.asList(
            new Order("O1", "C1", 100.0, "COMPLETED", Arrays.asList("Item1", "Item2")),
            new Order("O2", "C2", 200.0, "PENDING", Arrays.asList("Item3", "Item4")),
            new Order("O3", "C3", 150.0, "COMPLETED", Arrays.asList("Item5", "Item6"))
        );
        
        List<String> completedOrderItems = mixedOrders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))
            .flatMap(order -> order.getItems().stream())
            .collect(Collectors.toList());
        System.out.println("Items from COMPLETED orders only: " + completedOrderItems);

        // EXAMPLE 7: Nested flatMap (2D matrix to 1D list)
        System.out.println("\n--- 7. FLATTEN 2D MATRIX ---");
        List<List<List<Integer>>> matrix3D = Arrays.asList(
            Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4)
            ),
            Arrays.asList(
                Arrays.asList(5, 6),
                Arrays.asList(7, 8)
            )
        );
        
        System.out.println("3D structure: " + matrix3D);
        
        List<Integer> flatMatrix = matrix3D.stream()
            .flatMap(list2D -> list2D.stream())      // First flatten: 3D -> 2D
            .flatMap(list1D -> list1D.stream())      // Second flatten: 2D -> 1D
            .collect(Collectors.toList());
        System.out.println("Flattened to 1D: " + flatMatrix);

        // KEY TAKEAWAY
        System.out.println("\n" + "=".repeat(60));
        System.out.println("KEY DIFFERENCE:");
        System.out.println("  map()     : Transforms each element (1-to-1)");
        System.out.println("              Stream<T> -> Stream<R>");
        System.out.println("  flatMap() : Transforms and flattens (1-to-many)");
        System.out.println("              Stream<T> -> Stream<R> (flattened)");
        System.out.println("=".repeat(60));
    }
}
