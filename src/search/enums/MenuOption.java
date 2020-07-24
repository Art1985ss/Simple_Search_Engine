package search.enums;

import search.SearchException;

import java.util.Arrays;

public enum MenuOption {
    SEARCH(1, "Search information"),
    PRINT(2, "Print all data"),
    EXIT(0, "Exit");

    private final int id;
    private final String text;

    MenuOption(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public static MenuOption get(int id) throws SearchException {
        return Arrays.stream(values()).filter(e -> e.id == id)
                .findFirst().orElseThrow(() -> new SearchException("Incorrect option! Try again."));
    }

    @Override
    public String toString() {
        return id + ". " + text;
    }
}
