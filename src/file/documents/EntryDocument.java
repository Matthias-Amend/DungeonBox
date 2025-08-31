package file.documents;

import file.DocumentReader;
import file.DocumentWriter;

import java.io.File;
import java.io.IOException;

/**
 * An Entry object represents a miscellaneous type of {@link Document} which contains generic text information.
 * Entry objects can be used to represent Locations, side characters and other lore
 * @author Matthias Amend
 */
public class EntryDocument implements Document{

    private String title;
    private File path;

    private String content;

    public EntryDocument(String title, File path) {
        this.title = title;
        this.path = path;
    }

    /**
     * Create the document as a file.
     * If a file at the specified path does not yet exist, a new file will be created.
     * Should a file with the same name exist at the specified location, the existing file will NOT be overwritten
     */
    @Override
    public void create() {
        try {
            File file = new File(path, title);
            Boolean success = file.createNewFile();
        } catch (IOException e) {
            System.err.println("Error occurred creating File: " + e);
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
    public File getPath() {
        return path;
    }

    /**
     * Load file content into the document object.
     */
    @Override
    public void read() {
        File totalPath = new File(path, title);
        content = DocumentReader.readFile(totalPath.toPath());
    }

    /**
     * Write the content string into the file.
     */
    @Override
    public void write() {
        File totalPath = new File(path, title);
        DocumentWriter.writeToFile(totalPath.toPath(), content);
    }

    /**
     * Get the content of the document as a string.
     * @return The content string
     */
    @Override
    public String getContent() {
        return content;
    }

    /**
     * Get the document type of the document
     * @return The document type
     */
    public DocumentType getDocumentType() {
        return DocumentType.ENTRY;
    }

}
