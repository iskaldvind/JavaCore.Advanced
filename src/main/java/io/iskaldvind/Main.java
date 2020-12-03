package io.iskaldvind;

import java.util.HashMap;
import java.util.HashSet;

public class Main {

    final static String[] words = {
            "elephant", "dog", "apple", "house", "ship", "coffee",
            "dog", "book", "dog", "coffee", "hat", "dog", "sugar",
            "rocket", "apple", "java", "salt", "room", "cloud"
    };

    public static void main(String[] args) {
        HashSet<String> uniqueWords = new HashSet<>();
        HashMap<String, Integer> repeatedWords = new HashMap<>();
        for (String word: words) {
            if (uniqueWords.contains(word)) {
                if (repeatedWords.containsKey(word)) {
                    repeatedWords.put(word, repeatedWords.get(word) + 1);
                } else {
                    repeatedWords.put(word, 2);
                }
            }
            uniqueWords.add(word);
        }

        System.out.println("------\nUnique words:\n-----");
        StringBuilder unique = new StringBuilder();
        for (String uniqueWord : uniqueWords) {
            if (unique.length() != 0) unique.append(", ");
            unique.append(uniqueWord);
        }
        System.out.println(unique.toString());

        System.out.println("------\nRepeated words:\n-----");
        for (String key : repeatedWords.keySet()) {
            System.out.println(key + " repeates " + repeatedWords.get(key) + " times");
        }

        System.out.println("------\nPhone book:\n-----");
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Ivanov", "+79261112233");
        phoneBook.add("Petrov", "+79264445566");
        phoneBook.add("Petrov", "+79267778899");
        phoneBook.get("Sidorov");
        phoneBook.get("Ivanov");
        phoneBook.get("Petrov");
    }
}
