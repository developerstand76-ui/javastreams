package com.test.examples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class InvalidRecordCollectionExample {

    private InvalidRecordCollectionExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Invalid Record Collection Example - Run Independently");
        System.out.println("========================================");
        
        // Run the validation example
        run();
    }

    // Practical Use 3: Validation and invalid record collection
    public static void run() {
        // Input: ids = ["A-100", "", "B-200", "  ", "C-300", "INVALID#"]
        // Output: Prints invalid IDs filtered by regex validation
        // Expected Output:
        //   IDs: [A-100, , B-200,   , C-300, INVALID#]
        //   Invalid IDs: [,   , INVALID#]
        System.out.println("\n--- Practical Use: Validation (Invalid IDs) ---");
        List<String> ids = Arrays.asList("A-100", "", "B-200", "  ", "C-300", "INVALID#");

        // stream() - creates stream from list
        // filter() - keeps elements that fail validation (null, blank, or don't match pattern)
        //   matches() - regex validation: must be letter-dash-digits format
        // collect() - gathers invalid IDs into list
        List<String> invalidIds = ids.stream()
            .filter(id -> id == null || id.isBlank() || !id.matches("[A-Z]-\\d+"))  // validation
            .collect(Collectors.toList());                        // collect failures

        System.out.println("IDs: " + ids);
        System.out.println("Invalid IDs: " + invalidIds);
    }
}
