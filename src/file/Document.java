package file;
import java.io.File;
import java.nio.file.Path;

/**
 * The Document interface represents a file which contains information relating to the campaign.
 * @author Matthias Amend
 */
public interface Document {

    /**
     * Create the document as a file.
     * If a file at the specified path does not yet exist, a new file will be created.
     * Should a file with the same name exist at the specified location, the existing file will NOT be overwritten
     */
    public void create();

    /**
     * Get the title string of the document.
     * @return The title string
     */
    public String getTitle();

    /**
     * Get the path of the document.
     * @return The path object
     */
    public File getPath();

    /**
     * Load file content into the document object.
     */
    public void read();

    /**
     * Write the content string into the file.
     */
    public void write();

    /**
     * Get the content of the document as a string.
     * @return The content string
     */
    public String getContent();

    /**
     * Check if a file at the specified path exists already.
     * @param path The path to the file
     * @return True if a file already exists, false otherwise
     */
    public static boolean doesFileExist(Path path) {
        return path.toFile().exists();
    }

}
