package com.test.examples;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class MultiFieldSortExample {

    private MultiFieldSortExample() {
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Multi Field Sort Example - Run Independently");
        System.out.println("========================================");
        
        // Run the multi-field sort example
        run();
    }

    // Practical Use 6: Multi-field sorting
    public static void run() {
        // Input: people = [Mina(31), Alex(29), Alex(35), Zoe(25)]
        // Output: Prints people sorted by name then age
        // Expected Output:
        //   People: [Mina(31), Alex(29), Alex(35), Zoe(25)]
        //   Sorted: [Alex(29), Alex(35), Mina(31), Zoe(25)]
        System.out.println("\n--- Practical Use: Multi-field Sort ---");
        List<Person> people = Arrays.asList(
            new Person("Mina", 31),
            new Person("Alex", 29),
            new Person("Alex", 35),
            new Person("Zoe", 25)
        );

        // stream() - creates stream from list
        // sorted() - sorts using Comparator
        //   comparing() - creates comparator for first sort field (name)
        //   thenComparing() - adds secondary sort field (age)
        //   Sorts by name first, then by age for equal names
        // collect() - gathers sorted elements into list
        List<Person> sorted = people.stream()
            .sorted(Comparator.comparing(Person::name).thenComparing(Person::age))  // multi-field sort
            .collect(Collectors.toList());

        System.out.println("People: " + people);
        System.out.println("Sorted: " + sorted);
    }

    private record Person(String name, int age) {
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
}
