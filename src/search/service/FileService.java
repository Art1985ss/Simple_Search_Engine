package search.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileService {

    public static List<String> readLines(String filepath) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
