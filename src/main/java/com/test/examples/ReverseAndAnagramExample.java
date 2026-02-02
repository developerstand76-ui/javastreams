package com.test.examples;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        
        // Using simple for loop
        String numStr = String.valueOf(number);
        String reversedNumStr = "";
        for (int i = numStr.length() - 1; i >= 0; i--) {
            reversedNumStr += numStr.charAt(i);
        }
        int reversedLoop = Integer.parseInt(reversedNumStr);
        System.out.println("Reversed (for loop): " + reversedLoop);
        
        // Example 2: Reverse a String
        System.out.println("\n--- Example 2: REVERSE STRING ---");
        String text = "Hello World";
        System.out.println("Original string: " + text);
        
        // Method 1: Using StringBuilder
        String reversedStr = new StringBuilder(text).reverse().toString();
        System.out.println("Reversed (StringBuilder): " + reversedStr);
        
        // Method 2: Using for loop
        String reversedLoop2 = "";
        for (int i = text.length() - 1; i >= 0; i--) {
            reversedLoop2 += text.charAt(i);
        }
        System.out.println("Reversed (for loop): " + reversedLoop2);
        
        // Method 3: Using char array
        char[] chars = text.toCharArray();
        String reversedArray = "";
        for (int i = chars.length - 1; i >= 0; i--) {
            reversedArray += chars[i];
        }
        System.out.println("Reversed (char array): " + reversedArray);
        
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
        
        // Generate all substrings and count anagrams using simple loop
        int targetLength = target.length();
        int anagramCount = 0;
        
        for (int i = 0; i <= source.length() - targetLength; i++) {
            String substring = source.substring(i, i + targetLength);
            String sortedSubstring = sortString(substring);
            System.out.println("  Checking: " + substring + " -> sorted: " + sortedSubstring);
            
            if (sortedSubstring.equals(sortedTarget)) {
                anagramCount++;
            }
        }
        
        System.out.println("\nTotal anagram occurrences: " + anagramCount);
        
        // Show all matches with positions using simple loop
        System.out.println("\nDetailed matches:");
        for (int i = 0; i <= source.length() - targetLength; i++) {
            String substring = source.substring(i, i + targetLength);
            if (sortString(substring).equals(sortedTarget)) {
                System.out.println("  Position " + i + ": '" + substring + "' is an anagram of '" + target + "'");
            }
        }
        
        // Example 4: All Unique Anagrams using simple loop
        System.out.println("\n--- Example 4: ALL UNIQUE ANAGRAMS ---");
        Set<String> uniqueAnagrams = new HashSet<>();
        
        for (int i = 0; i <= source.length() - targetLength; i++) {
            String substring = source.substring(i, i + targetLength);
            if (sortString(substring).equals(sortedTarget)) {
                uniqueAnagrams.add(substring);
            }
        }
        
        System.out.println("Unique anagrams found: " + new ArrayList<>(uniqueAnagrams));
        
        // Example 5: Complex Example - Find longest palindrome in reversed string
        System.out.println("\n--- Example 5: BONUS - Palindromes in Reversed Text ---");
        String original = "abcba";
        String reversedText = new StringBuilder(original).reverse().toString();
        System.out.println("Original: " + original);
        System.out.println("Reversed: " + reversedText);
        System.out.println("Is palindrome? " + original.equals(reversedText));
    }
    
    // Helper method to sort characters in a string using simple approach
    private static String sortString(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
