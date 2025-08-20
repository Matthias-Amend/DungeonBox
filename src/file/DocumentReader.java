package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The DocumentReader class allows for the reading of file contents.
 * @author Matthias Amend
 */
public class DocumentReader {

    /**
     * Read the contents of a specific file and return them as a single string.
     * If the File could not be read, the readFile function returns null
     * @param path The path of the file that is to be read
     * @return The content of the file as a String
     */
    public static String readFile(Path path) {
        try {
            return String.join("\n", Files.readAllLines(path));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return null;
    }



}
