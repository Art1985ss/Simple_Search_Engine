package search;

public class SearchException extends RuntimeException {
    public SearchException(String message) {
        super(message);
    }

    public SearchException() {
        this("No matching people found.");
    }
}
