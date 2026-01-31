package com.test.controller;

import com.test.dto.OrderInput;
import com.test.dto.StreamResult;
import com.test.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST API Controller for Java Stream API Examples
 * 
 * <h2>Overview</h2>
 * This controller provides endpoints that demonstrate fundamental Stream API operations:
 * <ul>
 *     <li><strong>Filtering:</strong> Selecting elements based on conditions</li>
 *     <li><strong>Mapping:</strong> Transforming elements from one type to another</li>
 *     <li><strong>Aggregation:</strong> Combining elements into a single result (sum, reduce)</li>
 *     <li><strong>Grouping:</strong> Partitioning elements into logical groups</li>
 *     <li><strong>Character Analysis:</strong> Text processing with streams</li>
 * </ul>
 * 
 * <h2>Key Benefits of Stream API</h2>
 * <ul>
 *     <li><strong>Readability:</strong> Declarative syntax clearly shows intent (WHAT, not HOW)</li>
 *     <li><strong>Conciseness:</strong> Eliminates boilerplate loop and conditional logic</li>
 *     <li><strong>Composability:</strong> Operations chain naturally, creating clear data pipelines</li>
 *     <li><strong>Parallelization:</strong> Easy to parallelize for large datasets using parallelStream()</li>
 *     <li><strong>Lazy Evaluation:</strong> Intermediate operations are deferred until terminal operation</li>
 *     <li><strong>Functional:</strong> Promotes immutability and reduces side effects</li>
 * </ul>
 * 
 * <h2>Stream Operation Types</h2>
 * <ul>
 *     <li><strong>Intermediate Operations:</strong> filter(), map(), sorted() - return Stream, chainable</li>
 *     <li><strong>Terminal Operations:</strong> collect(), forEach(), reduce() - return final result, end stream</li>
 * </ul>
 * 
 * <h2>Comparison Approach</h2>
 * Each endpoint returns results comparing:
 * <ul>
 *     <li>Traditional imperative approach using loops and conditions</li>
 *     <li>Modern functional approach using Streams API</li>
 *     <li>Execution time for performance analysis (in milliseconds)</li>
 * </ul>
 * 
 * @author Stream API Examples
 * @version 1.0
 * @see Order The order domain model
 * @see OrderInput The order input DTO for API requests
 * @see StreamResult The result wrapper containing approach comparison data
 */
@RestController
@RequestMapping("/api/streams")
@Tag(
    name = "Stream Examples",
    description = """
        Core Java Stream API Examples with Performance Comparisons
        
        Demonstrates fundamental stream operations:
        - Filter: Select orders by multiple conditions (status, amount)
        - Map: Transform and extract data (customer IDs)
        - Aggregate: Sum revenue, calculate totals with reduce()
        - Group: Partition orders by status
        - Analyze: Character frequency in text
        
        Each endpoint compares traditional imperative loops with functional stream approaches,
        showing both the code patterns and execution time for performance insights.
        """
)
public class StreamExamplesController {

    /**
     * Filters orders by status COMPLETED and amount greater than threshold.
     * 
     * <h3>Use Case</h3>
     * <p>In an e-commerce revenue reporting system, extract high-value completed orders for:
     * <ul>
     *     <li>Revenue analytics - only count confirmed transactions</li>
     *     <li>Commission calculations - threshold excludes low-value orders</li>
     *     <li>Financial reporting - accurate actual revenue figures</li>
     * </ul>
     * </p>
     * 
     * <h3>Stream Operation Example</h3>
     * <pre>
     * orders.stream()
     *   .filter(order -> "COMPLETED".equals(order.getStatus()))  // Intermediate: Filter by status
     *   .filter(order -> order.getAmount() > threshold)           // Intermediate: Filter by amount
     *   .collect(Collectors.toList());                            // Terminal: Gather results
     * </pre>
     * 
     * <h3>Key Concepts</h3>
     * <ul>
     *     <li><strong>filter() Operation:</strong> Intermediate operation testing each element against a predicate (boolean condition)</li>
     *     <li><strong>Multiple Filters:</strong> Chaining conditions acts like AND logic - only elements passing all filters proceed</li>
     *     <li><strong>Lazy Evaluation:</strong> Filters don't execute until terminal operation (collect) is called</li>
     *     <li><strong>collect() Terminal:</strong> Gathers filtered stream elements into a List collection</li>
     *     <li><strong>Predicate Methods:</strong> Can use regular lambdas or method references for cleaner code</li>
     * </ul>
     * 
     * @param orderInputs List of order input objects with id, customerId, amount, status, and items
     * @param threshold Minimum amount threshold for filtering (default: 100). Orders with amount <= threshold are excluded.
     * @return List of StreamResult objects containing:
     *         <ul>
     *           <li>Traditional Loop approach results with execution time</li>
     *           <li>Stream API approach results with execution time</li>
     *         </ul>
     * 
     * @see Order The order domain model with all filter fields
     * @see StreamResult Response object with approach name, filtered results, and timing
     */
    @PostMapping("/filter")
    @Operation(
        summary = "Filter orders by status COMPLETED and amount > threshold",
        description = """
            Filters orders applying two conditions:
            1. Status must equal 'COMPLETED' (confirmed transactions only)
            2. Amount must be greater than threshold (minimum value filter)
            
            Demonstrates multiple filter() operations in a stream pipeline.
            Returns both traditional imperative loop and functional stream approach.
            Execution time comparison included (in milliseconds)."""
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully filtered orders using both approaches",
        content = @Content(
            mediaType = "application/json",
            examples = {
                @ExampleObject(
                    name = "Sample Success Response",
                    value = """
                        [
                          {
                            "approach": "Traditional Loop",
                            "result": [
                              {"id": "O1", "customerId": "C001", "amount": 150.50, "status": "COMPLETED", "items": ["Laptop"]},
                              {"id": "O3", "customerId": "C001", "amount": 225.00, "status": "COMPLETED", "items": ["Monitor", "Keyboard"]}
                            ],
                            "executionTimeMs": 2
                          },
                          {
                            "approach": "Stream API",
                            "result": [
                              {"id": "O1", "customerId": "C001", "amount": 150.50, "status": "COMPLETED", "items": ["Laptop"]},
                              {"id": "O3", "customerId": "C001", "amount": 225.00, "status": "COMPLETED", "items": ["Monitor", "Keyboard"]}
                            ],
                            "executionTimeMs": 1
                          }
                        ]
                        """
                )
            }
        )
    )
    public List<StreamResult> filterOrders(
        @Parameter(
            description = "List of order input objects to filter. Each order must include: id (unique identifier), customerId (customer reference), amount (order total), status (COMPLETED/PENDING/CANCELLED), and items (list of product names).",
            required = true,
            example = """
                [
                  {"id": "O1", "customerId": "C001", "amount": 150.50, "status": "COMPLETED", "items": ["Laptop"]},
                  {"id": "O2", "customerId": "C002", "amount": 45.99, "status": "PENDING", "items": ["Mouse"]},
                  {"id": "O3", "customerId": "C001", "amount": 225.00, "status": "COMPLETED", "items": ["Monitor", "Keyboard"]},
                  {"id": "O4", "customerId": "C003", "amount": 89.50, "status": "COMPLETED", "items": ["USB Cable"]}
                ]
                """
        )
        @RequestBody List<OrderInput> orderInputs,
        
        @Parameter(
            description = "Minimum order amount threshold. Orders with amount <= threshold are filtered out. Use to exclude small transactions. Default is 100.",
            example = "100"
        )
        @RequestParam(defaultValue = "100") double threshold
    ) {
        // Convert input DTOs to domain Order objects
        List<Order> orders = convertToOrders(orderInputs);
        List<StreamResult> results = new ArrayList<>();

        // ===== TRADITIONAL IMPERATIVE APPROACH =====
        long start = System.nanoTime();
        List<Order> filteredTraditional = new ArrayList<>();
        for (Order order : orders) {
            // Manually check both conditions with AND logic
            if ("COMPLETED".equals(order.getStatus()) && order.getAmount() > threshold) {
                filteredTraditional.add(order);
            }
        }
        long traditionalTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Traditional Loop", filteredTraditional, traditionalTime));

        // ===== FUNCTIONAL STREAM APPROACH =====
        start = System.nanoTime();
        List<Order> filteredStream = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))  // First condition: status is COMPLETED
            .filter(order -> order.getAmount() > threshold)           // Second condition: amount exceeds threshold
            .collect(Collectors.toList());                            // Terminal operation: gather into List
        long streamTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API", filteredStream, streamTime));

        return results;
    }

    /**
     * Extracts and deduplicates unique customer IDs from orders.
     * 
     * <h3>Use Case</h3>
     * <p>Build a customer contact list or customer database from historical orders:
     * <ul>
     *     <li>Marketing campaigns - get unique customer IDs for mailing lists</li>
     *     <li>Customer retention - identify all customers who have placed orders</li>
     *     <li>Data migration - consolidate customer list from transaction records</li>
     * </ul>
     * </p>
     * 
     * <h3>Stream Operation Example</h3>
     * <pre>
     * orders.stream()
     *   .map(Order::getCustomerId)                    // Transform each order to its customer ID
     *   .collect(Collectors.toSet());                 // Collect into Set (automatic deduplication)
     * </pre>
     * 
     * <h3>Key Concepts</h3>
     * <ul>
     *     <li><strong>map() Transformation:</strong> Intermediate operation that applies a function to each element, returning a new stream</li>
     *     <li><strong>Method References:</strong> Order::getCustomerId is shorthand for order -> order.getCustomerId()</li>
     *     <li><strong>Set Deduplication:</strong> Collectors.toSet() automatically removes duplicate customer IDs</li>
     *     <li><strong>Type Transformation:</strong> Stream of Order objects becomes Stream of Strings (customer IDs)</li>
     * </ul>
     * 
     * @param orderInputs List of order input objects with customer references
     * @return List of StreamResult objects containing:
     *         <ul>
     *           <li>Traditional Loop approach with manual deduplication</li>
     *           <li>Stream API approach with automatic Set deduplication</li>
     *         </ul>
     * 
     * @see Order Order model with customerId field
     * @see StreamResult Response with customer ID set and timing
     */
    @PostMapping("/map")
    @Operation(
        summary = "Extract unique customer IDs from orders",
        description = """
            Transforms orders to customer IDs and deduplicates using a Set.
            Demonstrates the map() operation for data transformation.
            Returns unique customer IDs from the order list."""
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully extracted and deduplicated customer IDs",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Sample Response",
                value = """
                    [
                      {
                        "approach": "Traditional Loop",
                        "result": ["C001", "C002", "C003"],
                        "executionTimeMs": 1
                      },
                      {
                        "approach": "Stream API",
                        "result": ["C001", "C002", "C003"],
                        "executionTimeMs": 0
                      }
                    ]
                    """
            )
        )
    )
    public List<StreamResult> extractCustomerIds(
        @Parameter(
            description = "List of orders to extract customer IDs from. Each order must have a customerId field.",
            required = true,
            example = """
                [
                  {"id": "O1", "customerId": "C001", "amount": 150.50, "status": "COMPLETED", "items": []},
                  {"id": "O2", "customerId": "C002", "amount": 89.99, "status": "PENDING", "items": []},
                  {"id": "O3", "customerId": "C001", "amount": 225.00, "status": "COMPLETED", "items": []},
                  {"id": "O4", "customerId": "C003", "amount": 105.00, "status": "COMPLETED", "items": []}
                ]
                """
        )
        @RequestBody List<OrderInput> orderInputs
    ) {
        // Convert to Order domain objects
        List<Order> orders = convertToOrders(orderInputs);
        List<StreamResult> results = new ArrayList<>();

        // ===== TRADITIONAL IMPERATIVE APPROACH =====
        long start = System.nanoTime();
        Set<String> customerIdsTraditional = new HashSet<>();
        for (Order order : orders) {
            // Manually add each customer ID (HashSet handles deduplication)
            customerIdsTraditional.add(order.getCustomerId());
        }
        long traditionalTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Traditional Loop", customerIdsTraditional, traditionalTime));

        // ===== FUNCTIONAL STREAM APPROACH =====
        start = System.nanoTime();
        Set<String> customerIdsStream = orders.stream()
            .map(Order::getCustomerId)                     // Transform each order to its customer ID string
            .collect(Collectors.toSet());                  // Collect into Set (deduplication automatic)
        long streamTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API", customerIdsStream, streamTime));

        return results;
    }

    /**
     * Calculates total revenue from completed orders using multiple approaches.
     * 
     * <h3>Use Case</h3>
     * <p>Financial reporting in e-commerce platforms:
     * <ul>
     *     <li>Monthly revenue calculation - sum only confirmed transactions</li>
     *     <li>Quarterly earnings reports - aggregate COMPLETED orders</li>
     *     <li>Executive dashboards - display total revenue figures</li>
     * </ul>
     * </p>
     * 
     * <h3>Stream Operation Examples</h3>
     * <pre>
     * // Approach 1: Using mapToDouble().sum() - Type-specific stream
     * orders.stream()
     *   .filter(order -> "COMPLETED".equals(order.getStatus()))
     *   .mapToDouble(Order::getAmount)            // Convert to DoubleStream
     *   .sum();                                    // Terminal: calculate sum
     * 
     * // Approach 2: Using reduce() - General purpose aggregation
     * orders.stream()
     *   .filter(order -> "COMPLETED".equals(order.getStatus()))
     *   .map(Order::getAmount)
     *   .reduce(0.0, Double::sum);                // Reduce with identity and combiner
     * </pre>
     * 
     * <h3>Key Concepts</h3>
     * <ul>
     *     <li><strong>mapToDouble():</strong> Converts Stream to DoubleStream for numeric operations without boxing</li>
     *     <li><strong>DoubleStream.sum():</strong> Terminal operation for numeric streams (more efficient)</li>
     *     <li><strong>reduce():</strong> General aggregation function with identity value (0.0) and binary operator</li>
     *     <li><strong>Boxing/Unboxing:</strong> mapToDouble() avoids wrapper object overhead vs. using Double objects</li>
     *     <li><strong>Filtering Before Aggregation:</strong> Essential for accurate financial calculations</li>
     * </ul>
     * 
     * @param orderInputs List of order input objects with amounts and status
     * @return List of StreamResult objects with three aggregation approaches:
     *         <ul>
     *           <li>Traditional Loop approach</li>
     *           <li>Stream with mapToDouble().sum()</li>
     *           <li>Stream with reduce() aggregation</li>
     *         </ul>
     * 
     * @see Order Order model with amount and status fields
     * @see StreamResult Response with formatted revenue string and timing
     */
    @PostMapping("/aggregate")
    @Operation(
        summary = "Calculate total revenue from completed orders",
        description = """
            Sums the amounts of all COMPLETED orders.
            Demonstrates three aggregation approaches:
            1. Traditional loop with manual accumulation
            2. Stream with mapToDouble().sum() - numeric stream optimization
            3. Stream with reduce() - general purpose aggregation
            
            Useful for understanding different aggregation patterns."""
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully calculated revenue using three approaches",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Sample Response",
                value = """
                    [
                      {
                        "approach": "Traditional Loop",
                        "result": "$575.50",
                        "executionTimeMs": 1
                      },
                      {
                        "approach": "Stream API (sum)",
                        "result": "$575.50",
                        "executionTimeMs": 0
                      },
                      {
                        "approach": "Stream API (reduce)",
                        "result": "$575.50",
                        "executionTimeMs": 1
                      }
                    ]
                    """
            )
        )
    )
    public List<StreamResult> aggregateRevenue(
        @Parameter(
            description = "List of orders to calculate revenue from. Each order must have amount and status fields. Only COMPLETED orders are included in the calculation.",
            required = true,
            example = """
                [
                  {"id": "O1", "customerId": "C001", "amount": 150.50, "status": "COMPLETED", "items": []},
                  {"id": "O2", "customerId": "C002", "amount": 75.00, "status": "PENDING", "items": []},
                  {"id": "O3", "customerId": "C001", "amount": 225.00, "status": "COMPLETED", "items": []},
                  {"id": "O4", "customerId": "C003", "amount": 200.00, "status": "COMPLETED", "items": []}
                ]
                """
        )
        @RequestBody List<OrderInput> orderInputs
    ) {
        // Convert to Order domain objects
        List<Order> orders = convertToOrders(orderInputs);
        List<StreamResult> results = new ArrayList<>();

        // ===== APPROACH 1: TRADITIONAL LOOP =====
        long start = System.nanoTime();
        double totalTraditional = 0.0;
        for (Order order : orders) {
            // Manually filter and accumulate
            if ("COMPLETED".equals(order.getStatus())) {
                totalTraditional += order.getAmount();
            }
        }
        long traditionalTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Traditional Loop", String.format("$%.2f", totalTraditional), traditionalTime));

        // ===== APPROACH 2: STREAM WITH mapToDouble().sum() =====
        // This is the most efficient for numeric operations - uses primitive DoubleStream
        start = System.nanoTime();
        double totalStream = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))   // Filter by status
            .mapToDouble(Order::getAmount)                            // Convert to DoubleStream (no boxing)
            .sum();                                                    // Terminal: sum all values
        long streamTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API (sum)", String.format("$%.2f", totalStream), streamTime));

        // ===== APPROACH 3: STREAM WITH reduce() =====
        // More flexible for general aggregations, but involves boxing Double objects
        start = System.nanoTime();
        double totalReduce = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))   // Filter by status
            .map(Order::getAmount)                                    // Transform to Double stream
            .reduce(0.0, Double::sum);                                // Reduce with identity 0.0 and sum combiner
        long reduceTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API (reduce)", String.format("$%.2f", totalReduce), reduceTime));

        return results;
    }

    /**
     * Groups orders by their status and counts items in each group.
     * 
     * <h3>Use Case</h3>
     * <p>Analytics dashboard for order management systems:
     * <ul>
     *     <li>Operational dashboards - see order distribution across statuses</li>
     *     <li>Business intelligence - track pending, completed, and cancelled orders</li>
     *     <li>SLA monitoring - identify stuck orders in PENDING status</li>
     * </ul>
     * </p>
     * 
     * <h3>Stream Operation Example</h3>
     * <pre>
     * orders.stream()
     *   .collect(Collectors.groupingBy(
     *       Order::getStatus,                    // Classification function: group by status
     *       Collectors.counting()                // Downstream collector: count items in each group
     *   ));
     * </pre>
     * 
     * <h3>Key Concepts</h3>
     * <ul>
     *     <li><strong>groupingBy():</strong> Collector that partitions stream into groups based on a classification function</li>
     *     <li><strong>Downstream Collector:</strong> counting() aggregates each group into a count</li>
     *     <li><strong>Map Result:</strong> Returns Map with status as key, count as value</li>
     *     <li><strong>Auto-Boxing:</strong> Long values auto-box to Long wrapper objects in the result map</li>
     *     <li><strong>Null Handling:</strong> Null status values appear as separate group if present</li>
     * </ul>
     * 
     * @param orderInputs List of order input objects with status fields
     * @return List of StreamResult objects containing:
     *         <ul>
     *           <li>Map with status as key and count as value</li>
     *           <li>Execution time for the grouping operation</li>
     *         </ul>
     * 
     * @see Order Order model with status field
     * @see StreamResult Response with grouped results and timing
     */
    @PostMapping("/group")
    @Operation(
        summary = "Group orders by status and count each group",
        description = """
            Partitions orders into groups based on their status field (COMPLETED, PENDING, CANCELLED, etc.)
            and counts the number of orders in each group.
            
            Demonstrates the groupingBy() collector for data partitioning and aggregation."""
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully grouped orders by status",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Sample Response",
                value = """
                    [
                      {
                        "approach": "Stream API",
                        "result": {
                          "COMPLETED": 8,
                          "PENDING": 3,
                          "CANCELLED": 2
                        },
                        "executionTimeMs": 1
                      }
                    ]
                    """
            )
        )
    )
    public List<StreamResult> groupOrders(
        @Parameter(
            description = "List of orders to group. Each order must have a status field (e.g., COMPLETED, PENDING, CANCELLED).",
            required = true,
            example = """
                [
                  {"id": "O1", "customerId": "C001", "amount": 150.50, "status": "COMPLETED", "items": []},
                  {"id": "O2", "customerId": "C002", "amount": 89.99, "status": "PENDING", "items": []},
                  {"id": "O3", "customerId": "C001", "amount": 225.00, "status": "COMPLETED", "items": []},
                  {"id": "O4", "customerId": "C003", "amount": 105.00, "status": "CANCELLED", "items": []},
                  {"id": "O5", "customerId": "C002", "amount": 65.00, "status": "PENDING", "items": []}
                ]
                """
        )
        @RequestBody List<OrderInput> orderInputs
    ) {
        // Convert to Order domain objects
        List<Order> orders = convertToOrders(orderInputs);
        List<StreamResult> results = new ArrayList<>();

        // ===== STREAM GROUPING APPROACH =====
        long start = System.nanoTime();
        var grouped = orders.stream()
            .collect(Collectors.groupingBy(
                Order::getStatus,                        // Classification: partition by status field
                Collectors.counting()                    // Downstream: count items in each partition
            ));
        long streamTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API", grouped, streamTime));

        return results;
    }

    /**
     * Analyzes character frequency in input text using stream operations.
     * 
     * <h3>Use Case</h3>
     * <p>Text analysis and cryptography applications:
     * <ul>
     *     <li>Cryptography - frequency analysis for cipher breaking</li>
     *     <li>Natural Language Processing - text statistics and validation</li>
     *     <li>Data quality - duplicate character detection</li>
     * </ul>
     * </p>
     * 
     * <h3>Stream Operation Examples</h3>
     * <pre>
     * // All characters including spaces and symbols
     * text.chars()
     *   .mapToObj(c -> (char) c)
     *   .collect(Collectors.groupingBy(
     *       c -> c,
     *       java.util.LinkedHashMap::new,        // Preserve insertion order
     *       Collectors.counting()
     *   ));
     * 
     * // Letters only (filtered and case-normalized)
     * text.toLowerCase()
     *   .chars()
     *   .filter(Character::isLetter)            // Keep only A-Z, a-z
     *   .mapToObj(c -> (char) c)
     *   .collect(Collectors.groupingBy(
     *       c -> c,
     *       java.util.LinkedHashMap::new,
     *       Collectors.counting()
     *   ));
     * </pre>
     * 
     * <h3>Key Concepts</h3>
     * <ul>
     *     <li><strong>chars():</strong> Terminal operation returning IntStream of character codes</li>
     *     <li><strong>mapToObj():</strong> Converts IntStream to Object stream of Character objects</li>
     *     <li><strong>LinkedHashMap:</strong> Preserves insertion order (appearance order in text)</li>
     *     <li><strong>Collectors.counting():</strong> Tallies occurrences in each group</li>
     *     <li><strong>Filter to Letters:</strong> Character::isLetter filters out spaces and symbols</li>
     *     <li><strong>toLowerCase():</strong> Normalizes case for case-insensitive counting</li>
     * </ul>
     * 
     * @param text Input text string to analyze for character frequency
     * @return List of StreamResult objects containing:
     *         <ul>
     *           <li>All characters (including spaces and symbols)</li>
     *           <li>Letters only (A-Z, case-insensitive)</li>
     *         </ul>
     * 
     * @see StreamResult Response with character frequency maps
     */
    @PostMapping("/character-frequency")
    @Operation(
        summary = "Calculate character frequency in text",
        description = """
            Analyzes input text and counts the frequency of each character.
            Provides two variants:
            1. All characters: spaces, symbols, letters
            2. Letters only: filtered to A-Z (case-insensitive)
            
            Uses LinkedHashMap to preserve character appearance order."""
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully calculated character frequencies",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Sample Response",
                value = """
                    [
                      {
                        "approach": "Stream API",
                        "result": {
                          "H": 1,
                          "a": 3,
                          "r": 2,
                          "i": 1,
                          "t": 1,
                          "h": 1,
                          " ": 1,
                          "R": 1,
                          "e": 1,
                          "d": 2,
                          "y": 1
                        },
                        "executionTimeMs": 0
                      },
                      {
                        "approach": "Stream API (letters only)",
                        "result": {
                          "a": 3,
                          "r": 2,
                          "i": 1,
                          "t": 1,
                          "h": 2,
                          "e": 1,
                          "d": 2,
                          "y": 1
                        },
                        "executionTimeMs": 0
                      }
                    ]
                    """
            )
        )
    )
    public List<StreamResult> characterFrequency(
        @Parameter(
            description = "Input text string to analyze. Can contain any characters (spaces, symbols, letters, numbers).",
            example = "Haritha Reddy",
            required = true
        )
        @RequestParam String text
    ) {
        List<StreamResult> results = new ArrayList<>();

        // ===== APPROACH 1: ALL CHARACTERS =====
        long start = System.nanoTime();
        var charCount = text.chars()                                   // Get IntStream of character codes
            .mapToObj(c -> (char) c)                                   // Convert to Character objects
            .collect(Collectors.groupingBy(
                c -> c,                                               // Group by character itself
                java.util.LinkedHashMap::new,                         // Preserve insertion order
                Collectors.counting()                                 // Count occurrences
            ));
        long streamTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API", charCount, streamTime));

        // ===== APPROACH 2: LETTERS ONLY =====
        start = System.nanoTime();
        var letterCount = text.toLowerCase()                          // Normalize to lowercase
            .chars()                                                  // Get IntStream of character codes
            .filter(Character::isLetter)                              // Keep only letters (A-Z, a-z)
            .mapToObj(c -> (char) c)                                  // Convert to Character objects
            .collect(Collectors.groupingBy(
                c -> c,                                               // Group by letter
                java.util.LinkedHashMap::new,                         // Preserve order
                Collectors.counting()                                 // Count occurrences
            ));
        long letterTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API (letters only)", letterCount, letterTime));

        return results;
    }

    /**
     * Converts OrderInput DTO objects to Order domain model objects.
     * 
     * <h3>Purpose</h3>
     * Transforms API input DTOs into domain objects for stream processing.
     * Isolates the API layer from the domain model.
     * 
     * <h3>Implementation</h3>
     * Uses stream mapping to transform each OrderInput to an Order object,
     * maintaining all fields (id, customerId, amount, status, items).
     * 
     * @param inputs List of OrderInput DTO objects from API request
     * @return List of Order domain objects ready for stream processing
     * 
     * @see OrderInput Input DTO from API layer
     * @see Order Domain model for business logic
     */
    private List<Order> convertToOrders(List<OrderInput> inputs) {
        return inputs.stream()
            .map(i -> new Order(i.getId(), i.getCustomerId(), i.getAmount(), i.getStatus(), i.getItems()))
            .collect(Collectors.toList());
    }
}
