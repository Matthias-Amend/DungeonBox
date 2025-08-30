package file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import game.objects.Character;
import game.objects.health.Vitality;

import java.io.File;
import java.io.IOException;

public class CharacterDocument implements Document{

    private String title;
    private File path;

    private Character character;
    private String content;

    public CharacterDocument(String title, File path) {
        this.title = title;
        this.path = path;
        character = new Character(title);
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
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            content = writer.writeValueAsString(character);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
     * Add a new Limb to the characters body.
     * @param identifier The identifier of the limb
     * @param vitality The vitality of the limb
     * @param health The health value of the limb
     */
    public void addLimb(String identifier, Vitality vitality, float health) {
        character.getBody().addLimb(identifier, vitality, health);
    }

}
