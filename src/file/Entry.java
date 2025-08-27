package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * An Entry object represents a miscellaneous type of {@link Document} which contains generic text information.
 * Entry objects can be used to represent Locations, side characters and other lore
 * @author Matthias Amend
 */
public class Entry implements Document{

    private String title;
    private Path path;

    private String content;

    /**
     * Create the document as a file.
     * If a file at the specified path does not yet exist, a new file will be created.
     * Should a file with the same name exist at the specified location, the existing file will NOT be overwritten
     */
    @Override
    public void create() {
        if(!Document.doesFileExist(path)) {
            try {
                File file = path.toFile();
                Boolean success = file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error occurred creating File: " + e);
            }
        }
    }

    /**
     * Get the title string of the document.
     * @return The title string
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Get the path of the document.
     * @return The path object
     */
    @Override
    public Path getPath() {
        return path;
    }

    /**
     * Load file content into the document object.
     */
    @Override
    public void read() {
        content = DocumentReader.readFile(path);
    }

    /**
     * Write the content string into the file.
     */
    @Override
    public void write() {
        DocumentWriter.writeToFile(path, content);
    }

    /**
     * Get the content of the document as a string.
     * @return The content string
     */
    @Override
    public String getContent() {
        return content;
    }

}
