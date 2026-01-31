package com.test.controller;

import com.test.dto.StreamResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * REST API Controller for Real-World Stream API Use Cases
 * 
 * <h2>Overview</h2>
 * This controller provides endpoints demonstrating practical, production-ready patterns:
 * <ul>
 *     <li><strong>Data Cleaning (ETL):</strong> Normalize and deduplicate data</li>
 *     <li><strong>Text Analytics:</strong> Word frequency and NLP patterns</li>
 *     <li><strong>Data Validation:</strong> Pattern-based validation with error collection</li>
 *     <li><strong>Partitioning:</strong> Binary classification (pass/fail, valid/invalid)</li>
 *     <li><strong>Cumulative Operations:</strong> Running totals for financial calculations</li>
 * </ul>
 * 
 * <h2>Production-Ready Patterns</h2>
 * These examples showcase patterns found in real production systems:
 * <ul>
 *     <li>ETL data pipelines processing raw input data</li>
 *     <li>Analytics systems extracting insights from text</li>
 *     <li>Data quality frameworks validating input formats</li>
 *     <li>Financial systems with cumulative calculations</li>
 *     <li>Classification systems (scoring, grading, filtering)</li>
 * </ul>
 * 
 * <h2>Stream API Advantages in These Scenarios</h2>
 * <ul>
 *     <li><strong>Expressiveness:</strong> Clearly convey complex data transformations in pipeline form</li>
 *     <li><strong>Reusability:</strong> Collectors and operations can be composed and reused</li>
 *     <li><strong>Maintainability:</strong> Pipeline structure mirrors business logic flow</li>
 *     <li><strong>Performance:</strong> Lazy evaluation and optional parallelization improve efficiency</li>
 *     <li><strong>Immutability:</strong> Functional approach reduces side effects and bugs</li>
 * </ul>
 * 
 * @author Practical Stream Examples
 * @version 1.0
 * @see StreamResult Result wrapper for all endpoints
 */
@RestController
@RequestMapping("/api/practical")
@Tag(
    name = "Practical Examples",
    description = """
        Real-World Stream API Use Cases and Production Patterns
        
        Demonstrates practical patterns found in production systems:
        - Data cleanup: Normalize, deduplicate, sort (ETL pipelines)
        - Text analytics: Word frequency, top-N patterns (NLP, content analysis)
        - Validation: Pattern matching and error collection (data quality)
        - Partitioning: Binary classification (scoring, grading)
        - Cumulative calculations: Running totals (financial, analytics)
        
        Each example shows how Stream API simplifies real business logic."""
)
public class PracticalExamplesController {

    /**
     * Normalizes, deduplicates, and sorts a list of names.
     * 
     * <h3>Use Case</h3>
     * <p>Data cleanup in ETL (Extract-Transform-Load) pipelines:
     * <ul>
     *     <li>Standardizing customer contact lists with inconsistent formatting</li>
     *     <li>Importing data from multiple sources with varying case/spacing</li>
     *     <li>Building clean master data for analytics dashboards</li>
     * </ul>
     * </p>
     * 
     * <h3>Stream Operation Pipeline</h3>
     * <pre>
     * names.stream()
     *   .map(name -> name.trim().toLowerCase(Locale.ROOT))  // Normalize: remove spaces, lowercase
     *   .filter(name -> !name.isBlank())                     // Clean: remove empty strings
     *   .collect(Collectors.toCollection(TreeSet::new));     // Sort and deduplicate simultaneously
     * </pre>
     * 
     * <h3>Key Concepts & Optimization</h3>
     * <ul>
     *     <li><strong>Normalization:</strong> trim() removes leading/trailing whitespace, toLowerCase() ensures case uniformity</li>
     *     <li><strong>Filtering:</strong> isBlank() catches both null and whitespace-only entries (Java 11+)</li>
     *     <li><strong>TreeSet Benefits:</strong> Provides BOTH deduplication AND alphabetical sorting in single pass</li>
     *     <li><strong>Locale.ROOT:</strong> Ensures consistent behavior across different system locales</li>
     *     <li><strong>Single-Pass Efficiency:</strong> TreeSet automatically maintains sorted unique set order</li>
     * </ul>
     * 
     * @param names List of name strings with potential inconsistent formatting (mixed case, extra spaces)
     * @return StreamResult containing:
     *         <ul>
     *           <li>Sorted TreeSet of normalized unique names</li>
     *           <li>Execution time for performance reference</li>
     *         </ul>
     * 
     * @see StreamResult Response object with results and timing
     */
    @PostMapping("/normalize-sort")
    @Operation(
        summary = "Normalize, deduplicate, and sort names",
        description = """
            Transforms a list of names through ETL pipeline:
            1. Normalize: Trim whitespace, convert to lowercase
            2. Clean: Filter out blank entries
            3. Deduplicate & Sort: Use TreeSet for automatic deduplication and alphabetical sort
            
            Perfect for data cleaning workflows."""
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully normalized and sorted names",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Normalized Names Response",
                value = """
                    {
                      "approach": "Stream API",
                      "result": ["alice", "bob", "charlie"],
                      "executionTimeMs": 1
                    }
                    """
            )
        )
    )
    public StreamResult normalizeSortNames(
        @Parameter(
            description = "List of names to normalize. May contain mixed case, extra whitespace, duplicates. Empty or blank entries are filtered out.",
            example = "[\" Alice \", \"bob\", \"ALICE\", \"  \", \"charlie\"]",
            required = true
        )
        @RequestBody List<String> names
    ) {
        long start = System.nanoTime();
        
        // Stream pipeline: normalize -> filter -> deduplicate & sort (all in one TreeSet)
        Set<String> normalized = names.stream()
            .map(name -> name.trim().toLowerCase(Locale.ROOT))         // Normalize: whitespace & case
            .filter(name -> !name.isBlank())                           // Filter: remove empty strings
            .collect(Collectors.toCollection(TreeSet::new));           // Collect: dedup + sort with TreeSet
        
        long time = (System.nanoTime() - start) / 1_000_000;
        
        return new StreamResult("Stream API", normalized, time);
    }

    /**
     * Extracts top N most frequent words from text with word frequency analysis.
     * 
     * <h3>Use Case</h3>
     * <p>Content analysis and NLP (Natural Language Processing):
     * <ul>
     *     <li>SEO analysis - identify most important keywords in content</li>
     *     <li>Content recommendations - understand document themes</li>
     *     <li>Autocomplete suggestions - surface frequently used terms</li>
     *     <li>Topic modeling - determine primary topics in text</li>
     * </ul>
     * </p>
     * 
     * <h3>Stream Operation Pipeline (Multi-Stage)</h3>
     * <pre>
     * Arrays.stream(text.toLowerCase().split("\\W+"))          // Split into word tokens
     *   .filter(word -> !word.isBlank())                        // Filter empty tokens
     *   .collect(Collectors.groupingBy(                         // Stage 1: Count words
     *       word -> word,
     *       Collectors.counting()
     *   ))
     *   .entrySet()
     *   .stream()
     *   .sorted(Map.Entry.comparingByValue().reversed())        // Stage 2: Sort by frequency (descending)
     *   .limit(topN)                                            // Stage 3: Take top N
     *   .collect(Collectors.toMap(..., LinkedHashMap::new))     // Stage 4: Collect with order preserved
     * </pre>
     * 
     * <h3>Key Concepts</h3>
     * <ul>
     *     <li><strong>Split Operation:</strong> \\W+ matches non-word characters (space, punctuation)</li>
     *     <li><strong>groupingBy() with Counting:</strong> Aggregates word occurrences into frequency map</li>
     *     <li><strong>entrySet() Stream:</strong> Converts map to stream of key-value pairs for sorting</li>
     *     <li><strong>comparingByValue().reversed():</strong> Sorts by count in descending order (highest first)</li>
     *     <li><strong>limit():</strong> Lazy operation limiting to topN results before collection</li>
     *     <li><strong>LinkedHashMap:</strong> Preserves insertion/sort order in result map</li>
     *     <li><strong>Lazy Evaluation Benefit:</strong> Only top N words processed, rest discarded early</li>
     * </ul>
     * 
     * @param text Input text to analyze for word frequency
     * @param topN Number of top words to return (default: 3)
     * @return StreamResult containing:
     *         <ul>
     *           <li>LinkedHashMap with top N words as keys and frequencies as values</li>
     *           <li>Ordered by frequency (highest first)</li>
     *           <li>Execution time</li>
     *         </ul>
     * 
     * @see StreamResult Response object with word frequencies and timing
     */
    @PostMapping("/word-frequency")
    @Operation(
        summary = "Calculate top N word frequencies",
        description = """
            Analyzes text to find most frequently occurring words.
            Pipeline stages:
            1. Tokenize: Split by non-word characters
            2. Count: Group words and tally occurrences
            3. Sort: Order by frequency (descending)
            4. Limit: Take top N results
            5. Collect: Preserve order in result map
            
            Demonstrates multi-stage stream pipeline."""
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully analyzed word frequencies",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Word Frequency Response",
                value = """
                    {
                      "approach": "Stream API",
                      "result": {
                        "streams": 3,
                        "with": 2,
                        "data": 2
                      },
                      "executionTimeMs": 2
                    }
                    """
            )
        )
    )
    public StreamResult wordFrequency(
        @Parameter(
            description = "Text content to analyze for word frequencies. Words are separated by non-word characters (spaces, punctuation). Case-insensitive analysis.",
            example = "Java streams make data processing with streams concise and elegant with streams",
            required = true
        )
        @RequestParam String text,
        
        @Parameter(
            description = "Maximum number of top frequent words to return. Results are sorted by frequency in descending order.",
            example = "3"
        )
        @RequestParam(defaultValue = "3") int topN
    ) {
        long start = System.nanoTime();
        
        // Multi-stage pipeline: tokenize -> count -> sort -> limit -> collect
        Map<String, Long> topWords = Arrays.stream(text.toLowerCase(Locale.ROOT).split("\\W+"))
            .filter(word -> !word.isBlank())                          // Stage 0: Filter empty tokens
            .collect(Collectors.groupingBy(                           // Stage 1: Count word occurrences
                word -> word,
                Collectors.counting()
            ))
            .entrySet()                                              // Convert to stream of Map.Entry
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue()       // Stage 2: Sort by frequency
                    .reversed())
            .limit(topN)                                             // Stage 3: Take top N (lazy)
            .collect(Collectors.toMap(                               // Stage 4: Collect preserving order
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a, b) -> a,                                         // Merge function (shouldn't need)
                LinkedHashMap::new                                   // Preserve sort order
            ));
        
        long time = (System.nanoTime() - start) / 1_000_000;
        
        return new StreamResult("Stream API", topWords, time);
    }

    /**
     * Validates IDs against a specific format pattern and collects invalid entries.
     * 
     * <h3>Use Case</h3>
     * <p>Data quality validation in ETL data pipelines:
     * <ul>
     *     <li>Input validation - ensure IDs match expected format before database insertion</li>
     *     <li>Error collection - gather ALL invalid records (not stopping at first error)</li>
     *     <li>Data quality metrics - track percentage of validation failures</li>
     *     <li>Transaction processing - validate account/order numbers before processing</li>
     * </ul>
     * </p>
     * 
     * <h3>Stream Operation Example</h3>
     * <pre>
     * ids.stream()
     *   .filter(id -> id == null || id.isBlank() || !id.matches("[A-Z]-\\d+"))
     *   .collect(Collectors.toList());
     * </pre>
     * 
     * <h3>Key Concepts</h3>
     * <ul>
     *     <li><strong>Pattern Format:</strong> [A-Z]-\\d+ means: single uppercase letter, dash, one or more digits (e.g., A-100, B-5)</li>
     *     <li><strong>Combined Conditions:</strong> Checks for null, blank, AND format using OR logic</li>
     *     <li><strong>Error Collection Pattern:</strong> Collects ALL invalid entries (not stopping at first)</li>
     *     <li><strong>Filter Inversion:</strong> Uses negated condition (!) to keep invalid entries</li>
     *     <li><strong>Null Handling:</strong> Explicitly checks for null values before calling methods</li>
     *     <li><strong>Regex Matching:</strong> matches() validates format pattern</li>
     * </ul>
     * 
     * @param ids List of ID strings to validate
     * @return StreamResult containing:
     *         <ul>
     *           <li>List of invalid IDs (null, blank, or not matching pattern)</li>
     *           <li>Execution time</li>
     *         </ul>
     * 
     * @see StreamResult Response object with invalid entries
     */
    @PostMapping("/validate-ids")
    @Operation(
        summary = "Validate IDs and collect invalid ones",
        description = """
            Validates IDs against required format: [A-Z]-\\d+ (e.g., A-100, B-5)
            Collects ALL invalid entries (null, blank, or non-matching format).
            
            Demonstrates error collection pattern - gathers all errors rather than failing on first."""
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully validated and collected invalid IDs",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Invalid IDs Response",
                value = """
                    {
                      "approach": "Stream API",
                      "result": ["", "INVALID#", "a-100"],
                      "executionTimeMs": 1
                    }
                    """
            )
        )
    )
    public StreamResult validateIds(
        @Parameter(
            description = "List of ID strings to validate. Valid format: [A-Z]-\\\\d+ (e.g., A-100, B-5). Invalid: null, blank, lowercase letters, missing dash/digits, special characters.",
            example = "[\"A-100\", \"\", \"INVALID#\", \"B-5\", null, \"a-100\"]",
            required = true
        )
        @RequestBody List<String> ids
    ) {
        long start = System.nanoTime();
        
        // Collect all invalid IDs (error collection pattern)
        List<String> invalidIds = ids.stream()
            .filter(id -> id == null                     // Null check
                    || id.isBlank()                      // Blank/whitespace-only check
                    || !id.matches("[A-Z]-\\d+"))        // Format validation: Single uppercase letter-digits
            .collect(Collectors.toList());
        
        long time = (System.nanoTime() - start) / 1_000_000;
        
        return new StreamResult("Stream API", invalidIds, time);
    }

    /**
     * Partitions scores into pass and fail groups based on a threshold.
     * 
     * <h3>Use Case</h3>
     * <p>Binary classification in business systems:
     * <ul>
     *     <li>Grading systems - separate passing and failing scores</li>
     *     <li>Credit scoring - separate approved and rejected applications</li>
     *     <li>Performance reviews - divide employees into tiers</li>
     *     <li>Quality control - separate acceptable and defective items</li>
     * </ul>
     * </p>
     * 
     * <h3>Stream Operation Example</h3>
     * <pre>
     * scores.stream()
     *   .collect(Collectors.partitioningBy(
     *       score -> score >= threshold      // Partitioning predicate (binary split)
     *   ));
     * 
     * // Result map structure:
     * // {
     * //   true: [95, 82, 67, 73],         // Scores >= threshold (passing)
     * //   false: [49, 58]                 // Scores < threshold (failing)
     * // }
     * </pre>
     * 
     * <h3>Key Concepts</h3>
     * <ul>
     *     <li><strong>partitioningBy():</strong> Special groupingBy() for exactly 2 groups (true/false)</li>
     *     <li><strong>Boolean Keys:</strong> Always has keys true and false, even if one list is empty</li>
     *     <li><strong>Guaranteed Presence:</strong> Unlike groupingBy(), both partitions guaranteed to exist</li>
     *     <li><strong>Single-Pass Efficiency:</strong> One pass through data creates both partitions</li>
     *     <li><strong>vs Double Filter:</strong> More efficient than .filter(x>threshold) UNION .filter(x<=threshold)</li>
     * </ul>
     * 
     * @param scores List of numeric scores to partition
     * @param threshold Threshold value for pass/fail (scores >= threshold pass, otherwise fail)
     * @return StreamResult containing:
     *         <ul>
     *           <li>Map with "Pass" and "Fail" keys containing respective score lists</li>
     *           <li>Execution time</li>
     *         </ul>
     * 
     * @see StreamResult Response object with partitioned scores
     */
    @PostMapping("/partition-scores")
    @Operation(
        summary = "Partition scores into pass/fail groups",
        description = """
            Divides scores into two partitions:
            - Pass: scores >= threshold
            - Fail: scores < threshold
            
            Uses partitioningBy() for efficient binary classification.
            Guarantees both partitions exist in result, even if empty."""
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully partitioned scores",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Partitioned Scores Response",
                value = """
                    {
                      "approach": "Stream API",
                      "result": {
                        "Pass": [95, 82, 67, 73],
                        "Fail": [49, 58]
                      },
                      "executionTimeMs": 1
                    }
                    """
            )
        )
    )
    public StreamResult partitionScores(
        @Parameter(
            description = "List of numeric scores to partition (integers)",
            example = "[95, 82, 67, 49, 73, 58]",
            required = true
        )
        @RequestBody List<Integer> scores,
        
        @Parameter(
            description = "Threshold value. Scores >= threshold go to Pass partition, others to Fail.",
            example = "60"
        )
        @RequestParam(defaultValue = "60") int threshold
    ) {
        long start = System.nanoTime();
        
        // Binary partition: true (pass) and false (fail)
        Map<Boolean, List<Integer>> passFail = scores.stream()
            .collect(Collectors.partitioningBy(
                score -> score >= threshold                           // True if score >= threshold (pass)
            ));
        
        // Convert boolean keys to String keys for API response clarity
        Map<String, List<Integer>> result = new LinkedHashMap<>();
        result.put("Pass", passFail.get(true));
        result.put("Fail", passFail.get(false));
        
        long time = (System.nanoTime() - start) / 1_000_000;
        
        return new StreamResult("Stream API", result, time);
    }

    /**
     * Calculates running (cumulative) totals for a list of amounts.
     * 
     * <h3>Use Case</h3>
     * <p>Financial reporting and analytics:
     * <ul>
     *     <li>YTD (Year-To-Date) calculations - cumulative sales through each month</li>
     *     <li>Cashflow analysis - running balance over time</li>
     *     <li>Cumulative inventory - track total stock received over time</li>
     *     <li>Progress tracking - cumulative project milestones</li>
     * </ul>
     * </p>
     * 
     * <h3>Example Calculation</h3>
     * <pre>
     * Input:  [5, 10, 3, 7, 2]
     * Output: [5, 15, 18, 25, 27]  // Each element is sum of all elements up to and including that index
     * 
     * Calculation:
     *   Position 0: 5         (just first element)
     *   Position 1: 5+10=15   (first + second)
     *   Position 2: 5+10+3=18 (first + second + third)
     *   Position 3: 5+10+3+7=25
     *   Position 4: 5+10+3+7+2=27
     * </pre>
     * 
     * <h3>Stream Operation Example</h3>
     * <pre>
     * IntStream.range(0, amounts.size())      // Generate indices 0, 1, 2, ...
     *   .mapToObj(i -> amounts
     *       .subList(0, i + 1)                // Take all elements from 0 to i (inclusive)
     *       .stream()
     *       .mapToInt(Integer::intValue)
     *       .sum()                            // Sum the sublist
     *   )
     *   .collect(Collectors.toList());
     * </pre>
     * 
     * <h3>Key Concepts</h3>
     * <ul>
     *     <li><strong>IntStream.range():</strong> Generates indices for positional processing</li>
     *     <li><strong>Windowing with subList():</strong> Creates expanding window [0..i] for each position</li>
     *     <li><strong>Nested Streams:</strong> Inner stream calculates sum for each window</li>
     *     <li><strong>Cumulative Pattern:</strong> Each result includes all previous values</li>
     *     <li><strong>Performance Note:</strong> O(n²) algorithm - each position requires new subList stream</li>
     * </ul>
     * 
     * @param amounts List of numeric amounts to calculate running totals for
     * @return StreamResult containing:
     *         <ul>
     *           <li>List of cumulative sums at each position</li>
     *           <li>Execution time</li>
     *         </ul>
     * 
     * @see StreamResult Response object with running totals
     */
    @PostMapping("/running-totals")
    @Operation(
        summary = "Calculate running (cumulative) totals",
        description = """
            Computes cumulative sum at each position in the list.
            
            Example: [5, 10, 3, 7, 2] → [5, 15, 18, 25, 27]
            - Position 0: 5
            - Position 1: 5+10=15
            - Position 2: 5+10+3=18
            - Position 3: 5+10+3+7=25
            - Position 4: 5+10+3+7+2=27
            
            Common in financial systems (YTD, cumulative cashflow)."""
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully calculated running totals",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Running Totals Response",
                value = """
                    {
                      "approach": "Stream API",
                      "result": [5, 15, 18, 25, 27],
                      "executionTimeMs": 2
                    }
                    """
            )
        )
    )
    public StreamResult runningTotals(
        @Parameter(
            description = "List of numeric amounts to calculate cumulative totals. Each result element is the sum of all elements from index 0 through that position.",
            example = "[5, 10, 3, 7, 2]",
            required = true
        )
        @RequestBody List<Integer> amounts
    ) {
        long start = System.nanoTime();
        
        // Generate running totals using IntStream for index generation and nested stream for windowing
        List<Integer> runningTotals = IntStream.range(0, amounts.size())
            .mapToObj(i -> 
                amounts.subList(0, i + 1)                             // Window: from start to position i (inclusive)
                    .stream()
                    .mapToInt(Integer::intValue)
                    .sum()                                            // Sum all values in window
            )
            .collect(Collectors.toList());
        
        long time = (System.nanoTime() - start) / 1_000_000;
        
        return new StreamResult("Stream API", runningTotals, time);
    }
}
