package com.test.examples;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class MultiFieldSortExample {

    private MultiFieldSortExample() {
    }

    // Practical Use 6: Multi-field sorting
    public static void run() {
        // Input: list of Person(name, age)
        // Output: Prints people sorted by name then age
        System.out.println("\n--- Practical Use: Multi-field Sort ---");
        List<Person> people = Arrays.asList(
            new Person("Mina", 31),
            new Person("Alex", 29),
            new Person("Alex", 35),
            new Person("Zoe", 25)
        );

        List<Person> sorted = people.stream()
            .sorted(Comparator.comparing(Person::name).thenComparing(Person::age))
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
