package search.entities;

import search.SearchException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private static Map<String, Set<Integer>> map = new HashMap<>();

    public Person(String firstName) {
        this.firstName = firstName;
        this.lastName = "";
        this.email = "";
    }

    public Person(String firstName, String lastName) {
        this(firstName);
        if (lastName != null) {
            this.lastName = lastName;
        }
    }


    public Person(String firstName, String lastName, String email) {
        this(firstName, lastName);
        if (email != null) {
            this.email = email;
        }
    }

    private static void updateMap(String str, int num) {
        String key = str.toLowerCase();
        if (map.containsKey(key)) {
            Set<Integer> temp = map.get(key);
            temp.add(num);
            map.replace(key, temp);
        } else {
            Set<Integer> temp = new HashSet<>();
            temp.add(num);
            map.put(key, temp);
        }
    }

    public static Set<Integer> getIndexes(String str) throws SearchException {
        String key = str.toLowerCase();
        if (!map.containsKey(key)) {
            throw new SearchException();
        }
        return map.get(key);
    }

    public static Person create(String entry, int num) {
        String[] input = entry.split("\\s+");
        Person person = null;
        switch (input.length) {
            case 1:
                person = new Person(input[0]);
                updateMap(input[0], num);
                break;
            case 2:
                person = new Person(input[0], input[1]);
                updateMap(input[0], num);
                updateMap(input[1], num);
                break;
            case 3:
                person = new Person(input[0], input[1], input[2]);
                updateMap(input[0], num);
                updateMap(input[1], num);
                updateMap(input[2], num);
                break;
            default:
        }
        return person;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        String text = firstName;
        if (!lastName.equals("")) {
            text += " " + lastName;
        }
        if (!email.equals("")) {
            text += " " + email;
        }
        return text;
    }
}
