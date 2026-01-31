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
        // Input: scores = [95, 82, 67, 49, 73, 58]
        // Output: Prints pass and fail partitions based on threshold 60
        // Expected Output:
        //   Scores: [95, 82, 67, 49, 73, 58]
        //   Pass: [95, 82, 67, 73]
        //   Fail: [49, 58]
        System.out.println("\n--- Practical Use: Partition Pass/Fail ---");
        List<Integer> scores = Arrays.asList(95, 82, 67, 49, 73, 58);

        // stream() - creates stream from list
        // partitioningBy() - special grouping that creates exactly 2 groups (true/false)
        //   predicate: score >= 60 (true = pass, false = fail)
        // Returns Map<Boolean, List<Integer>>
        Map<Boolean, List<Integer>> passFail = scores.stream()
            .collect(Collectors.partitioningBy(score -> score >= 60));  // partition by threshold

        System.out.println("Scores: " + scores);
        System.out.println("Pass: " + passFail.get(true));
        System.out.println("Fail: " + passFail.get(false));
    }
}
