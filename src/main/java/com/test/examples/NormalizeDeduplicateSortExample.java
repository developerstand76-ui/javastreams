package com.test.examples;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public final class NormalizeDeduplicateSortExample {

    private NormalizeDeduplicateSortExample() {
    }

    // Practical Use 1: Normalize + deduplicate + sort
    public static void run() {
        // Input: rawNames list with mixed case/spacing
        // Output: Prints normalized, deduplicated, and sorted names
        System.out.println("\n--- Practical Use: Normalize + Deduplicate + Sort ---");
        List<String> rawNames = Arrays.asList(" Alice ", "bob", "ALICE", "  Bob ", "Clara", "clara ");

        Set<String> normalizedNames = rawNames.stream()
            .map(name -> name.trim().toLowerCase(Locale.ROOT))
            .filter(name -> !name.isBlank())
            .collect(Collectors.toCollection(java.util.TreeSet::new));

        System.out.println("Input: " + rawNames);
        System.out.println("Normalized: " + normalizedNames);
    }
}
