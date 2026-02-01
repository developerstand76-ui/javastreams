package com.test.examples;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class LazyEvaluationExample {

    private LazyEvaluationExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Lazy Evaluation Example - Run Independently");
        System.out.println("========================================");
        
        run();
    }

    public static void run() {
        System.out.println("\n--- LAZY EVALUATION: Operations execute only when needed ---");
        
        // Example 1: Basic Lazy Evaluation Demonstration
        System.out.println("\n1. BASIC LAZY EVALUATION:");
        System.out.println("Building stream pipeline (no execution yet)...");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Stream<Integer> stream = numbers.stream()
            .filter(n -> {
                System.out.println("  -> Filtering: " + n);
                return n % 2 == 0;
            })
            .map(n -> {
                System.out.println("  -> Mapping: " + n + " to " + (n * 10));
                return n * 10;
            });
        
        System.out.println("Pipeline built! Notice: No filtering or mapping happened yet.");
        System.out.println("Now calling terminal operation collect()...");
        
        List<Integer> result = stream.collect(Collectors.toList());
        System.out.println("Result: " + result);
        System.out.println("Key Point: Operations execute only when collect() is called!");
        
        // Example 2: Short-Circuiting with findFirst()
        System.out.println("\n2. SHORT-CIRCUITING (findFirst stops early):");
        System.out.println("Looking for first number > 100 in large list...");
        
        List<Integer> largeList = Arrays.asList(10, 20, 30, 150, 200, 300, 400);
        Optional<Integer> firstLarge = largeList.stream()
            .peek(n -> System.out.println("  -> Checking: " + n))
            .filter(n -> n > 100)
            .findFirst();
        
        System.out.println("Found: " + firstLarge.orElse(0));
        System.out.println("Key Point: Stopped at 150, didn't process 200, 300, 400!");
        
        // Example 3: Short-Circuiting with anyMatch()
        System.out.println("\n3. SHORT-CIRCUITING (anyMatch stops at first match):");
        System.out.println("Checking if any number is divisible by 7...");
        
        List<Integer> testNumbers = Arrays.asList(2, 4, 6, 7, 8, 10, 12, 14);
        boolean hasMultipleOf7 = testNumbers.stream()
            .peek(n -> System.out.println("  -> Testing: " + n))
            .anyMatch(n -> n % 7 == 0);
        
        System.out.println("Result: " + hasMultipleOf7);
        System.out.println("Key Point: Stopped at 7, didn't check 8, 10, 12, 14!");
        
        // Example 4: Infinite Streams (only possible with lazy evaluation)
        System.out.println("\n4. INFINITE STREAMS:");
        System.out.println("Creating infinite stream of natural numbers...");
        
        List<Integer> first5Squares = Stream.iterate(1, n -> n + 1)
            .peek(n -> System.out.println("  -> Generated: " + n))
            .map(n -> n * n)
            .limit(5)
            .collect(Collectors.toList());
        
        System.out.println("First 5 squares: " + first5Squares);
        System.out.println("Key Point: limit(5) prevented infinite execution!");
        
        // Example 5: Operation Fusion (single pass through data)
        System.out.println("\n5. OPERATION FUSION (all operations on each element):");
        System.out.println("Processing with multiple operations...");
        System.out.println("Watch the order: each element goes through ALL operations sequentially\n");
        
        List<Integer> fusionTest = Arrays.asList(5, 10, 15, 20, 25);
        List<Integer> fusionResult = fusionTest.stream()
            .filter(n -> {
                System.out.println("  -> Filter1 (>8): " + n);
                return n > 8;
            })
            .map(n -> {
                System.out.println("  -> Map (*2): " + n + " => " + (n * 2));
                return n * 2;
            })
            .filter(n -> {
                System.out.println("  -> Filter2 (<50): " + n);
                return n < 50;
            })
            .collect(Collectors.toList());
        
        System.out.println("\nResult: " + fusionResult);
        System.out.println("\nKey Point: Operation Fusion");
        System.out.println("  Element 5:  Filter1(>8)? NO → skipped");
        System.out.println("  Element 10: Filter1(>8)? YES → Map(*2)=20 → Filter2(<50)? YES → collected");
        System.out.println("  Element 15: Filter1(>8)? YES → Map(*2)=30 → Filter2(<50)? YES → collected");
        System.out.println("  Element 20: Filter1(>8)? YES → Map(*2)=40 → Filter2(<50)? YES → collected");
        System.out.println("  Element 25: Filter1(>8)? YES → Map(*2)=50 → Filter2(<50)? NO → skipped");
        System.out.println("\nEach element processes through ALL operations before next element!");
        
        // Example 6: Eager vs Lazy Comparison
        System.out.println("\n6. EAGER (Traditional) vs LAZY (Streams):");
        
        System.out.println("\nEAGER approach (processes all elements at each step):");
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        
        // Eager: Step 1 - filter all
        System.out.println("  Step 1: Filtering all elements...");
        List<Integer> filtered = data.stream()
            .filter(n -> {
                System.out.println("    Filtering: " + n);
                return n % 2 == 0;
            })
            .collect(Collectors.toList());
        
        // Eager: Step 2 - map all
        System.out.println("  Step 2: Mapping all filtered elements...");
        List<Integer> mapped = filtered.stream()
            .map(n -> {
                System.out.println("    Mapping: " + n);
                return n * 10;
            })
            .collect(Collectors.toList());
        
        System.out.println("  Result: " + mapped);
        
        System.out.println("\nLAZY approach (single pass, fused operations):");
        List<Integer> lazyResult = data.stream()
            .filter(n -> {
                System.out.println("  Filter: " + n);
                return n % 2 == 0;
            })
            .map(n -> {
                System.out.println("  Map: " + n);
                return n * 10;
            })
            .collect(Collectors.toList());
        
        System.out.println("  Result: " + lazyResult);
        System.out.println("Key Point: Lazy does filter+map on each element in one pass!");
        
        // Example 7: No Terminal Operation = No Execution
        System.out.println("\n7. NO TERMINAL OPERATION = NOTHING HAPPENS:");
        
        System.out.println("Creating stream pipeline without terminal operation...");
        numbers.stream()
            .filter(n -> {
                System.out.println("  This will NEVER print!");
                return n > 5;
            })
            .map(n -> n * 2);
        
        System.out.println("No output above because no terminal operation was called!");
        
        // Example 8: Intermediate vs Terminal Operations
        System.out.println("\n8. INTERMEDIATE vs TERMINAL OPERATIONS:");
        
        System.out.println("\nIntermediate operations (lazy - return Stream):");
        System.out.println("  - filter(), map(), flatMap(), distinct()");
        System.out.println("  - sorted(), limit(), skip(), peek()");
        System.out.println("  -> Build pipeline, don't execute yet");
        
        System.out.println("\nTerminal operations (eager - trigger execution):");
        System.out.println("  - collect(), reduce(), forEach()");
        System.out.println("  - count(), findFirst(), anyMatch(), allMatch()");
        System.out.println("  - min(), max(), toArray()");
        System.out.println("  -> Execute entire pipeline and return result");
        
        // Example 9: Performance Benefit
        System.out.println("\n9. PERFORMANCE BENEFIT:");
        
        List<Integer> hugeList = Stream.iterate(1, n -> n + 1)
            .limit(1000000)
            .collect(Collectors.toList());
        
        System.out.println("Finding first element > 500 in list of 1 million...");
        
        long start = System.nanoTime();
        Optional<Integer> found = hugeList.stream()
            .filter(n -> n > 500)
            .findFirst();
        long end = System.nanoTime();
        
        System.out.println("Found: " + found.orElse(0));
        System.out.println("Time: " + (end - start) / 1_000_000.0 + " ms");
        System.out.println("Key Point: Only checked ~500 elements, not all 1 million!");
        
        System.out.println("\n========================================");
        System.out.println("SUMMARY:");
        System.out.println("========================================");
        System.out.println("✓ Lazy: Intermediate operations build pipeline only");
        System.out.println("✓ Eager: Terminal operations execute entire pipeline");
        System.out.println("✓ Fusion: All operations applied per element");
        System.out.println("✓ Short-circuit: Stops early when result found");
        System.out.println("✓ Efficient: No wasted work on unused elements");
    }
}
