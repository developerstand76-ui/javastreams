package com.test.examples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class RunningTotalsExample {

    private RunningTotalsExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Running Totals Example - Run Independently");
        System.out.println("========================================");
        
        // Run the running totals example
        run();
    }

    // Practical Use 5: Running totals (window-like logic)
    public static void run() {
        // Input: amounts = [5, 10, 3, 7, 2]
        // Output: Prints running totals list
        // Expected Output:
        //   Amounts: [5, 10, 3, 7, 2]
        //   Running totals: [5, 15, 18, 25, 27]
        System.out.println("\n--- Practical Use: Running Totals ---");
        List<Integer> amounts = Arrays.asList(5, 10, 3, 7, 2);

        // IntStream.range() - creates stream of indices (0, 1, 2, ...)
        // mapToObj() - for each index, creates sublist [0..i] and sums it
        //   subList() - gets elements from start to current index
        //   stream().mapToInt() - converts to IntStream
        //   sum() - calculates sum of sublist
        // collect() - gathers all sums into list
        List<Integer> runningTotals = IntStream.range(0, amounts.size())
            .mapToObj(i -> amounts.subList(0, i + 1).stream().mapToInt(Integer::intValue).sum())
            .collect(Collectors.toList());

        System.out.println("Amounts: " + amounts);
        System.out.println("Running totals: " + runningTotals);
    }
}
