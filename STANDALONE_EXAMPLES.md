# Java Streams Standalone Examples (CLI)

These examples run directly as separate Java classes (standalone files) without the REST API.

## How to Run

**Compile first:**
```
mvn -q -DskipTests clean compile
```

**Run a specific standalone class:**
```
java -cp target/classes com.test.examples.FilteringExample
```

**Run via the menu runner (optional):**
```
java -cp target/classes com.test.ExamplesRunner 1
```

## Standalone File Reference

### FilteringExample (Filter Orders)
**File:** src/main/java/com/test/examples/FilteringExample.java

**Input (sample created in code):**
- Orders:
  - O1: C1, $150.50, COMPLETED
  - O2: C2, $89.99, PENDING
  - O3: C1, $200.00, COMPLETED
  - O4: C3, $45.00, CANCELLED
  - O5: C2, $320.75, COMPLETED

**Output (example):**
- Orders with status COMPLETED and amount > 100

**Steps:**
1. Stream all orders.
2. Filter to COMPLETED status.
3. Filter to amount > 100.
4. Print each matching order.

**Run:**
```
java -cp target/classes com.test.examples.FilteringExample
```

### ComplexPipelineExample (Top 2 Customers by Spending)
**File:** src/main/java/com/test/examples/ComplexPipelineExample.java

**Input (sample created in code):**
- Same 5 orders as FilteringExample

**Output (example):**
- C1: $350.50
- C2: $320.75

**Steps:**
1. Filter orders to COMPLETED.
2. Group by customerId and sum amounts.
3. Convert map to entries and sort by total descending.
4. Limit to top 2 and print.

**Run:**
```
java -cp target/classes com.test.examples.ComplexPipelineExample
```

### SortingExample (Various Sorting Scenarios)
**File:** src/main/java/com/test/examples/SortingExample.java

**Input (sample created in code):**
- Same 5 orders as FilteringExample

**Output (example):**
- Scenario 1: Amount ascending (O4, O2, O1, O3, O5)
- Scenario 2: Amount descending (O5, O3, O1, O2, O4)
- Scenario 3: Status alphabetical
- Scenario 4: Customer ID then amount
- Scenario 5: Status then customer ID
- Scenario 6: Number of items descending
- Scenario 7: Conditional sorting by status

**Steps:**
1. Create sample orders.
2. Apply a different Comparator per scenario.
3. Print the ordered results for each scenario.

**Run:**
```
java -cp target/classes com.test.examples.SortingExample
```
