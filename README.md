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

### Console Application
From the streams folder:
- Build: `mvn clean package`
- Run: `java -cp target/streams-1.0-SNAPSHOT.jar com.test.StreamTest`

### REST API with Swagger UI
Start the Spring Boot application:
```bash
mvn spring-boot:run
```

Then access:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

The Swagger UI provides:
- Interactive API documentation
- Input forms for each stream example
- Real-time execution with results
- Execution time comparisons
- Sample request bodies

### API Endpoints
**Stream Examples** (`/api/streams`)
- `POST /filter` - Filter orders by status and amount
- `POST /map` - Extract unique customer IDs
- `POST /aggregate` - Calculate total revenue
- `POST /group` - Group orders by status
- `POST /character-frequency` - Character frequency analysis

**Practical Examples** (`/api/practical`)
- `POST /normalize-sort` - Normalize and sort names
- `POST /word-frequency` - Top N word frequencies
- `POST /validate-ids` - Validate ID patterns
- `POST /partition-scores` - Partition pass/fail scores
- `POST /running-totals` - Calculate cumulative sums

## API Usage Examples

### 1. Filter Orders (`POST /api/streams/filter`)
**Sample Input:**
```json
{
  "orderInputs": [
    {"id": "O1", "customerId": "C1", "amount": 150.50, "status": "COMPLETED", "items": ["Item1", "Item2"]},
    {"id": "O2", "customerId": "C2", "amount": 89.99, "status": "PENDING", "items": ["Item3"]},
    {"id": "O3", "customerId": "C1", "amount": 200.00, "status": "COMPLETED", "items": ["Item4"]},
    {"id": "O4", "customerId": "C3", "amount": 45.00, "status": "CANCELLED", "items": ["Item7"]},
    {"id": "O5", "customerId": "C2", "amount": 320.75, "status": "COMPLETED", "items": ["Item8"]}
  ],
  "threshold": 100
}
```
**Sample Output:**
```json
[
  {
    "approach": "Traditional Loop",
    "result": [
      {"id": "O1", "customerId": "C1", "amount": 150.5, "status": "COMPLETED", "items": ["Item1", "Item2"]},
      {"id": "O3", "customerId": "C1", "amount": 200.0, "status": "COMPLETED", "items": ["Item4"]},
      {"id": "O5", "customerId": "C2", "amount": 320.75, "status": "COMPLETED", "items": ["Item8"]}
    ],
    "executionTimeMs": 2
  },
  {
    "approach": "Stream API",
    "result": [
      {"id": "O1", "customerId": "C1", "amount": 150.5, "status": "COMPLETED", "items": ["Item1", "Item2"]},
      {"id": "O3", "customerId": "C1", "amount": 200.0, "status": "COMPLETED", "items": ["Item4"]},
      {"id": "O5", "customerId": "C2", "amount": 320.75, "status": "COMPLETED", "items": ["Item8"]}
    ],
    "executionTimeMs": 1
  }
]
```

### 2. Extract Customer IDs (`POST /api/streams/map`)
**Sample Input:**
```json
[
  {"id": "O1", "customerId": "C1", "amount": 150.50, "status": "COMPLETED", "items": ["Item1"]},
  {"id": "O2", "customerId": "C2", "amount": 89.99, "status": "PENDING", "items": ["Item3"]},
  {"id": "O3", "customerId": "C1", "amount": 200.00, "status": "COMPLETED", "items": ["Item4"]}
]
```
**Sample Output:**
```json
[
  {
    "approach": "Traditional Loop",
    "result": ["C1", "C2"],
    "executionTimeMs": 1
  },
  {
    "approach": "Stream API",
    "result": ["C1", "C2"],
    "executionTimeMs": 0
  }
]
```

### 3. Aggregate Revenue (`POST /api/streams/aggregate`)
**Sample Input:**
```json
[
  {"id": "O1", "customerId": "C1", "amount": 150.50, "status": "COMPLETED", "items": ["Item1"]},
  {"id": "O2", "customerId": "C2", "amount": 89.99, "status": "PENDING", "items": ["Item3"]},
  {"id": "O3", "customerId": "C1", "amount": 200.00, "status": "COMPLETED", "items": ["Item4"]},
  {"id": "O5", "customerId": "C2", "amount": 320.75, "status": "COMPLETED", "items": ["Item8"]}
]
```
**Sample Output:**
```json
[
  {
    "approach": "Traditional Loop",
    "result": "$671.25",
    "executionTimeMs": 1
  },
  {
    "approach": "Stream API (sum)",
    "result": "$671.25",
    "executionTimeMs": 1
  },
  {
    "approach": "Stream API (reduce)",
    "result": "$671.25",
    "executionTimeMs": 1
  }
]
```

### 4. Group Orders (`POST /api/streams/group`)
**Sample Input:**
```json
[
  {"id": "O1", "customerId": "C1", "amount": 150.50, "status": "COMPLETED", "items": ["Item1"]},
  {"id": "O2", "customerId": "C2", "amount": 89.99, "status": "PENDING", "items": ["Item3"]},
  {"id": "O4", "customerId": "C3", "amount": 45.00, "status": "CANCELLED", "items": ["Item7"]}
]
```
**Sample Output:**
```json
[
  {
    "approach": "Stream API",
    "result": {
      "COMPLETED": 1,
      "PENDING": 1,
      "CANCELLED": 1
    },
    "executionTimeMs": 2
  }
]
```

### 5. Character Frequency (`POST /api/streams/character-frequency?text=Haritha Reddy`)
**Sample Input:** Query parameter `text=Haritha Reddy`

**Sample Output:**
```json
[
  {
    "approach": "Stream API",
    "result": {
      "H": 1, "a": 2, "r": 1, "i": 1, "t": 1, "h": 1, " ": 1, "R": 1, "e": 1, "d": 2, "y": 1
    },
    "executionTimeMs": 3
  },
  {
    "approach": "Stream API (letters only)",
    "result": {
      "h": 2, "a": 2, "r": 2, "i": 1, "t": 1, "e": 1, "d": 2, "y": 1
    },
    "executionTimeMs": 2
  }
]
```

### 6. Normalize and Sort Names (`POST /api/practical/normalize-sort`)
**Sample Input:**
```json
[" Alice ", "bob", "ALICE", "  Bob ", "Clara", "clara "]
```
**Sample Output:**
```json
{
  "approach": "Stream API",
  "result": ["alice", "bob", "clara"],
  "executionTimeMs": 2
}
```

### 7. Word Frequency (`POST /api/practical/word-frequency?text=Java streams make data processing with streams concise&topN=3`)
**Sample Input:** 
- Query parameter `text=Java streams make data processing with streams concise`
- Query parameter `topN=3`

**Sample Output:**
```json
{
  "approach": "Stream API",
  "result": {
    "streams": 2,
    "java": 1,
    "make": 1
  },
  "executionTimeMs": 4
}
```

### 8. Validate IDs (`POST /api/practical/validate-ids`)
**Sample Input:**
```json
["A-100", "", "B-200", "  ", "C-300", "INVALID#"]
```
**Sample Output:**
```json
{
  "approach": "Stream API",
  "result": ["", "  ", "INVALID#"],
  "executionTimeMs": 1
}
```

### 9. Partition Scores (`POST /api/practical/partition-scores?threshold=60`)
**Sample Input:**
```json
[95, 82, 67, 49, 73, 58]
```
**Sample Output:**
```json
{
  "approach": "Stream API",
  "result": {
    "Pass": [95, 82, 67, 73],
    "Fail": [49, 58]
  },
  "executionTimeMs": 1
}
```

### 10. Running Totals (`POST /api/practical/running-totals`)
**Sample Input:**
```json
[5, 10, 3, 7, 2]
```
**Sample Output:**
```json
{
  "approach": "Stream API",
  "result": [5, 15, 18, 25, 27],
  "executionTimeMs": 2
}
```

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

### Stream Creation
- `stream()` - Creates a sequential stream from a collection
- `parallelStream()` - Creates a parallel stream for concurrent processing
- `Arrays.stream()` - Creates a stream from an array
- `IntStream.range()` - Creates a stream of integers in a range
- `chars()` - Creates an IntStream of character codes from a String

### Intermediate Operations (Return Stream)
**Intermediate operations are lazy - they don't execute until a terminal operation is called**

- `filter(Predicate)` - Keeps only elements matching the predicate condition. Returns a stream with filtered elements.
  - Example: `.filter(order -> order.getAmount() > 100)` - keeps orders with amount > 100
  
- `map(Function)` - Transforms each element using the provided function. Returns a stream of transformed elements.
  - Example: `.map(Order::getCustomerId)` - extracts customerId from each Order
  
- `mapToInt/mapToDouble/mapToLong()` - Converts to primitive streams for better performance with numeric operations
  - Example: `.mapToDouble(Order::getAmount)` - extracts amounts as primitive doubles
  
- `mapToObj(Function)` - Converts primitive stream back to object stream
  - Example: `.mapToObj(c -> (char) c)` - converts int codes to Character objects
  
- `flatMap(Function)` - Flattens nested structures (stream of streams → single stream)
  - Example: `.flatMap(order -> order.getItems().stream())` - flattens all order items
  
- `sorted()` / `sorted(Comparator)` - Orders elements naturally or by custom comparator
  - Example: `.sorted(Comparator.comparing(Person::name))` - sorts by name
  
- `distinct()` - Removes duplicate elements (uses equals() method)
  
- `limit(n)` - Restricts stream to first n elements (short-circuiting)
  - Example: `.limit(3)` - keeps only first 3 elements
  
- `skip(n)` - Skips first n elements and returns remaining stream

### Terminal Operations (Return Result)
**Terminal operations trigger stream processing and produce final results**

- `collect(Collector)` - Gathers stream elements into a collection or other data structure
  - Example: `.collect(Collectors.toList())` - collects to ArrayList
  
- `forEach(Consumer)` - Performs an action for each element (side effects)
  - Example: `.forEach(System.out::println)` - prints each element
  
- `reduce(identity, accumulator)` - Combines elements into single value using accumulator function
  - Example: `.reduce(0, Integer::sum)` - sums all integers starting from 0
  - Example: `.reduce((a, b) -> a > b ? a : b)` - finds maximum
  
- `count()` - Returns the number of elements in the stream
  
- `sum()` - (Primitive streams only) Sums all numeric values
  
- `min(Comparator)` / `max(Comparator)` - Finds minimum/maximum element
  - Returns Optional<T> to handle empty streams
  
- `findFirst()` / `findAny()` - Locates first/any element (returns Optional)
  
- `allMatch(Predicate)` / `anyMatch(Predicate)` / `noneMatch(Predicate)` - Tests if all/any/none match predicate
  
- `toArray()` - Collects stream elements into an array

### Collectors (Used with collect() terminal operation)
**Collectors provide convenient ways to accumulate stream elements**

- `Collectors.toList()` - Collects to ArrayList
- `Collectors.toSet()` - Collects to HashSet (removes duplicates)
- `Collectors.toCollection(Supplier)` - Collects to specific collection type
  - Example: `.collect(Collectors.toCollection(TreeSet::new))` - collects to sorted TreeSet
  
- `Collectors.toMap(keyMapper, valueMapper)` - Collects to HashMap
  
- `Collectors.groupingBy(classifier)` - Groups elements by key into Map<K, List<V>>
  - Example: `.collect(Collectors.groupingBy(Order::getStatus))` - groups orders by status
  
- `Collectors.groupingBy(classifier, downstream)` - Groups and applies downstream collector
  - Example: `.groupingBy(Order::getStatus, Collectors.counting())` - counts per group
  
- `Collectors.partitioningBy(predicate)` - Special grouping with boolean key (2 groups: true/false)
  - Example: `.collect(Collectors.partitioningBy(score -> score >= 60))` - pass/fail
  
- `Collectors.joining(delimiter)` - Concatenates strings with delimiter
  - Example: `.collect(Collectors.joining(", "))` - joins with comma
  
- `Collectors.counting()` - Counts elements (returns Long)
  
- `Collectors.summingDouble/Int/Long(mapper)` - Sums numeric values
  - Example: `.summingDouble(Order::getAmount)` - sums all amounts
  
- `Collectors.averagingDouble/Int/Long(mapper)` - Calculates average

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
