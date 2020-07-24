package search.service;

import search.SearchException;
import search.entities.Person;
import search.enums.SearchStrategy;
import search.repositories.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SearchService {
    public static Repository<Person> search(Repository<Person> personRepository,
                                            String searchWord) throws SearchException {
        final String word = searchWord.toLowerCase();
        Repository<Person> resultRepository = new Repository<>();
        Predicate<Person> predicate = person ->
                person.getFirstName().toLowerCase().contains(word) ||
                        person.getLastName().toLowerCase().contains(word) ||
                        person.getEmail().toLowerCase().contains(word);
        List<Person> persons = personRepository.getAll()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
        if (persons.isEmpty()) {
            throw new SearchException();
        }
        resultRepository.addAll(persons);
        return resultRepository;
    }

    public static Repository<Person> invertedIndexSearch(Repository<Person> personRepository,
                                                         String searchWords,
                                                         SearchStrategy searchStrategy) throws SearchException {
        String[] words = searchWords.split("\\s+");
        List<Set<Integer>> list = new ArrayList<>(words.length);
        for (String word : words) {
            try {
                list.add(Person.getIndexes(word));
            } catch (SearchException ignored) {

            }
        }
        if (list.isEmpty()) {
            throw new SearchException();
        }
        Set<Integer> indexes = new HashSet<>();
        switch (searchStrategy) {
            case ALL:
                indexes = allStrategy(list);
                break;
            case ANY:
                indexes = anyStrategy(list);
                break;
            case NONE:
                indexes = noneStrategy(list, personRepository.size());
                break;
            default:
        }

        return personRepository.getByIndexes(indexes);
    }

    private static Set<Integer> allStrategy(List<Set<Integer>> list) {
        Set<Integer> indexes = list.get(0);
        for (Set<Integer> set : list) {
            indexes.retainAll(set);
        }
        return indexes;
    }

    private static Set<Integer> anyStrategy(List<Set<Integer>> list) {
        Set<Integer> indexes = new HashSet<>();
        for (Set<Integer> set : list) {
            indexes.addAll(set);
        }
        return indexes;
    }

    private static Set<Integer> noneStrategy(List<Set<Integer>> list, int max) {
        Set<Integer> indexes = new HashSet<>();
        for (int i = 0; i < max; i++) {
            indexes.add(i);
        }
        for (Set<Integer> set : list) {
            indexes.removeAll(set);
        }
        return indexes;
    }
}
