package search;

import search.entities.Person;
import search.enums.MenuOption;
import search.enums.SearchStrategy;
import search.repositories.Repository;
import search.service.FileService;
import search.service.SearchService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Console {
    private final Scanner scanner = new Scanner(System.in);
    private final Repository<Person> personRepository = new Repository<>();

    private void printMenu() {
        System.out.println();
        System.out.println("=== Menu ===");
        Arrays.stream(MenuOption.values()).forEach(System.out::println);
    }

    private boolean userInput() {
        int number = Integer.parseInt(scanner.nextLine().trim());
        MenuOption menuOption = MenuOption.get(number);
        switch (menuOption) {
            case SEARCH:
                search();
                break;
            case PRINT:
                System.out.println();
                System.out.println("=== List of people ===");
                System.out.println(personRepository);
                break;
            case EXIT:
                System.out.println("Bye!");
                return false;
            default:
        }
        return true;
    }

    public void execute() {
        boolean run = true;
        do {
            printMenu();
            try {
                run = userInput();
            } catch (SearchException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (run);
    }

    private void search() throws SearchException, IllegalArgumentException {
        System.out.print("Select a matching strategy: ");
        Arrays.stream(SearchStrategy.values())
                .forEach(searchStrategy -> System.out.print(searchStrategy + " "));
        System.out.println();
        SearchStrategy searchStrategy = SearchStrategy.valueOf(scanner.nextLine());
        System.out.println("Enter a name or email to search all suitable people.");
        String searchWord = scanner.nextLine();
        System.out.println();
        Repository<Person> repo = SearchService.invertedIndexSearch(personRepository, searchWord, searchStrategy);
        System.out.println(repo.size() + " persons found:");
        System.out.println(repo);
    }

    public void fillPeople() {
        System.out.println("Enter the number of people:");
        int numberOfPeople = Integer.parseInt(scanner.nextLine().trim());
        for (int i = 0; i < numberOfPeople; i++) {
            personRepository.add(Person.create(scanner.nextLine(), i));
        }
    }

    public void fillPeople(String filepath) {
        List<String> lines = FileService.readLines(filepath);
        for (int i = 0; i < lines.size(); i++) {
            personRepository.add(Person.create(lines.get(i), i));
        }
    }


}
