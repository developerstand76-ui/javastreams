package com.test.examples;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class PartitionPassFailExample {

    private PartitionPassFailExample() {
    }

    // Practical Use 4: Partitioning pass/fail sets
    public static void run() {
        // Input: scores list
        // Output: Prints pass and fail partitions based on threshold 60
        System.out.println("\n--- Practical Use: Partition Pass/Fail ---");
        List<Integer> scores = Arrays.asList(95, 82, 67, 49, 73, 58);

        Map<Boolean, List<Integer>> passFail = scores.stream()
            .collect(Collectors.partitioningBy(score -> score >= 60));

        System.out.println("Scores: " + scores);
        System.out.println("Pass: " + passFail.get(true));
        System.out.println("Fail: " + passFail.get(false));
    }
}
