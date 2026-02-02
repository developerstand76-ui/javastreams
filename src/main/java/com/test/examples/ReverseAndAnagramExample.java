package com.test.examples;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ReverseAndAnagramExample {

    private ReverseAndAnagramExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Reverse & Anagram Example - Run Independently");
        System.out.println("========================================");
        
        run();
    }

    public static void run() {
        
        // Example 1: Reverse an Integer
        System.out.println("\n--- Example 1: REVERSE INTEGER ---");
        int number = 345345;
        System.out.println("Original number: " + number);
        
        // Convert to string, reverse, convert back to int
        int reversed = Integer.parseInt(
            new StringBuilder(String.valueOf(number))
                .reverse()
                .toString()
        );
        System.out.println("Reversed number: " + reversed);
        
        // Using streams (digit by digit)
        String numStr = String.valueOf(number);
        int reversedStream = Integer.parseInt(
            IntStream.range(0, numStr.length())
                .map(i -> numStr.charAt(numStr.length() - 1 - i))
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining())
        );
        System.out.println("Reversed (stream): " + reversedStream);
        
        // Example 2: Reverse a String
        System.out.println("\n--- Example 2: REVERSE STRING ---");
        String text = "Hello World";
        System.out.println("Original string: " + text);
        
        // Method 1: Using StringBuilder
        String reversedStr = new StringBuilder(text).reverse().toString();
        System.out.println("Reversed (StringBuilder): " + reversedStr);
        
        // Method 2: Using streams
        String reversedStream2 = IntStream.range(0, text.length())
            .map(i -> text.charAt(text.length() - 1 - i))
            .mapToObj(c -> String.valueOf((char) c))
            .collect(Collectors.joining());
        System.out.println("Reversed (stream): " + reversedStream2);
        
        // Method 3: Using streams with boxed
        String reversedStream3 = text.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    java.util.Collections.reverse(list);
                    return list.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining());
                }
            ));
        System.out.println("Reversed (stream collect): " + reversedStream3);
        
        // Example 3: Find Anagram Occurrences
        System.out.println("\n--- Example 3: FIND ANAGRAM OCCURRENCES ---");
        String target = "abc";
        String source = "abcbacdcba";
        System.out.println("Target: " + target);
        System.out.println("Source: " + source);
        System.out.println("Looking for anagrams of '" + target + "' in '" + source + "'");
        
        // Get sorted version of target for comparison
        String sortedTarget = sortString(target);
        System.out.println("Sorted target: " + sortedTarget);
        
        // Generate all substrings of length equal to target
        int targetLength = target.length();
        long anagramCount = IntStream.rangeClosed(0, source.length() - targetLength)
            .mapToObj(i -> source.substring(i, i + targetLength))
            .peek(substring -> System.out.println("  Checking: " + substring + " -> sorted: " + sortString(substring)))
            .filter(substring -> sortString(substring).equals(sortedTarget))
            .count();
        
        System.out.println("\nTotal anagram occurrences: " + anagramCount);
        
        // Show all matches with positions
        System.out.println("\nDetailed matches:");
        IntStream.rangeClosed(0, source.length() - targetLength)
            .filter(i -> {
                String substring = source.substring(i, i + targetLength);
                return sortString(substring).equals(sortedTarget);
            })
            .forEach(i -> {
                String match = source.substring(i, i + targetLength);
                System.out.println("  Position " + i + ": '" + match + "' is an anagram of '" + target + "'");
            });
        
        // Example 4: All Unique Anagrams
        System.out.println("\n--- Example 4: ALL UNIQUE ANAGRAMS ---");
        var uniqueAnagrams = IntStream.rangeClosed(0, source.length() - targetLength)
            .mapToObj(i -> source.substring(i, i + targetLength))
            .filter(substring -> sortString(substring).equals(sortedTarget))
            .distinct()
            .collect(Collectors.toList());
        
        System.out.println("Unique anagrams found: " + uniqueAnagrams);
        
        // Example 5: Complex Example - Find longest palindrome in reversed string
        System.out.println("\n--- Example 5: BONUS - Palindromes in Reversed Text ---");
        String original = "abcba";
        String reversedText = new StringBuilder(original).reverse().toString();
        System.out.println("Original: " + original);
        System.out.println("Reversed: " + reversedText);
        System.out.println("Is palindrome? " + original.equals(reversedText));
    }
    
    // Helper method to sort characters in a string
    private static String sortString(String str) {
        return str.chars()
            .sorted()
            .mapToObj(c -> String.valueOf((char) c))
            .collect(Collectors.joining());
    }
}
