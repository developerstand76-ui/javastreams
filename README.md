# StreamTest - Java Streams API Comprehensive Guide

## Overview
StreamTest is a comprehensive educational resource demonstrating the power and versatility of Java Streams API introduced in Java 8. It provides side-by-side comparisons between traditional imperative approaches and modern declarative stream-based solutions.

## Purpose
This class illustrates:
- How to transform traditional loops into efficient stream operations
- Common stream patterns: filtering, mapping, grouping, and reduction
- Performance benefits and code readability improvements
- Best practices for handling collections and data transformations

## File Structure
```
streams/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── com/
                └── test/
                    ├── StreamTest.java
                    ├── model/
                    │   └── Order.java
                    └── examples/
                        ├── AggregationExample.java
                        ├── CharacterFrequencyExample.java
                        ├── ComplexPipelineExample.java
                        ├── FilteringExample.java
                        ├── GroupingExample.java
                        ├── InvalidRecordCollectionExample.java
                        ├── MappingExample.java
                        ├── MultiFieldSortExample.java
                        ├── NormalizeDeduplicateSortExample.java
                        ├── PartitionPassFailExample.java
                        ├── ReduceOperationsExample.java
                        ├── RunningTotalsExample.java
                        └── WordFrequencyTopNExample.java
```

## Build & Run (Maven)
Requirements:
- Java 17+
- Maven 3.8+

From the streams folder:
- Build: `mvn -q clean package`
- Run: `mvn -q exec:java`

## Program Flow

### Execution Entry Point
The program starts with `main()` in `StreamTest` and executes 8 sections of examples in sequence.

### Example 1: FILTERING
**Purpose**: Filter data based on multiple conditions  
**Scenario**: Find completed orders with amounts > $100  
**Approach Comparison**:
- **Traditional**: Uses explicit if statements in a for loop to filter orders
- **Stream**: Uses `filter()` method with lambda predicates
- **Key Benefit**: More concise, readable, and chainable conditions
**Input**: `List<Order>` sample orders  
**Output**: Printed list of matching orders

### Example 2: MAPPING / TRANSFORMATION
**Purpose**: Transform/extract data from objects  
**Scenario**: Extract unique customer IDs from orders  
**Approach Comparison**:
- **Traditional**: Manual loop with HashSet to track uniqueness
- **Stream**: Uses `map()` to extract IDs and `collect(Collectors.toSet())` for deduplication
- **Key Benefit**: Declarative intent - clearly shows data transformation
**Input**: `List<Order>` sample orders  
**Output**: Printed set of unique customer IDs

### Example 3: AGGREGATION
**Purpose**: Combine multiple values into a single result  
**Scenario**: Calculate total revenue from completed orders  
**Approach Comparison**:
- **Traditional**: Loop with running sum accumulator
- **Stream**: `filter()` + `mapToDouble()` + `sum()` or `reduce()`
- **Key Benefit**: Specialized numeric stream operations for efficiency
**Input**: `List<Order>` sample orders  
**Output**: Printed totals (loop, stream sum, stream reduce)

### Example 4: GROUPING
**Purpose**: Partition data into logical groups  
**Scenario**: Group orders by their status (COMPLETED, PENDING, CANCELLED)  
**Approach Comparison**:
- **Traditional**: Manual HashMap management with containsKey checks
- **Stream**: Uses `groupingBy()` collector with customizable count aggregation
- **Key Benefit**: Built-in grouping with optional downstream collectors
**Input**: `List<Order>` sample orders  
**Output**: Printed counts grouped by status

### Example 5: COMPLEX PIPELINE
**Purpose**: Chain multiple operations for advanced transformations  
**Scenario**: Find top 2 customers by total spending (completed orders only)  
**Approach Comparison**:
- **Traditional**: Nested loops with multiple manual sorting steps
- **Stream**: `filter()` → `groupingBy()` → `entrySet()` → `sorted()` → `limit()`
- **Key Benefit**: Multi-stage pipeline is readable as a sequence of transformations
**Input**: `List<Order>` sample orders  
**Output**: Printed top 2 customers and their totals

### Example 6: CHARACTER FREQUENCY
**Purpose**: Analyze character distribution in strings  
**Scenario**: Count character occurrences in "Haritha Reddy"  
**Approach Comparison**:
- **Traditional**: Character array loop with manual frequency map
- **Stream**: Uses `chars()` stream with `groupingBy()` and `counting()` collector
- **Bonus**: Case-insensitive letter-only variant
- **Key Benefit**: Works with primitive streams (IntStream)
**Input**: String "Haritha Reddy"  
**Output**: Printed frequency maps

### Example 7: REDUCE OPERATIONS (12 Sub-Examples)
**Purpose**: Demonstrate the versatile `reduce()` terminal operation  
**Input**: In-method lists for numbers, words, prices, and sample orders  
**Output**: Printed results for each reduce scenario
**Sub-Examples**:

#### 7.1 SUM - Adding all numbers
- Accumulating total from a list
- Output: 81 (5+12+3+18+7+25+9+2)

#### 7.2 PRODUCT - Multiplying all numbers
- Computing factorial-like operations
- Output: 120 (2×3×4×5)

#### 7.3 MAX - Finding maximum value
- Optional-based safe retrieval
- Output: 25

#### 7.4 MIN - Finding minimum value
- Paired with MAX for range detection
- Output: 2

#### 7.5 STRING CONCATENATION
- Direct string joining with reduce
- Output: "HelloWorldJavaStreams"
- Also shows delimiter variant: "Hello, World, Java, Streams"

#### 7.6 COUNT - Counting elements
- Demonstrates identity-neutral counting
- Shows comparison with `count()` method
- Output: 8

#### 7.7 AVERAGE - Custom aggregation
- Combines sum with division for averages
- Compares with `averagingDouble()` collector
- Output: $13.56

#### 7.8 CONDITIONAL AGGREGATION - Sum of even numbers
- Filtering before reduction
- Output: 36 (12+18+2)

#### 7.9 COMPLEX REDUCE - Building summary string
- Creating formatted output strings
- Output: "Numbers: [5, 12, 3, 18, 7, 25, 9, 2]"

#### 7.10 LONGEST STRING - Custom comparison
- Finding max by attribute (length)
- Comparison operations in reduce
- Output: "Streams"

#### 7.11 PARALLEL REDUCE - Large dataset optimization
- Demonstrates parallel stream processing
- Shows identity, accumulator, and combiner parameters
- Output: 5050 (sum of 1-100)

#### 7.12 CUSTOM OBJECT REDUCTION
- Reducing non-primitive types
- Multiple aggregation strategies on same collection
- Combines order details into summary statistics

### Example 8: PRACTICAL USES (One function per class)
**Purpose**: Show real-world stream patterns that appear in daily work  
**Classes**:
- NormalizeDeduplicateSortExample
- WordFrequencyTopNExample
- InvalidRecordCollectionExample
- PartitionPassFailExample
- RunningTotalsExample
- MultiFieldSortExample
**Input**: In-method sample collections/strings per class  
**Output**: Printed results for each practical use case

## Sample Data

### Order Class
`Order` is in `com.test.model.Order` and represents:
- id: order identifier
- customerId: associated customer
- amount: transaction amount
- status: COMPLETED, PENDING, or CANCELLED
- items: items in the order

### Sample Dataset (5 Orders)
```
O1: Customer C1, $150.50, COMPLETED, 2 items
O2: Customer C2, $89.99,  PENDING,   1 item
O3: Customer C1, $200.00, COMPLETED, 3 items
O4: Customer C3, $45.00,  CANCELLED, 1 item
O5: Customer C2, $320.75, COMPLETED, 2 items
```

## Expected Output Structure

### Each Example Displays:
1. **Section Header**: Clearly identifies the example and its purpose
2. **Traditional Approach**: Traditional imperative code with output
3. **Stream API Approach**: Modern declarative code with output
4. **Bonus/Advanced**: Optional variations showing best practices
5. **Key Insights**: Comments explaining performance/readability benefits

### Sample Output Format
```
--- Example 1: FILTERING (Orders: COMPLETED & amount > 100) ---

Traditional Loop:
Order{id='O1', customer='C1', amount=150.5, status='COMPLETED'}
Order{id='O3', customer='C1', amount=200.0, status='COMPLETED'}

Stream API:
Order{id='O1', customer='C1', amount=150.5, status='COMPLETED'}
Order{id='O3', customer='C1', amount=200.0, status='COMPLETED'}
```

## Key Java Stream Concepts Covered

### Intermediate Operations (Return Stream)
- `filter()` - Select elements matching predicate
- `map()` - Transform elements using function
- `sorted()` - Order elements
- `distinct()` - Remove duplicates
- `limit()` - Restrict element count
- `skip()` - Skip first N elements

### Terminal Operations (Return Result)
- `collect()` - Gather elements into collection
- `forEach()` - Consume each element
- `reduce()` - Combine elements into single value
- `count()` - Count elements
- `findFirst()` / `findAny()` - Locate specific element
- `min()` / `max()` - Find extremes

### Collectors
- `toList()`, `toSet()`, `toMap()` - Collection conversions
- `groupingBy()` - Partition by key
- `joining()` - String concatenation
- `counting()`, `summingDouble()` - Aggregations
- `averagingDouble()` - Statistical operations

## Performance Notes

### When to Use Streams
✅ Transforming collections (filter, map, sort)  
✅ Aggregating data (sum, grouping, counting)  
✅ Working with functional operations  
✅ Readable, expressive code is a priority  

### When to Use Traditional Loops
✅ Single-pass iteration with side effects  
✅ Early termination/complex control flow  
✅ Performance-critical tight loops  

## Running the Program

### Compilation
```bash
javac StreamTest.java
```

### Execution
```bash
java com.test.StreamTest
```

### Expected Runtime
< 100ms on modern hardware (primarily I/O for System.out operations)

## Educational Value

This class is ideal for:
- **Java Students**: Learning Stream API fundamentals
- **Developers**: Transitioning from imperative to functional style
- **Code Reviews**: Reference examples for stream best practices
- **Interview Prep**: Demonstrates understanding of modern Java
- **Refactoring**: Template for converting traditional code

## Key Takeaways

1. **Readability**: Stream code reads like a recipe of transformations
2. **Composability**: Operations chain naturally and intuitively
3. **Efficiency**: Streams enable compiler/JVM optimization opportunities
4. **Maintenance**: Declarative code is easier to modify and test
5. **Functionality**: Reduce eliminates loops for complex aggregations

## Common Pitfalls to Avoid

❌ Calling `collect()` inside the stream pipeline (defeats lazy evaluation)  
❌ Using mutable reduce accumulators without thread safety  
❌ Comparing stream performance on tiny datasets  
❌ Over-chaining operations - split complex pipelines for readability  
❌ Forgetting that streams are consumed after terminal operation  

## References

- Java Streams API Documentation
- Lambda Expressions and Functional Interfaces
- Collectors Framework
- Parallel Streams Best Practices

---

**Version**: 1.0  
**Java Version**: 8+  
**Last Updated**: January 2026
