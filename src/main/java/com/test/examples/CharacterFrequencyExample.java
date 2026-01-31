package com.test.examples;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CharacterFrequencyExample {

    private CharacterFrequencyExample() {
    }

    // EXAMPLE 6: Character Frequency (Original example)
    public static void run() {
        // Input: String name = "Haritha Reddy"
        // Output: Prints character frequency maps (including letters-only variant)
        System.out.println("\n--- Example 6: CHARACTER FREQUENCY ---");
        String name = "Haritha Reddy";

        // TRADITIONAL APPROACH
        System.out.println("\nTraditional Loop:");
        System.out.println("Input: \"" + name + "\"");
        Map<Character, Integer> charCountTraditional = new LinkedHashMap<>();
        for (char c : name.toCharArray()) {
            charCountTraditional.put(c, charCountTraditional.getOrDefault(c, 0) + 1);
        }
        System.out.println(charCountTraditional);

        // STREAM APPROACH
        System.out.println("\nStream API:");
        Map<Character, Long> charCountStream = name.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                Function.identity(),
                LinkedHashMap::new,
                Collectors.counting()
            ));
        System.out.println(charCountStream);

        // BONUS: Only letters (no spaces), case-insensitive
        System.out.println("\nStream (letters only, case-insensitive):");
        Map<Character, Long> letterCount = name.toLowerCase()
            .chars()
            .filter(Character::isLetter)
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                Function.identity(),
                LinkedHashMap::new,
                Collectors.counting()
            ));
        System.out.println(letterCount);
    }
}
