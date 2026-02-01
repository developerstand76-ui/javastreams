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
        String name = "Haritha Reddy";
        System.out.println("Input: \"" + name + "\"");
        
        Map<Character, Long> charCountStream = name.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                Function.identity(),
                LinkedHashMap::new,
                Collectors.counting()
            ));
        System.out.println(charCountStream);
    }
}
