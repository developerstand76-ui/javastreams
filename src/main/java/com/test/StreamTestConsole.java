package com.test;

import com.test.examples.*;

/**
 * Standalone console runner for testing each Stream example independently.
 * This allows running individual examples without needing the Spring Boot server.
 * 
 * Usage:
 *   java com.test.StreamTestConsole                    # Shows menu
 *   java com.test.StreamTestConsole 1                  # Run example 1 (Filter)
 *   java com.test.StreamTestConsole filter             # Run by name
 *   java com.test.StreamTestConsole all                # Run all examples
 */
public class StreamTestConsole {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            printMenu();
            return;
        }

        String example = args[0].toLowerCase();
        
        switch (example) {
            case "1", "filter" -> FilteringExample.main(new String[]{});
            case "2", "map" -> MappingExample.main(new String[]{});
            case "3", "aggregate" -> AggregationExample.main(new String[]{});
            case "4", "group" -> GroupingExample.main(new String[]{});
            case "5", "complex" -> ComplexPipelineExample.main(new String[]{});
            case "6", "character" -> CharacterFrequencyExample.main(new String[]{});
            case "7", "reduce" -> ReduceOperationsExample.main(new String[]{});
            case "8", "normalize" -> NormalizeDeduplicateSortExample.main(new String[]{});
            case "9", "word" -> WordFrequencyTopNExample.main(new String[]{});
            case "10", "validate" -> InvalidRecordCollectionExample.main(new String[]{});
            case "11", "partition" -> PartitionPassFailExample.main(new String[]{});
            case "12", "running" -> RunningTotalsExample.main(new String[]{});
            case "13", "multifield" -> MultiFieldSortExample.main(new String[]{});
            case "all" -> runAll();
            default -> {
                System.out.println("Unknown example: " + example);
                printMenu();
            }
        }
    }

    private static void runAll() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║          RUNNING ALL STREAM EXAMPLES INDEPENDENTLY              ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
        
        FilteringExample.main(new String[]{});
        MappingExample.main(new String[]{});
        AggregationExample.main(new String[]{});
        GroupingExample.main(new String[]{});
        ComplexPipelineExample.main(new String[]{});
        CharacterFrequencyExample.main(new String[]{});
        ReduceOperationsExample.main(new String[]{});
        NormalizeDeduplicateSortExample.main(new String[]{});
        WordFrequencyTopNExample.main(new String[]{});
        InvalidRecordCollectionExample.main(new String[]{});
        PartitionPassFailExample.main(new String[]{});
        RunningTotalsExample.main(new String[]{});
        MultiFieldSortExample.main(new String[]{});
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║               ALL EXAMPLES COMPLETED SUCCESSFULLY               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
    }

    private static void printMenu() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║         JAVA STREAMS API - EXAMPLES RUNNER                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        System.out.println("Usage: java -cp . com.test.StreamTestConsole [example]\n");
        System.out.println("Stream Examples:");
        System.out.println("  1  or filter       - Filter Orders by Status & Amount");
        System.out.println("  2  or map          - Extract Unique Customer IDs");
        System.out.println("  3  or aggregate    - Calculate Total Revenue");
        System.out.println("  4  or group        - Group Orders by Status");
        System.out.println("  5  or complex      - Top Customers by Spending");
        System.out.println("  6  or character    - Character Frequency Analysis\n");
        System.out.println("Reduce & Intermediate Examples:");
        System.out.println("  7  or reduce       - 12 Reduce Operations (sum, product, etc.)\n");
        System.out.println("Practical Use Cases:");
        System.out.println("  8  or normalize    - Normalize, Deduplicate, Sort Names");
        System.out.println("  9  or word         - Word Frequency (Top-N)");
        System.out.println("  10 or validate     - Validate IDs (Regex Pattern)");
        System.out.println("  11 or partition    - Partition Scores (Pass/Fail)");
        System.out.println("  12 or running      - Running Totals (Cumulative Sum)");
        System.out.println("  13 or multifield   - Multi-field Sorting\n");
        System.out.println("Special:");
        System.out.println("  all                - Run all 13 examples\n");
        System.out.println("Examples:");
        System.out.println("  java com.test.StreamTestConsole 1");
        System.out.println("  java com.test.StreamTestConsole filter");
        System.out.println("  java com.test.StreamTestConsole all\n");
    }
}
