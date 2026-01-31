package com.test.examples;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CharacterFrequencyExample {

    private CharacterFrequencyExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Character Frequency Example - Run Independently");
        System.out.println("========================================");
        
        // Run the character frequency example
        run();
    }

    // EXAMPLE 6: Character Frequency (Original example)
    public static void run() {
        // Input: String name = "Haritha Reddy"
        // Output: Prints character frequency maps (including letters-only variant)
        // Expected Output:
        //   {H=1, a=2, r=1, i=1, t=1, h=1,  =1, R=1, e=1, d=2, y=1}
        //   Letters only: {h=2, a=2, r=2, i=1, t=1, e=1, d=2, y=1}
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
        // chars() - converts String to IntStream of character codes
        // mapToObj() - converts int to Object type (char)
        // groupingBy() - groups characters
        //   Function.identity() - uses the element itself as key (c -> c)
        //   LinkedHashMap::new - preserves insertion order
        //   counting() - counts occurrences of each character
        Map<Character, Long> charCountStream = name.chars()       // IntStream of char codes
            .mapToObj(c -> (char) c)                              // convert to Character
            .collect(Collectors.groupingBy(
                Function.identity(),                              // group by character itself
                LinkedHashMap::new,                               // use LinkedHashMap
                Collectors.counting()                             // count occurrences
            ));
        System.out.println(charCountStream);

        // BONUS: Only letters (no spaces), case-insensitive
        System.out.println("\nStream (letters only, case-insensitive):");
        // filter() - keeps only letter characters (removes spaces, punctuation)
        // Character::isLetter - method reference for filtering
        Map<Character, Long> letterCount = name.toLowerCase()
            .chars()
            .filter(Character::isLetter)                          // keep only letters
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                Function.identity(),
                LinkedHashMap::new,
                Collectors.counting()
            ));
        System.out.println(letterCount);
    }
}
