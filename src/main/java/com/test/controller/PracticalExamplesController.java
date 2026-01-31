package com.test.controller;

import com.test.dto.StreamResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/practical")
@Tag(name = "Practical Examples", description = "Real-world practical stream use cases")
public class PracticalExamplesController {

    @PostMapping("/normalize-sort")
    @Operation(
        summary = "Normalize, deduplicate, and sort names",
        description = "Trims whitespace, converts to lowercase, removes duplicates, and sorts names alphabetically."
    )
    public StreamResult normalizeSortNames(
        @Parameter(description = "List of names with mixed case/spacing", example = "[\" Alice \", \"bob\", \"ALICE\"]", required = true)
        @RequestBody List<String> names
    ) {
        long start = System.nanoTime();
        Set<String> normalized = names.stream()
            .map(name -> name.trim().toLowerCase(Locale.ROOT))
            .filter(name -> !name.isBlank())
            .collect(Collectors.toCollection(TreeSet::new));
        long time = (System.nanoTime() - start) / 1_000_000;
        
        return new StreamResult("Stream API", normalized, time);
    }

    @PostMapping("/word-frequency")
    @Operation(
        summary = "Calculate top N word frequencies",
        description = "Splits text into words, counts occurrences, and returns top N most frequent words."
    )
    public StreamResult wordFrequency(
        @Parameter(description = "Input text", example = "Java streams make data processing with streams concise", required = true)
        @RequestParam String text,
        @Parameter(description = "Number of top words to return", example = "3")
        @RequestParam(defaultValue = "3") int topN
    ) {
        long start = System.nanoTime();
        Map<String, Long> topWords = Arrays.stream(text.toLowerCase(Locale.ROOT).split("\\W+"))
            .filter(word -> !word.isBlank())
            .collect(Collectors.groupingBy(word -> word, Collectors.counting()))
            .entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(topN)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a, b) -> a,
                LinkedHashMap::new
            ));
        long time = (System.nanoTime() - start) / 1_000_000;
        
        return new StreamResult("Stream API", topWords, time);
    }

    @PostMapping("/validate-ids")
    @Operation(
        summary = "Validate IDs and collect invalid ones",
        description = "Validates IDs against pattern [A-Z]-\\d+ and returns invalid entries."
    )
    public StreamResult validateIds(
        @Parameter(description = "List of IDs to validate", example = "[\"A-100\", \"\", \"INVALID#\"]", required = true)
        @RequestBody List<String> ids
    ) {
        long start = System.nanoTime();
        List<String> invalidIds = ids.stream()
            .filter(id -> id == null || id.isBlank() || !id.matches("[A-Z]-\\d+"))
            .collect(Collectors.toList());
        long time = (System.nanoTime() - start) / 1_000_000;
        
        return new StreamResult("Stream API", invalidIds, time);
    }

    @PostMapping("/partition-scores")
    @Operation(
        summary = "Partition scores into pass/fail",
        description = "Divides scores into two groups based on threshold (default 60)."
    )
    public StreamResult partitionScores(
        @Parameter(description = "List of scores", example = "[95, 82, 67, 49, 73, 58]", required = true)
        @RequestBody List<Integer> scores,
        @Parameter(description = "Pass threshold", example = "60")
        @RequestParam(defaultValue = "60") int threshold
    ) {
        long start = System.nanoTime();
        Map<Boolean, List<Integer>> passFail = scores.stream()
            .collect(Collectors.partitioningBy(score -> score >= threshold));
        
        Map<String, List<Integer>> result = new LinkedHashMap<>();
        result.put("Pass", passFail.get(true));
        result.put("Fail", passFail.get(false));
        
        long time = (System.nanoTime() - start) / 1_000_000;
        
        return new StreamResult("Stream API", result, time);
    }

    @PostMapping("/running-totals")
    @Operation(
        summary = "Calculate running totals",
        description = "Computes cumulative sum at each position in the list."
    )
    public StreamResult runningTotals(
        @Parameter(description = "List of amounts", example = "[5, 10, 3, 7, 2]", required = true)
        @RequestBody List<Integer> amounts
    ) {
        long start = System.nanoTime();
        List<Integer> runningTotals = IntStream.range(0, amounts.size())
            .mapToObj(i -> amounts.subList(0, i + 1).stream().mapToInt(Integer::intValue).sum())
            .collect(Collectors.toList());
        long time = (System.nanoTime() - start) / 1_000_000;
        
        return new StreamResult("Stream API", runningTotals, time);
    }
}
