package com.test.examples;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public final class NormalizeDeduplicateSortExample {

    private NormalizeDeduplicateSortExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Normalize Deduplicate Sort Example - Run Independently");
        System.out.println("========================================");
        
        // Run the normalization example
        run();
    }

    // Practical Use 1: Normalize + deduplicate + sort
    public static void run() {
        // Input: rawNames = [" Alice ", "bob", "ALICE", "  Bob ", "Clara", "clara "]
        // Output: Prints normalized, deduplicated, and sorted names
        // Expected Output:
        //   Input: [ Alice , bob, ALICE,   Bob , Clara, clara ]
        //   Normalized: [alice, bob, clara]
        System.out.println("\n--- Practical Use: Normalize + Deduplicate + Sort ---");
        List<String> rawNames = Arrays.asList(" Alice ", "bob", "ALICE", "  Bob ", "Clara", "clara ");

        // stream() - creates stream from list
        // map() - transforms each element (trim whitespace, convert to lowercase)
        // filter() - removes blank strings
        // collect() - gathers into TreeSet (automatically sorts and deduplicates)
        Set<String> normalizedNames = rawNames.stream()
            .map(name -> name.trim().toLowerCase(Locale.ROOT))    // normalize: trim + lowercase
            .filter(name -> !name.isBlank())                      // remove empty strings
            .collect(Collectors.toCollection(java.util.TreeSet::new));  // collect to sorted set

        System.out.println("Input: " + rawNames);
        System.out.println("Normalized: " + normalizedNames);
    }
}
