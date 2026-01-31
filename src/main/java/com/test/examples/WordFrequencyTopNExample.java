package com.test.examples;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public final class WordFrequencyTopNExample {

    private WordFrequencyTopNExample() {
    }

    // Practical Use 2: Word frequency with top-N results
    public static void run() {
        // Input: text string
        // Output: Prints top 3 word frequencies
        System.out.println("\n--- Practical Use: Word Frequency (Top 3) ---");
        String text = "Java streams make data processing with streams concise and readable";

        Map<String, Long> topWords = Arrays.stream(text.toLowerCase(Locale.ROOT).split("\\W+"))
            .filter(word -> !word.isBlank())
            .collect(Collectors.groupingBy(word -> word, Collectors.counting()))
            .entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(3)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a, b) -> a,
                LinkedHashMap::new
            ));

        System.out.println("Text: " + text);
        System.out.println("Top words: " + topWords);
    }
}
