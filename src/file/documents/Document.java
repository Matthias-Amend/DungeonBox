package file.documents;
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
     * @return True if the file creation was successful, false otherwise
     */
    public boolean create();

    /**
     * Get the title string of the document.
     * @return The title string
     */
    public String getTitle();

    /**
     * Load file content into the document object.
     * @return True if the reading operation was successful, false otherwise
     */
    public boolean read();

    /**
     * Write the content string into the file.
     * @return True if the writing operation was successful, false otherwise
     */
    public boolean write();

    /**
     * Get the document type of the document
     * @return The document type
     */
    public DocumentType getDocumentType();

    /**
     * Check if a file at the specified path exists already.
     * @param path The path to the file
     * @return True if a file already exists, false otherwise
     */
    public static boolean doesFileExist(Path path) {
        return path.toFile().exists();
    }

    /**
     * Get the absolute file location of the document.
     * @return The absolute file location
     */
    public File getFileLocation();

}
