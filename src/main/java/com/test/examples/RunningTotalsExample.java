package com.test.examples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class RunningTotalsExample {

    private RunningTotalsExample() {
    }

    // Practical Use 5: Running totals (window-like logic)
    public static void run() {
        // Input: amounts list
        // Output: Prints running totals list
        System.out.println("\n--- Practical Use: Running Totals ---");
        List<Integer> amounts = Arrays.asList(5, 10, 3, 7, 2);

        List<Integer> runningTotals = IntStream.range(0, amounts.size())
            .mapToObj(i -> amounts.subList(0, i + 1).stream().mapToInt(Integer::intValue).sum())
            .collect(Collectors.toList());

        System.out.println("Amounts: " + amounts);
        System.out.println("Running totals: " + runningTotals);
    }
}
