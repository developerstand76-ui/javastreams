package com.test.examples;

import com.test.model.Order;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ReduceOperationsExample {

    private ReduceOperationsExample() {
    }

    // EXAMPLE 7: Comprehensive reduce() operations
    public static void run() {
        // Input: numbers = [5, 12, 3, 18, 7, 25, 9, 2]
        //        words = ["Hello", "World", "Java", "Streams"]
        //        prices = [10.5, 20.0, 15.75, 8.25]
        //        sampleOrders = [O1: C1 $100, O2: C1 $200, O3: C1 $150]
        // Output: Prints results of 12 reduce-based examples (sum, product, min/max, etc.)
        // Expected Outputs:
        //   1. SUM: 81 (5+12+3+18+7+25+9+2)
        //   2. PRODUCT: 120 (2*3*4*5)
        //   3. MAX: 25
        //   4. MIN: 2
        //   5. STRING CONCAT: "HelloWorldJavaStreams" or "Hello, World, Java, Streams"
        //   6. COUNT: 8
        //   7. AVERAGE: $13.63
        //   8. CONDITIONAL: 32 (sum of even numbers: 12+18+2)
        //   9. COMPLEX: "Numbers: [5, 12, 3, 18, 7, 25, 9, 2]"
        //   10. LONGEST: "Streams"
        //   11. PARALLEL: 5050 (sum 1-100)
        //   12. CUSTOM OBJECT: max order, total $450.00, all items [A, B, C, D]
        System.out.println("\n--- Example 7: REDUCE() OPERATIONS ---");

        List<Integer> numbers = Arrays.asList(5, 12, 3, 18, 7, 25, 9, 2);
        List<String> words = Arrays.asList("Hello", "World", "Java", "Streams");
        List<Double> prices = Arrays.asList(10.5, 20.0, 15.75, 8.25);

        // 1. SUM - Adding all numbers
        System.out.println("\n1. SUM (Adding numbers):");
        System.out.println("Numbers: " + numbers);

        // Traditional
        int sumTraditional = 0;
        for (int num : numbers) {
            sumTraditional += num;
        }
        System.out.println("Traditional: " + sumTraditional);

        // Stream reduce
        int sumReduce = numbers.stream()
            .reduce(0, (a, b) -> a + b);
        System.out.println("Stream reduce: " + sumReduce);

        // Stream reduce with method reference
        int sumMethodRef = numbers.stream()
            .reduce(0, Integer::sum);
        System.out.println("Stream reduce (method ref): " + sumMethodRef);

        // 2. PRODUCT - Multiplying all numbers
        System.out.println("\n2. PRODUCT (Multiplying numbers):");
        List<Integer> smallNumbers = Arrays.asList(2, 3, 4, 5);
        System.out.println("Numbers: " + smallNumbers);

        // Traditional
        int productTraditional = 1;
        for (int num : smallNumbers) {
            productTraditional *= num;
        }
        System.out.println("Traditional: " + productTraditional);

        // Stream reduce
        int productReduce = smallNumbers.stream()
            .reduce(1, (a, b) -> a * b);
        System.out.println("Stream reduce: " + productReduce);

        // 3. MAX - Finding maximum value
        System.out.println("\n3. MAX (Finding maximum value):");
        System.out.println("Numbers: " + numbers);

        // Traditional
        int maxTraditional = Integer.MIN_VALUE;
        for (int num : numbers) {
            if (num > maxTraditional) {
                maxTraditional = num;
            }
        }
        System.out.println("Traditional: " + maxTraditional);

        // Stream reduce
        int maxReduce = numbers.stream()
            .reduce(Integer.MIN_VALUE, (a, b) -> a > b ? a : b);
        System.out.println("Stream reduce: " + maxReduce);

        // Stream reduce with Integer::max
        int maxMethodRef = numbers.stream()
            .reduce(Integer.MIN_VALUE, Integer::max);
        System.out.println("Stream reduce (Integer::max): " + maxMethodRef);

        // Stream reduce returning Optional (no identity)
        Optional<Integer> maxOptional = numbers.stream()
            .reduce(Integer::max);
        System.out.println("Stream reduce (Optional): " + maxOptional.orElse(0));

        // 4. MIN - Finding minimum value
        System.out.println("\n4. MIN (Finding minimum value):");
        System.out.println("Numbers: " + numbers);

        // Traditional
        int minTraditional = Integer.MAX_VALUE;
        for (int num : numbers) {
            if (num < minTraditional) {
                minTraditional = num;
            }
        }
        System.out.println("Traditional: " + minTraditional);

        // Stream reduce
        int minReduce = numbers.stream()
            .reduce(Integer.MAX_VALUE, (a, b) -> a < b ? a : b);
        System.out.println("Stream reduce: " + minReduce);

        // Stream reduce with Integer::min
        int minMethodRef = numbers.stream()
            .reduce(Integer.MAX_VALUE, Integer::min);
        System.out.println("Stream reduce (Integer::min): " + minMethodRef);

        // 5. STRING CONCATENATION
        System.out.println("\n5. STRING CONCATENATION:");
        System.out.println("Words: " + words);

        // Traditional
        String concatTraditional = "";
        for (String word : words) {
            concatTraditional += word;
        }
        System.out.println("Traditional: " + concatTraditional);

        // Stream reduce - simple concatenation
        String concatReduce = words.stream()
            .reduce("", (a, b) -> a + b);
        System.out.println("Stream reduce (simple): " + concatReduce);

        // Stream reduce - with delimiter
        String concatWithDelimiter = words.stream()
            .reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b);
        System.out.println("Stream reduce (with comma): " + concatWithDelimiter);

        // Better way: String.join or Collectors.joining
        String joined = words.stream()
            .collect(Collectors.joining(", "));
        System.out.println("Stream Collectors.joining: " + joined);

        // 6. COUNT - Counting elements
        System.out.println("\n6. COUNT (Counting elements):");
        System.out.println("Numbers: " + numbers);

        // Traditional
        int countTraditional = 0;
        for (int num : numbers) {
            countTraditional++;
        }
        System.out.println("Traditional: " + countTraditional);

        // Stream reduce (counting ones)
        long countReduce = numbers.stream()
            .map(n -> 1)
            .reduce(0, Integer::sum);
        System.out.println("Stream reduce: " + countReduce);

        // Better way: count()
        long countMethod = numbers.stream().count();
        System.out.println("Stream count(): " + countMethod);

        // 7. AVERAGE - Custom aggregation
        System.out.println("\n7. AVERAGE (using reduce for custom aggregation):");
        System.out.println("Prices: " + prices);

        // Traditional
        double sumPricesTraditional = 0.0;
        for (double price : prices) {
            sumPricesTraditional += price;
        }
        double avgTraditional = sumPricesTraditional / prices.size();
        System.out.println("Traditional average: $" + String.format("%.2f", avgTraditional));

        // Stream reduce - sum then divide
        double sumPrices = prices.stream()
            .reduce(0.0, Double::sum);
        double avgReduce = sumPrices / prices.size();
        System.out.println("Stream reduce average: $" + String.format("%.2f", avgReduce));

        // Better way: averagingDouble collector
        double avgCollector = prices.stream()
            .collect(Collectors.averagingDouble(Double::doubleValue));
        System.out.println("Stream Collectors.averagingDouble: $" + String.format("%.2f", avgCollector));

        // 8. CONDITIONAL AGGREGATION - Sum of even numbers only
        System.out.println("\n8. CONDITIONAL AGGREGATION (Sum of even numbers):");
        System.out.println("Numbers: " + numbers);

        // Traditional
        int evenSumTraditional = 0;
        for (int num : numbers) {
            if (num % 2 == 0) {
                evenSumTraditional += num;
            }
        }
        System.out.println("Traditional: " + evenSumTraditional);

        // Stream reduce with filter
        int evenSumReduce = numbers.stream()
            .filter(n -> n % 2 == 0)
            .reduce(0, Integer::sum);
        System.out.println("Stream reduce: " + evenSumReduce);

        // 9. COMPLEX REDUCE - Building a summary string
        System.out.println("\n9. COMPLEX REDUCE (Building summary):");
        System.out.println("Numbers: " + numbers);

        // Traditional
        StringBuilder summaryTraditional = new StringBuilder("Numbers: [");
        for (int i = 0; i < numbers.size(); i++) {
            summaryTraditional.append(numbers.get(i));
            if (i < numbers.size() - 1) {
                summaryTraditional.append(", ");
            }
        }
        summaryTraditional.append("]");
        System.out.println("Traditional: " + summaryTraditional);

        // Stream reduce
        String summaryReduce = numbers.stream()
            .map(String::valueOf)
            .reduce("Numbers: [", (a, b) -> a.equals("Numbers: [") ? a + b : a + ", " + b) + "]";
        System.out.println("Stream reduce: " + summaryReduce);

        // 10. LONGEST STRING - Custom comparison
        System.out.println("\n10. LONGEST STRING (Custom comparison):");
        System.out.println("Words: " + words);

        // Traditional
        String longestTraditional = "";
        for (String word : words) {
            if (word.length() > longestTraditional.length()) {
                longestTraditional = word;
            }
        }
        System.out.println("Traditional: " + longestTraditional);

        // Stream reduce
        String longestReduce = words.stream()
            .reduce("", (a, b) -> a.length() >= b.length() ? a : b);
        System.out.println("Stream reduce: " + longestReduce);

        // Stream reduce with Optional
        Optional<String> longestOptional = words.stream()
            .reduce((a, b) -> a.length() >= b.length() ? a : b);
        System.out.println("Stream reduce (Optional): " + longestOptional.orElse(""));

        // 11. PARALLEL REDUCE - For large datasets
        System.out.println("\n11. PARALLEL REDUCE (with combiner):");
        List<Integer> largeList = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());

        // Sequential reduce
        int seqSum = largeList.stream()
            .reduce(0, Integer::sum);
        System.out.println("Sequential sum (1-100): " + seqSum);

        // Parallel reduce (identity, accumulator, combiner)
        int parallelSum = largeList.parallelStream()
            .reduce(0, Integer::sum, Integer::sum);
        System.out.println("Parallel sum (1-100): " + parallelSum);

        // 12. CUSTOM OBJECT REDUCTION
        System.out.println("\n12. CUSTOM OBJECT REDUCTION (Combining orders):");
        List<Order> sampleOrders = Arrays.asList(
            new Order("O1", "C1", 100.0, "COMPLETED", Arrays.asList("A")),
            new Order("O2", "C1", 200.0, "COMPLETED", Arrays.asList("B", "C")),
            new Order("O3", "C1", 150.0, "COMPLETED", Arrays.asList("D"))
        );

        // Reduce to find order with maximum amount
        Optional<Order> maxOrder = sampleOrders.stream()
            .reduce((o1, o2) -> o1.getAmount() > o2.getAmount() ? o1 : o2);
        System.out.println("Order with max amount: " + maxOrder.map(Order::toString).orElse("None"));

        // Reduce to sum all amounts
        double totalAmount = sampleOrders.stream()
            .map(Order::getAmount)
            .reduce(0.0, Double::sum);
        System.out.println("Total amount: $" + String.format("%.2f", totalAmount));

        // Reduce to combine all item lists
        List<String> allItems = sampleOrders.stream()
            .map(Order::getItems)
            .reduce(new ArrayList<>(), (list1, list2) -> {
                List<String> combined = new ArrayList<>(list1);
                combined.addAll(list2);
                return combined;
            });
        System.out.println("All items: " + allItems);
    }
}
