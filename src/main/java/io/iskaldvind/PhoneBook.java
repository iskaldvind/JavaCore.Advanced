package io.iskaldvind;

import java.util.HashMap;
import java.util.HashSet;

public class PhoneBook {

    private final HashMap<String, HashSet<String>> BOOK = new HashMap<>();

    public void add(String lastName, String phone) {
        if (!BOOK.containsKey(lastName)) {
            BOOK.put(lastName, new HashSet<>());
        }
        BOOK.get(lastName).add(phone);
    }

    public void get(String lastName) {
        if (BOOK.containsKey(lastName)) {
            StringBuilder phones = new StringBuilder();
            for (String phone: BOOK.get(lastName)) {
                if (phones.length() != 0) phones.append(", ");
                phones.append(phone);
            }
            System.out.println(lastName + ": " + phones.toString());
        } else {
            System.out.println(lastName + ": No phone numbers found");
        }
    }
}
