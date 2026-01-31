# Java Streams API - Complete Test Cases with Sample Inputs and Expected Outputs

## Table of Contents
1. [Stream Examples](#stream-examples)
2. [Practical Examples](#practical-examples)

---

## Stream Examples

### 1. Filter Orders
**Endpoint:** `POST /api/streams/filter`  
**Description:** Filters orders with COMPLETED status and amount greater than threshold

**Real-World Use Case:**  
In e-commerce or retail systems, you often need to find high-value completed orders for analytics, commission calculations, or targeted marketing campaigns. This example demonstrates how to efficiently filter a collection based on multiple criteria.

**What It Does:**
- Takes a list of orders and a monetary threshold
- Filters out orders that are NOT completed
- Further filters to only include orders above the threshold amount
- Returns the matching orders

**Stream Operations Used:**
- `stream()` - Converts the collection to a stream for processing
- `filter()` - Applies two predicates (status check and amount comparison)
- `collect(Collectors.toList())` - Gathers filtered results back into a list

**Why This Pattern Is Useful:**
- Combines multiple filtering conditions cleanly without nested if statements
- More readable than traditional loop-based filtering
- Easy to add or remove filter conditions
- Can be parallelized for large datasets with `.parallelStream()`

#### Sample Input 1 - Basic Filtering
```json
{
  "orderInputs": [
    {
      "id": "O1",
      "customerId": "C1",
      "amount": 150.50,
      "status": "COMPLETED",
      "items": ["Laptop", "Mouse"]
    },
    {
      "id": "O2",
      "customerId": "C2",
      "amount": 89.99,
      "status": "PENDING",
      "items": ["Keyboard"]
    },
    {
      "id": "O3",
      "customerId": "C1",
      "amount": 200.00,
      "status": "COMPLETED",
      "items": ["Monitor"]
    },
    {
      "id": "O4",
      "customerId": "C3",
      "amount": 45.00,
      "status": "CANCELLED",
      "items": ["Cable"]
    },
    {
      "id": "O5",
      "customerId": "C2",
      "amount": 320.75,
      "status": "COMPLETED",
      "items": ["Desktop", "Speakers"]
    }
  ],
  "threshold": 100
}
```

**Expected Output:**
```json
[
  {
    "approach": "Traditional Loop",
    "result": [
      {
        "id": "O1",
        "customerId": "C1",
        "amount": 150.5,
        "status": "COMPLETED",
        "items": ["Laptop", "Mouse"]
      },
      {
        "id": "O3",
        "customerId": "C1",
        "amount": 200.0,
        "status": "COMPLETED",
        "items": ["Monitor"]
      },
      {
        "id": "O5",
        "customerId": "C2",
        "amount": 320.75,
        "status": "COMPLETED",
        "items": ["Desktop", "Speakers"]
      }
    ],
    "executionTimeMs": 2
  },
  {
    "approach": "Stream API",
    "result": [
      {
        "id": "O1",
        "customerId": "C1",
        "amount": 150.5,
        "status": "COMPLETED",
        "items": ["Laptop", "Mouse"]
      },
      {
        "id": "O3",
        "customerId": "C1",
        "amount": 200.0,
        "status": "COMPLETED",
        "items": ["Monitor"]
      },
      {
        "id": "O5",
        "customerId": "C2",
        "amount": 320.75,
        "status": "COMPLETED",
        "items": ["Desktop", "Speakers"]
      }
    ],
    "executionTimeMs": 1
  }
]
```

#### Sample Input 2 - High Threshold (No Results)
```json
{
  "orderInputs": [
    {
      "id": "O1",
      "customerId": "C1",
      "amount": 50.00,
      "status": "COMPLETED",
      "items": ["Book"]
    },
    {
      "id": "O2",
      "customerId": "C2",
      "amount": 75.00,
      "status": "COMPLETED",
      "items": ["Pen"]
    }
  ],
  "threshold": 1000
}
```

**Expected Output:**
```json
[
  {
    "approach": "Traditional Loop",
    "result": [],
    "executionTimeMs": 0
  },
  {
    "approach": "Stream API",
    "result": [],
    "executionTimeMs": 0
  }
]
```

---

### 2. Extract Customer IDs (Map)
**Endpoint:** `POST /api/streams/map`  
**Description:** Extracts unique customer IDs from orders

**Real-World Use Case:**  
When analyzing order data, you often need to know how many unique customers made purchases, or create a list for sending notifications. This example shows how to extract and deduplicate data from a collection of complex objects.

**What It Does:**
- Takes a list of orders (each with customer information)
- Extracts just the customer ID from each order
- Automatically removes duplicate customer IDs
- Returns a set of unique customer IDs

**Stream Operations Used:**
- `stream()` - Creates a stream from the order list
- `map(Order::getCustomerId)` - Transforms each Order object to just its customerId string (projection/transformation)
- `collect(Collectors.toSet())` - Collects into a Set, which naturally handles deduplication

**Why This Pattern Is Useful:**
- Separates data extraction from deduplication logic
- No need to manually check for duplicates with contains() or containsKey()
- Clear intent: "map these orders to their customer IDs and collect unique values"
- Works efficiently with method references (Order::getCustomerId)
- Functional approach makes the transformation pipeline explicit

#### Sample Input 1 - Multiple Customers with Duplicates
```json
[
  {
    "id": "O1",
    "customerId": "C1",
    "amount": 150.50,
    "status": "COMPLETED",
    "items": ["Item1"]
  },
  {
    "id": "O2",
    "customerId": "C2",
    "amount": 89.99,
    "status": "PENDING",
    "items": ["Item2"]
  },
  {
    "id": "O3",
    "customerId": "C1",
    "amount": 200.00,
    "status": "COMPLETED",
    "items": ["Item3"]
  },
  {
    "id": "O4",
    "customerId": "C3",
    "amount": 120.00,
    "status": "COMPLETED",
    "items": ["Item4"]
  },
  {
    "id": "O5",
    "customerId": "C2",
    "amount": 99.00,
    "status": "CANCELLED",
    "items": ["Item5"]
  }
]
```

**Expected Output:**
```json
[
  {
    "approach": "Traditional Loop",
    "result": ["C1", "C2", "C3"],
    "executionTimeMs": 1
  },
  {
    "approach": "Stream API",
    "result": ["C1", "C2", "C3"],
    "executionTimeMs": 1
  }
]
```

#### Sample Input 2 - Single Customer
```json
[
  {
    "id": "O1",
    "customerId": "C100",
    "amount": 50.00,
    "status": "COMPLETED",
    "items": ["Item1"]
  },
  {
    "id": "O2",
    "customerId": "C100",
    "amount": 75.00,
    "status": "PENDING",
    "items": ["Item2"]
  }
]
```

**Expected Output:**
```json
[
  {
    "approach": "Traditional Loop",
    "result": ["C100"],
    "executionTimeMs": 0
  },
  {
    "approach": "Stream API",
    "result": ["C100"],
    "executionTimeMs": 0
  }
]
```

---

### 3. Aggregate Revenue
**Endpoint:** `POST /api/streams/aggregate`  
**Description:** Calculates total revenue from all completed orders

**Real-World Use Case:**  
Financial reporting, revenue tracking, and business analytics require calculating totals from transaction data. This example demonstrates aggregation operations - combining multiple values into a single result using both specialized stream methods and the general-purpose reduce operation.

**What It Does:**
- Takes a list of orders with various statuses and amounts
- Filters to only COMPLETED orders (only count actual revenue, not pending/cancelled)
- Extracts the amount from each order
- Sums all the amounts to get total revenue
- Shows three different approaches: traditional loop, stream sum(), and stream reduce()

**Stream Operations Used:**
- `filter()` - Selects only completed orders
- `mapToDouble(Order::getAmount)` - Converts each order to its amount value, creating a DoubleStream
- `sum()` - Specialized terminal operation for DoubleStream that adds all values
- `reduce(0.0, Double::sum)` - General-purpose accumulator that builds the total incrementally

**Why This Pattern Is Useful:**
- Handles the common "filter then aggregate" pattern cleanly
- Specialized numeric streams (DoubleStream) avoid boxing/unboxing overhead
- `reduce()` is flexible and can be adapted for min, max, average, or custom aggregations
- Clearly separates filtering logic from calculation logic
- Easy to modify (e.g., change filter criteria, calculate average instead of sum)

#### Sample Input 1 - Mixed Status Orders
```json
[
  {
    "id": "O1",
    "customerId": "C1",
    "amount": 150.50,
    "status": "COMPLETED",
    "items": ["Item1"]
  },
  {
    "id": "O2",
    "customerId": "C2",
    "amount": 89.99,
    "status": "PENDING",
    "items": ["Item2"]
  },
  {
    "id": "O3",
    "customerId": "C1",
    "amount": 200.00,
    "status": "COMPLETED",
    "items": ["Item3"]
  },
  {
    "id": "O4",
    "customerId": "C3",
    "amount": 45.00,
    "status": "CANCELLED",
    "items": ["Item4"]
  },
  {
    "id": "O5",
    "customerId": "C2",
    "amount": 320.75,
    "status": "COMPLETED",
    "items": ["Item5"]
  }
]
```

**Expected Output:**
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
    "executionTimeMs": 0
  }
]
```

#### Sample Input 2 - No Completed Orders
```json
[
  {
    "id": "O1",
    "customerId": "C1",
    "amount": 100.00,
    "status": "PENDING",
    "items": ["Item1"]
  },
  {
    "id": "O2",
    "customerId": "C2",
    "amount": 200.00,
    "status": "CANCELLED",
    "items": ["Item2"]
  }
]
```

**Expected Output:**
```json
[
  {
    "approach": "Traditional Loop",
    "result": "$0.00",
    "executionTimeMs": 0
  },
  {
    "approach": "Stream API (sum)",
    "result": "$0.00",
    "executionTimeMs": 0
  },
  {
    "approach": "Stream API (reduce)",
    "result": "$0.00",
    "executionTimeMs": 0
  }
]
```

---

### 4. Group Orders by Status
**Endpoint:** `POST /api/streams/group`  
**Description:** Groups orders by status and counts each group

**Real-World Use Case:**  
Dashboard analytics, order pipeline reporting, and status tracking require grouping data by categories. This example shows how to partition a collection by a property and aggregate within each group - a pattern commonly needed for reporting and analytics.

**What It Does:**
- Takes a list of orders with different statuses (COMPLETED, PENDING, CANCELLED)
- Groups orders by their status field
- Counts how many orders are in each status group
- Returns a map where keys are status values and values are counts

**Stream Operations Used:**
- `stream()` - Creates the processing pipeline
- `collect(Collectors.groupingBy(...))` - The star of this example; groups elements by a classifier function
  - First parameter: `Order::getStatus` - The classifier that determines which group each order belongs to
  - Second parameter: `Collectors.counting()` - Downstream collector that counts elements in each group
- Alternative: Can use `groupingBy()` without downstream collector to get `Map<Status, List<Order>>`

**Why This Pattern Is Useful:**
- Replaces complex manual map management (checking if key exists, initializing lists/counters)
- Single line does what traditionally requires multiple loops and conditional checks
- Flexible: can easily swap counting() for summingDouble(), averaging(), etc.
- Can nest groupingBy() for multi-level grouping (e.g., group by status, then by customer)
- Type-safe and IDE-friendly with proper generics
- Perfect for generating SQL-like GROUP BY style reports in Java

#### Sample Input 1 - Diverse Status Mix
```json
[
  {
    "id": "O1",
    "customerId": "C1",
    "amount": 150.50,
    "status": "COMPLETED",
    "items": ["Item1"]
  },
  {
    "id": "O2",
    "customerId": "C2",
    "amount": 89.99,
    "status": "PENDING",
    "items": ["Item2"]
  },
  {
    "id": "O3",
    "customerId": "C1",
    "amount": 200.00,
    "status": "COMPLETED",
    "items": ["Item3"]
  },
  {
    "id": "O4",
    "customerId": "C3",
    "amount": 45.00,
    "status": "CANCELLED",
    "items": ["Item4"]
  },
  {
    "id": "O5",
    "customerId": "C2",
    "amount": 320.75,
    "status": "COMPLETED",
    "items": ["Item5"]
  },
  {
    "id": "O6",
    "customerId": "C4",
    "amount": 99.00,
    "status": "PENDING",
    "items": ["Item6"]
  }
]
```

**Expected Output:**
```json
[
  {
    "approach": "Stream API",
    "result": {
      "COMPLETED": 3,
      "PENDING": 2,
      "CANCELLED": 1
    },
    "executionTimeMs": 2
  }
]
```

#### Sample Input 2 - All Same Status
```json
[
  {
    "id": "O1",
    "customerId": "C1",
    "amount": 100.00,
    "status": "COMPLETED",
    "items": ["Item1"]
  },
  {
    "id": "O2",
    "customerId": "C2",
    "amount": 200.00,
    "status": "COMPLETED",
    "items": ["Item2"]
  },
  {
    "id": "O3",
    "customerId": "C3",
    "amount": 150.00,
    "status": "COMPLETED",
    "items": ["Item3"]
  }
]
```

**Expected Output:**
```json
[
  {
    "approach": "Stream API",
    "result": {
      "COMPLETED": 3
    },
    "executionTimeMs": 1
  }
]
```

---

### 5. Character Frequency Analysis
**Endpoint:** `POST /api/streams/character-frequency?text={text}`  
**Description:** Analyzes character frequency in given text

**Real-World Use Case:**  
Text analysis, cryptography, data validation, and natural language processing often require character frequency analysis. This example demonstrates working with primitive streams (IntStream), character manipulation, and frequency counting - useful for analyzing passwords, detecting encoding issues, or implementing simple encryption algorithms.

**What It Does:**
- Takes a text string as input
- Converts the string to individual characters
- Counts how many times each character appears
- Provides two versions: one including all characters (spaces, punctuation) and one with only letters
- Returns a map where keys are characters and values are their occurrence counts

**Stream Operations Used:**
- `chars()` - Converts String to IntStream of character codes (primitive stream)
- `mapToObj(c -> (char) c)` - Converts int codes back to Character objects for easier manipulation
- `filter(Character::isLetter)` - For the letters-only version, filters out non-alphabetic characters
- `map(Character::toLowerCase)` - Normalizes case for case-insensitive counting
- `collect(Collectors.groupingBy(c -> c, Collectors.counting()))` - Groups identical characters and counts occurrences

**Why This Pattern Is Useful:**
- Handles Unicode characters correctly through Java's character handling
- Single pipeline from string to frequency map
- Easy to add filters (ignore spaces, convert case, filter punctuation)
- Efficient for analyzing passwords (check for repeated characters)
- Foundation for more complex text analytics (word frequency, n-gram analysis)
- No manual HashMap initialization or counter management
- Can be adapted for byte-level analysis or multi-character sequences

#### Test Case 1 - Simple Text
**URL:** `/api/streams/character-frequency?text=Hello World`

**Expected Output:**
```json
[
  {
    "approach": "Stream API",
    "result": {
      "H": 1,
      "e": 1,
      "l": 3,
      "o": 2,
      " ": 1,
      "W": 1,
      "r": 1,
      "d": 1
    },
    "executionTimeMs": 2
  },
  {
    "approach": "Stream API (letters only)",
    "result": {
      "h": 1,
      "e": 1,
      "l": 3,
      "o": 2,
      "w": 1,
      "r": 1,
      "d": 1
    },
    "executionTimeMs": 1
  }
]
```

#### Test Case 2 - Text with Numbers and Special Characters
**URL:** `/api/streams/character-frequency?text=Java@2024!`

**Expected Output:**
```json
[
  {
    "approach": "Stream API",
    "result": {
      "J": 1,
      "a": 2,
      "v": 1,
      "@": 1,
      "2": 1,
      "0": 1,
      "4": 1,
      "!": 1
    },
    "executionTimeMs": 1
  },
  {
    "approach": "Stream API (letters only)",
    "result": {
      "j": 1,
      "a": 2,
      "v": 1
    },
    "executionTimeMs": 1
  }
]
```

#### Test Case 3 - Name Analysis
**URL:** `/api/streams/character-frequency?text=Haritha Reddy`

**Expected Output:**
```json
[
  {
    "approach": "Stream API",
    "result": {
      "H": 1,
      "a": 2,
      "r": 1,
      "i": 1,
      "t": 1,
      "h": 1,
      " ": 1,
      "R": 1,
      "e": 1,
      "d": 2,
      "y": 1
    },
    "executionTimeMs": 2
  },
  {
    "approach": "Stream API (letters only)",
    "result": {
      "h": 2,
      "a": 2,
      "r": 2,
      "i": 1,
      "t": 1,
      "e": 1,
      "d": 2,
      "y": 1
    },
    "executionTimeMs": 1
  }
]
```

---

## Practical Examples

### 6. Normalize and Sort Names
**Endpoint:** `POST /api/practical/normalize-sort`  
**Description:** Normalizes names (trim, lowercase) and sorts while removing duplicates

**Real-World Use Case:**  
Data cleansing is essential when importing user data from different sources (CSV files, web forms, databases). Names often come with inconsistent formatting: extra spaces, mixed case, duplicates. This example shows a complete data normalization and deduplication pipeline - critical for maintaining data quality in user management systems, mailing lists, or contact databases.

**What It Does:**
- Takes a list of names that may have:
  - Leading/trailing whitespace ("  John  ")
  - Inconsistent capitalization ("ALICE", "alice", "Alice")
  - Duplicate entries
- Normalizes each name by trimming whitespace and converting to lowercase
- Removes duplicates automatically
- Sorts the cleaned names alphabetically
- Returns a clean, deduplicated, sorted list

**Stream Operations Used:**
- `stream()` - Initiates the processing pipeline
- `map(String::trim)` - Removes leading and trailing whitespace
- `map(String::toLowerCase)` - Normalizes to lowercase for case-insensitive deduplication
- `collect(Collectors.toCollection(TreeSet::new))` - Collects into TreeSet which:
  - Automatically removes duplicates (Set behavior)
  - Maintains sorted order (Tree structure)
  - Provides O(log n) insertion while maintaining order

**Why This Pattern Is Useful:**
- Single pipeline handles three common tasks: normalize, deduplicate, sort
- TreeSet does the heavy lifting - no manual sorting or duplicate checking
- Perfect for cleaning user input before database insertion
- Can easily extend with additional normalization (remove special characters, standardize format)
- Immutable transformation - doesn't modify original data
- Common in ETL (Extract, Transform, Load) processes
- Foundation for fuzzy matching algorithms (normalize before comparing)

#### Sample Input 1 - Mixed Case with Whitespace
```json
[
  " Alice ",
  "bob",
  "ALICE",
  "  Bob ",
  "Charlie",
  "alice",
  "clara ",
  "Clara"
]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": [
    "alice",
    "bob",
    "charlie",
    "clara"
  ],
  "executionTimeMs": 2
}
```

#### Sample Input 2 - Already Clean Data
```json
[
  "adam",
  "betty",
  "charlie",
  "diana"
]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": [
    "adam",
    "betty",
    "charlie",
    "diana"
  ],
  "executionTimeMs": 1
}
```

#### Sample Input 3 - Many Duplicates
```json
[
  "JOHN",
  "john",
  "John",
  " john ",
  "JANE",
  "jane",
  "Jane"
]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": [
    "jane",
    "john"
  ],
  "executionTimeMs": 1
}
```

---

### 7. Word Frequency Analysis (Top N)
**Endpoint:** `POST /api/practical/word-frequency?text={text}&topN={n}`  
**Description:** Finds top N most frequent words in text

**Real-World Use Case:**  
Text analytics, SEO optimization, content analysis, and natural language processing require identifying the most common words. This is used in search engines for keyword extraction, in customer feedback analysis to find trending topics, in document summarization, and in spam detection. The "Top-N" pattern is crucial for handling large datasets where you only need the most significant results.

**What It Does:**
- Takes a text string and splits it into individual words
- Normalizes words (converts to lowercase for case-insensitive analysis)
- Counts how many times each word appears in the text
- Sorts words by frequency (most common first)
- Returns only the top N most frequent words with their counts
- Maintains insertion order in results using LinkedHashMap

**Stream Operations Used:**
- `split("\\s+")` - Splits text into words on whitespace
- `Arrays.stream()` - Creates stream from word array
- `map(String::toLowerCase)` - Normalizes case for accurate counting
- `collect(Collectors.groupingBy(..., Collectors.counting()))` - Groups words and counts occurrences
- `entrySet().stream()` - Converts map to stream of key-value pairs for sorting
- `sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))` - Sorts by count descending
- `limit(topN)` - Keeps only top N results (crucial for large texts)
- `collect(Collectors.toMap(..., LinkedHashMap::new))` - Preserves sort order in final map

**Why This Pattern Is Useful:**
- Foundation for text mining and document classification
- Used in building word clouds and tag clouds
- Critical for SEO: identifies main topics/keywords in content
- Can detect spam or unusual patterns (repeated words)
- Efficient even with large documents due to limit() optimization
- Easily extensible: add stop-word filtering, stemming, n-gram analysis
- Pattern applicable to any "count and rank" scenario (products, users, events)

#### Test Case 1 - Technical Article
**URL:** `/api/practical/word-frequency?text=Java streams make data processing with streams concise&topN=3`

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": {
    "streams": 2,
    "java": 1,
    "make": 1
  },
  "executionTimeMs": 3
}
```

#### Test Case 2 - Marketing Text
**URL:** `/api/practical/word-frequency?text=Best product best quality best price best service best experience&topN=2`

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": {
    "best": 5,
    "product": 1
  },
  "executionTimeMs": 2
}
```

#### Test Case 3 - Longer Paragraph
**URL:** `/api/practical/word-frequency?text=The quick brown fox jumps over the lazy dog the dog sleeps&topN=4`

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": {
    "the": 3,
    "dog": 2,
    "quick": 1,
    "brown": 1
  },
  "executionTimeMs": 2
}
```

---

### 8. Validate IDs
**Endpoint:** `POST /api/practical/validate-ids`  
**Description:** Finds invalid IDs that don't match pattern: Letter-Number (e.g., A-100)

**Real-World Use Case:**  
Data validation is critical in any system that imports or receives external data. Product codes, employee IDs, transaction IDs often follow specific formats. This example demonstrates pattern-based validation using regular expressions - essential for data quality checks, import validation, and detecting corrupted or malicious data. It's commonly used in data migration, API input validation, and batch processing systems.

**What It Does:**
- Takes a list of ID strings that should follow a specific format
- Expected format: Single letter, hyphen, one or more digits (e.g., "A-100", "Z-999")
- Checks each ID against this pattern using regex
- Collects all IDs that DON'T match the pattern (invalid ones)
- Also catches empty strings and whitespace-only entries
- Returns list of problematic IDs for error reporting or correction

**Stream Operations Used:**
- `stream()` - Creates processing pipeline from ID list
- `filter(id -> !id.matches("[A-Z]-\\d+"))` - Applies regex validation, keeps non-matching IDs
  - `[A-Z]` - Single uppercase letter
  - `-` - Literal hyphen character
  - `\\d+` - One or more digits
  - `!` negation - we want the ones that DON'T match
- `collect(Collectors.toList())` - Gathers invalid IDs into a list for reporting

**Why This Pattern Is Useful:**
- Centralizes validation logic for reusability and maintenance
- Regex provides flexible pattern matching for complex formats
- Returns clear list of problems for error reporting to users
- Can be adapted for any ID format: UUID validation, email validation, phone numbers
- Perfect for ETL pipelines: validate before insert, report errors without stopping process
- Prevents invalid data from entering your system
- Can be combined with other validations (length checks, checksum validation)
- Foundation for building comprehensive data quality frameworks
- Easy to unit test with various valid/invalid examples

#### Sample Input 1 - Mixed Valid and Invalid
```json
[
  "A-100",
  "",
  "B-200",
  "  ",
  "C-300",
  "INVALID#",
  "D-999",
  "E123",
  "F-"
]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": [
    "",
    "  ",
    "INVALID#",
    "E123",
    "F-"
  ],
  "executionTimeMs": 1
}
```

#### Sample Input 2 - All Valid IDs
```json
[
  "A-100",
  "B-200",
  "C-300",
  "Z-999"
]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": [],
  "executionTimeMs": 0
}
```

#### Sample Input 3 - All Invalid IDs
```json
[
  "123",
  "ABC",
  "A B-100",
  "!@#$",
  ""
]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": [
    "123",
    "ABC",
    "A B-100",
    "!@#$",
    ""
  ],
  "executionTimeMs": 1
}
```

---

### 9. Partition Scores (Pass/Fail)
**Endpoint:** `POST /api/practical/partition-scores?threshold={threshold}`  
**Description:** Partitions scores into Pass (≥ threshold) and Fail (< threshold)

**Real-World Use Case:**  
Binary classification is fundamental in many domains: student grades (pass/fail), quality control (accept/reject), customer segmentation (active/inactive), inventory management (in-stock/out-of-stock). This example demonstrates partitioning - splitting a collection into exactly two groups based on a predicate. It's more efficient and semantic than filtering twice, and the pattern is used in decision-making systems, reporting dashboards, and batch processing workflows.

**What It Does:**
- Takes a list of numeric scores (test scores, ratings, measurements)
- Takes a threshold value that defines the pass/fail boundary
- Evaluates each score against the threshold
- Splits scores into exactly two groups:
  - "Pass": scores greater than or equal to threshold
  - "Fail": scores less than threshold
- Returns a map with two keys ("Pass" and "Fail") containing their respective score lists
- Preserves original order within each partition

**Stream Operations Used:**
- `stream()` - Creates the processing pipeline
- `collect(Collectors.partitioningBy(...))` - The key operation:
  - Takes a boolean predicate (score >= threshold)
  - Creates a `Map<Boolean, List<Integer>>` with exactly two entries (true/false)
  - More efficient than two separate filter operations
  - Guarantees both keys exist even if one partition is empty
- Custom formatting to convert Boolean keys (true/false) to readable labels ("Pass"/"Fail")

**Why This Pattern Is Useful:**
- Single pass through data (more efficient than filter twice)
- Guaranteed to produce both categories (no missing keys in result map)
- Perfect for A/B testing analysis, cohort separation, binary classification
- Clean separation of data without losing any elements
- Can be nested for multi-level partitioning (e.g., partition by pass/fail, then by grade level)
- Foundation for more complex classification (combine with groupingBy for multi-class)
- Used in machine learning preprocessing (split train/test data)
- Excellent for generating statistical reports with category breakdowns
- Thread-safe and parallelizable for large datasets

#### Test Case 1 - Student Scores (Threshold 60)
**URL:** `/api/practical/partition-scores?threshold=60`

**Sample Input:**
```json
[95, 82, 67, 49, 73, 58, 91, 45, 88, 52]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": {
    "Pass": [95, 82, 67, 73, 91, 88],
    "Fail": [49, 58, 45, 52]
  },
  "executionTimeMs": 1
}
```

#### Test Case 2 - All Pass (Threshold 50)
**URL:** `/api/practical/partition-scores?threshold=50`

**Sample Input:**
```json
[85, 90, 75, 100, 65]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": {
    "Pass": [85, 90, 75, 100, 65],
    "Fail": []
  },
  "executionTimeMs": 1
}
```

#### Test Case 3 - All Fail (Threshold 95)
**URL:** `/api/practical/partition-scores?threshold=95`

**Sample Input:**
```json
[45, 60, 70, 85, 90]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": {
    "Pass": [],
    "Fail": [45, 60, 70, 85, 90]
  },
  "executionTimeMs": 1
}
```

#### Test Case 4 - Edge Case at Threshold (Threshold 75)
**URL:** `/api/practical/partition-scores?threshold=75`

**Sample Input:**
```json
[74, 75, 76, 75, 74]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": {
    "Pass": [75, 76, 75],
    "Fail": [74, 74]
  },
  "executionTimeMs": 1
}
```

---

### 10. Running Totals (Cumulative Sum)
**Endpoint:** `POST /api/practical/running-totals`  
**Description:** Calculates cumulative sum at each position

**Real-World Use Case:**  
Running totals (also called cumulative sums) are essential in financial reporting, sales tracking, and time-series analysis. Used for: calculating year-to-date sales, tracking cumulative spending against budgets, showing progress toward goals, analyzing cash flow, computing running balances in bank accounts, and visualizing cumulative metrics in dashboards. This pattern is fundamental in accounting, analytics dashboards, progress tracking, and any scenario where you need to see accumulated values over time or sequence.

**What It Does:**
- Takes a list of numbers representing individual values (daily sales, transactions, scores, etc.)
- Calculates the cumulative sum at each position
- Each result element is the sum of all elements up to and including that position
- Example: Input [5, 10, 3] → Output [5, 15, 18]
  - Position 0: 5 (just the first element)
  - Position 1: 5 + 10 = 15 (sum of first two)
  - Position 2: 5 + 10 + 3 = 18 (sum of all three)
- Preserves the length of the original list
- Returns the cumulative sequence

**Stream Operations Used:**
- `IntStream.range(0, numbers.size())` - Creates indices 0 to n-1 for positional access
- `map(i -> numbers.subList(0, i + 1))` - For each index, creates sublist from start to current position
- `mapToInt(subList -> subList.stream().mapToInt(Integer::intValue).sum())` - Sums each sublist
- `boxed()` - Converts IntStream back to Stream<Integer> for collection
- `collect(Collectors.toList())` - Gathers results into final list

**Alternative Approach (More Efficient):**
Using mutable accumulator (not shown but common):
- Use a running sum variable
- Update it in each iteration
- More efficient (O(n) vs O(n²)) but less purely functional

**Why This Pattern Is Useful:**
- Critical for financial reports: YTD (Year-To-Date), QTD (Quarter-To-Date) calculations
- Enables visualization of trends: how metrics accumulate over time
- Used in progress tracking: "You've earned $X so far this month"
- Foundation for moving averages and time-series analysis
- Essential in budget tracking: cumulative spending vs budget limit
- Can identify inflection points where cumulative values cross thresholds
- Useful in inventory: track cumulative receipts/shipments
- Applicable to any sequential accumulation problem (page views, sign-ups, events)
- Pattern extends to cumulative products, running max/min, rolling averages

#### Sample Input 1 - Simple Sequence
```json
[5, 10, 3, 7, 2]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": [5, 15, 18, 25, 27],
  "executionTimeMs": 2
}
```

#### Sample Input 2 - Single Element
```json
[100]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": [100],
  "executionTimeMs": 0
}
```

#### Sample Input 3 - Sales Data
```json
[1000, 1500, 2000, 500, 3000, 750]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": [1000, 2500, 4500, 5000, 8000, 8750],
  "executionTimeMs": 1
}
```

#### Sample Input 4 - With Zeros
```json
[10, 0, 5, 0, 15, 0, 20]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": [10, 10, 15, 15, 30, 30, 50],
  "executionTimeMs": 1
}
```

#### Sample Input 5 - Negative Numbers
```json
[10, -5, 15, -10, 20]
```

**Expected Output:**
```json
{
  "approach": "Stream API",
  "result": [10, 5, 20, 10, 30],
  "executionTimeMs": 1
}
```

---

## Testing Tips

### Using Swagger UI
1. Navigate to http://localhost:8080/swagger-ui.html
2. Select an endpoint from the list
3. Click "Try it out"
4. Paste JSON input in the request body field
5. Click "Execute"
6. View the response with execution times

### Using cURL
```bash
# Filter Orders
curl -X POST http://localhost:8080/api/streams/filter \
  -H "Content-Type: application/json" \
  -d '{"orderInputs":[{"id":"O1","customerId":"C1","amount":150.50,"status":"COMPLETED","items":["Item1"]}],"threshold":100}'

# Character Frequency
curl -X POST "http://localhost:8080/api/streams/character-frequency?text=Hello%20World"

# Running Totals
curl -X POST http://localhost:8080/api/practical/running-totals \
  -H "Content-Type: application/json" \
  -d '[5, 10, 3, 7, 2]'
```

### Using Postman
1. Import the OpenAPI spec from http://localhost:8080/api-docs
2. Create a new collection with all endpoints
3. Copy JSON samples from this document
4. Execute and compare with expected outputs

---

## Performance Notes
- Execution times (`executionTimeMs`) will vary based on:
  - System load
  - JVM warmup state
  - Input data size
  - Available CPU resources
- Stream API generally performs better for larger datasets
- Traditional loops may be faster for very small datasets (< 10 elements)
- Results show Stream API is more concise while maintaining comparable performance
