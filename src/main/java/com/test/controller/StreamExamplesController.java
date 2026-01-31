package com.test.controller;

import com.test.dto.OrderInput;
import com.test.dto.StreamResult;
import com.test.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/streams")
@Tag(name = "Stream Examples", description = "Java Stream API examples with traditional vs stream comparisons")
public class StreamExamplesController {

    @PostMapping("/filter")
    @Operation(
        summary = "Filter orders (COMPLETED & amount > threshold)",
        description = "Filters orders by status COMPLETED and amount greater than threshold. Shows both traditional loop and stream approach."
    )
    public List<StreamResult> filterOrders(
        @Parameter(description = "List of orders to filter", required = true)
        @RequestBody List<OrderInput> orderInputs,
        @Parameter(description = "Minimum amount threshold", example = "100")
        @RequestParam(defaultValue = "100") double threshold
    ) {
        List<Order> orders = convertToOrders(orderInputs);
        List<StreamResult> results = new ArrayList<>();

        // Traditional approach
        long start = System.nanoTime();
        List<Order> filteredTraditional = new ArrayList<>();
        for (Order order : orders) {
            if ("COMPLETED".equals(order.getStatus()) && order.getAmount() > threshold) {
                filteredTraditional.add(order);
            }
        }
        long traditionalTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Traditional Loop", filteredTraditional, traditionalTime));

        // Stream approach
        start = System.nanoTime();
        List<Order> filteredStream = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))
            .filter(order -> order.getAmount() > threshold)
            .collect(Collectors.toList());
        long streamTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API", filteredStream, streamTime));

        return results;
    }

    @PostMapping("/map")
    @Operation(
        summary = "Extract unique customer IDs",
        description = "Extracts and deduplicates customer IDs from orders. Shows both traditional loop and stream approach."
    )
    public List<StreamResult> extractCustomerIds(
        @Parameter(description = "List of orders", required = true)
        @RequestBody List<OrderInput> orderInputs
    ) {
        List<Order> orders = convertToOrders(orderInputs);
        List<StreamResult> results = new ArrayList<>();

        // Traditional approach
        long start = System.nanoTime();
        Set<String> customerIdsTraditional = new HashSet<>();
        for (Order order : orders) {
            customerIdsTraditional.add(order.getCustomerId());
        }
        long traditionalTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Traditional Loop", customerIdsTraditional, traditionalTime));

        // Stream approach
        start = System.nanoTime();
        Set<String> customerIdsStream = orders.stream()
            .map(Order::getCustomerId)
            .collect(Collectors.toSet());
        long streamTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API", customerIdsStream, streamTime));

        return results;
    }

    @PostMapping("/aggregate")
    @Operation(
        summary = "Calculate total revenue from completed orders",
        description = "Sums amounts of all COMPLETED orders. Shows traditional loop, stream sum, and stream reduce approaches."
    )
    public List<StreamResult> aggregateRevenue(
        @Parameter(description = "List of orders", required = true)
        @RequestBody List<OrderInput> orderInputs
    ) {
        List<Order> orders = convertToOrders(orderInputs);
        List<StreamResult> results = new ArrayList<>();

        // Traditional approach
        long start = System.nanoTime();
        double totalTraditional = 0.0;
        for (Order order : orders) {
            if ("COMPLETED".equals(order.getStatus())) {
                totalTraditional += order.getAmount();
            }
        }
        long traditionalTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Traditional Loop", String.format("$%.2f", totalTraditional), traditionalTime));

        // Stream approach
        start = System.nanoTime();
        double totalStream = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))
            .mapToDouble(Order::getAmount)
            .sum();
        long streamTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API (sum)", String.format("$%.2f", totalStream), streamTime));

        // Stream with reduce
        start = System.nanoTime();
        double totalReduce = orders.stream()
            .filter(order -> "COMPLETED".equals(order.getStatus()))
            .map(Order::getAmount)
            .reduce(0.0, Double::sum);
        long reduceTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API (reduce)", String.format("$%.2f", totalReduce), reduceTime));

        return results;
    }

    @PostMapping("/group")
    @Operation(
        summary = "Group orders by status",
        description = "Groups orders by their status field and counts each group. Shows traditional and stream approaches."
    )
    public List<StreamResult> groupOrders(
        @Parameter(description = "List of orders", required = true)
        @RequestBody List<OrderInput> orderInputs
    ) {
        List<Order> orders = convertToOrders(orderInputs);
        List<StreamResult> results = new ArrayList<>();

        // Stream approach
        long start = System.nanoTime();
        var grouped = orders.stream()
            .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
        long streamTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API", grouped, streamTime));

        return results;
    }

    @PostMapping("/character-frequency")
    @Operation(
        summary = "Calculate character frequency in text",
        description = "Counts occurrences of each character in the input text. Shows traditional and stream approaches."
    )
    public List<StreamResult> characterFrequency(
        @Parameter(description = "Input text", example = "Haritha Reddy", required = true)
        @RequestParam String text
    ) {
        List<StreamResult> results = new ArrayList<>();

        // Stream approach
        long start = System.nanoTime();
        var charCount = text.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                c -> c,
                java.util.LinkedHashMap::new,
                Collectors.counting()
            ));
        long streamTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API", charCount, streamTime));

        // Letters only
        start = System.nanoTime();
        var letterCount = text.toLowerCase()
            .chars()
            .filter(Character::isLetter)
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                c -> c,
                java.util.LinkedHashMap::new,
                Collectors.counting()
            ));
        long letterTime = (System.nanoTime() - start) / 1_000_000;
        results.add(new StreamResult("Stream API (letters only)", letterCount, letterTime));

        return results;
    }

    private List<Order> convertToOrders(List<OrderInput> inputs) {
        return inputs.stream()
            .map(i -> new Order(i.getId(), i.getCustomerId(), i.getAmount(), i.getStatus(), i.getItems()))
            .collect(Collectors.toList());
    }
}
