package com.test;

import com.test.examples.AggregationExample;
import com.test.examples.CharacterFrequencyExample;
import com.test.examples.ComplexPipelineExample;
import com.test.examples.FilteringExample;
import com.test.examples.GroupingExample;
import com.test.examples.MappingExample;
import com.test.examples.InvalidRecordCollectionExample;
import com.test.examples.MultiFieldSortExample;
import com.test.examples.NormalizeDeduplicateSortExample;
import com.test.examples.PartitionPassFailExample;
import com.test.examples.RunningTotalsExample;
import com.test.examples.WordFrequencyTopNExample;
import com.test.examples.ReduceOperationsExample;
import com.test.model.Order;
import java.util.Arrays;
import java.util.List;

public class StreamTest {

    public static void main(String[] args) {
        System.out.println("=== STREAMS vs TRADITIONAL APPROACHES ===\n");

        // Sample data
        List<Order> orders = Arrays.asList(
            new Order("O1", "C1", 150.50, "COMPLETED", Arrays.asList("Item1", "Item2")),
            new Order("O2", "C2", 89.99, "PENDING", Arrays.asList("Item3")),
            new Order("O3", "C1", 200.00, "COMPLETED", Arrays.asList("Item4", "Item5", "Item6")),
            new Order("O4", "C3", 45.00, "CANCELLED", Arrays.asList("Item7")),
            new Order("O5", "C2", 320.75, "COMPLETED", Arrays.asList("Item8", "Item9"))
        );

        // Example 1: Filtering
        FilteringExample.run(orders);

        // Example 2: Mapping/Transformation
        MappingExample.run(orders);

        // Example 3: Aggregation
        AggregationExample.run(orders);

        // Example 4: Grouping
        GroupingExample.run(orders);

        // Example 5: Complex Pipeline
        ComplexPipelineExample.run(orders);

        // Example 6: Character Frequency (Original example)
        CharacterFrequencyExample.run();

        // Example 7: reduce() operations
        ReduceOperationsExample.run();

        // Example 8: Practical uses (one function per class)
        NormalizeDeduplicateSortExample.run();
        WordFrequencyTopNExample.run();
        InvalidRecordCollectionExample.run();
        PartitionPassFailExample.run();
        RunningTotalsExample.run();
        MultiFieldSortExample.run();
    }
}
