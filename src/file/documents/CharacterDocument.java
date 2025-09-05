package file.documents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import file.DocumentWriter;
import game.objects.character.Character;
import game.objects.character.health.Limb;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CharacterDocument implements Document{

    private String title;
    private static final String EXTENSION = ".character";
    private File path;
    private final DocumentType documentType = DocumentType.CHARACTER;

    private Character character;

    public CharacterDocument(String title, File path) {
        this.title = title;
        this.path = path;
        character = new Character(title);
    }

    /**
     * Create the document as a file.
     * If a file at the specified path does not yet exist, a new file will be created.
     * Should a file with the same name exist at the specified location, the existing file will NOT be overwritten
     * @return True if the file creation was successful, false otherwise
     */
    @Override
    public boolean create() {
        try {
            File file = new File(path, title + EXTENSION);
            return file.createNewFile();
        } catch (IOException e) {
            return false;
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
     * Load file content into the document object.
     */
    @Override
    public boolean read() {
        File totalPath = new File(path, title + EXTENSION);
        ObjectMapper mapper = new ObjectMapper();
        Character characterObject;
        try {
            characterObject = mapper.readValue(totalPath, new TypeReference<Character>() {});
        } catch (IOException e) {
            return false;
        }
        character = characterObject;
        return true;
    }

    /**
     * Write the content string into the file.
     * @return True if the Writing operation was successful, false otherwise
     */
    @Override
    public boolean write() {
        File totalPath = new File(path, title + EXTENSION);
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String content;
        try {
            content = writer.writeValueAsString(character);
        } catch (JsonProcessingException e) {
            return false;
        }
        DocumentWriter.writeToFile(totalPath, content);
        return true;
    }

    /**
     * Write the characters health values to a file.
     * @param file The target file
     * @return True if the writing operation was successful, false otherwise
     */
    public boolean writeHealthToFile(File file) {
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String healthContent;
        try {
            healthContent = writer.writeValueAsString(character.getBody());
        } catch (JsonProcessingException e) {
            return false;
        }
        DocumentWriter.writeToFile(file, healthContent);
        return true;
    }

    /**
     * Read the characters health values from a file.
     * @param file The source file
     * @return True if the reading operation was successful, false otherwise
     */
    public boolean readHealthFromFile(File file) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Limb> bodyMap;
        try {
            bodyMap = mapper.readValue(file, new TypeReference<HashMap<String, Limb>>() {});
        } catch(IOException e) {
            return false;
        }
        character.setBody(bodyMap);
        return true;
    }

    /**
     * Get the document type of the document
     * @return The document type
     */
    public DocumentType getDocumentType() {
        return documentType;
    }

    /**
     * Get the absolute file location of the document.
     * @return The absolute file location
     */
    @Override
    public File getFileLocation() {
        return new File(path, title + EXTENSION);
    }

    /**
     * Get the character object attached to the document.
     * @return The character object
     */
    public Character getCharacter() {
        return character;
    }

}
