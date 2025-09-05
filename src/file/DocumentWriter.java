package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The DocumentWriter class allows for the writing to a file.
 * @author Matthias Amend
 */
public class DocumentWriter {

    /**
     * Write the content string to the file.
     * @param file The file to write to
     * @param content The content string that is to be written to the specified file
     * @return True if the writing operation was successful, false otherwise
     */
    public static boolean writeToFile(File file, String content) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
