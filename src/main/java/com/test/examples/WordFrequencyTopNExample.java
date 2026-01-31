package com.test.examples;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public final class WordFrequencyTopNExample {

    private WordFrequencyTopNExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Word Frequency Top N Example - Run Independently");
        System.out.println("========================================");
        
        // Run the word frequency example
        run();
    }

    // Practical Use 2: Word frequency with top-N results
    public static void run() {
        // Input: text = "Java streams make data processing with streams concise and readable"
        // Output: Prints top 3 word frequencies
        // Expected Output:
        //   Top words: {streams=2, readable=1, with=1}
        System.out.println("\n--- Practical Use: Word Frequency (Top 3) ---");
        String text = "Java streams make data processing with streams concise and readable";

        // Multi-stage pipeline:
        // Arrays.stream() - creates stream from array of words
        // filter() - removes blank words
        // groupingBy() - groups identical words and counts them
        // entrySet().stream() - creates new stream from map entries
        // sorted() - sorts by count (descending)
        // limit() - keeps top N entries
        // toMap() - collects back to LinkedHashMap (preserves order)
        Map<String, Long> topWords = Arrays.stream(text.toLowerCase(Locale.ROOT).split("\\W+"))
            .filter(word -> !word.isBlank())                      // remove empty strings
            .collect(Collectors.groupingBy(word -> word, Collectors.counting()))  // count words
            .entrySet()                                           // get entries
            .stream()                                             // new stream
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())  // sort by count desc
            .limit(3)                                             // top 3
            .collect(Collectors.toMap(                            // collect to map
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a, b) -> a,
                LinkedHashMap::new                                // preserve order
            ));

        System.out.println("Text: " + text);
        System.out.println("Top words: " + topWords);
    }
}
