package file;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * The DocumentWriter class allows for the writing to a file.
 * @author Matthias Amend
 */
public class DocumentWriter {

    /**
     * Write the content string to the file.
     * @param path The path of the file
     * @param content The content string that is to be written to the specified file
     */
    public static void writeToFile(Path path, String content) {
        try {
            FileWriter writer = new FileWriter(path.toFile());
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to File: " + e);
        }

    }

}
