package com.test.examples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class InvalidRecordCollectionExample {

    private InvalidRecordCollectionExample() {
    }

    // Practical Use 3: Validation and invalid record collection
    public static void run() {
        // Input: ids list with valid/invalid values
        // Output: Prints invalid IDs filtered by regex validation
        System.out.println("\n--- Practical Use: Validation (Invalid IDs) ---");
        List<String> ids = Arrays.asList("A-100", "", "B-200", "  ", "C-300", "INVALID#");

        List<String> invalidIds = ids.stream()
            .filter(id -> id == null || id.isBlank() || !id.matches("[A-Z]-\\d+"))
            .collect(Collectors.toList());

        System.out.println("IDs: " + ids);
        System.out.println("Invalid IDs: " + invalidIds);
    }
}
