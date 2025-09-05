package file;

import file.documents.CharacterDocument;
import game.objects.character.health.Vitality;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentTest {

    @Test
    public void testHealthWriting() {
        File file = new File("/Users/matthiasamend/Desktop/DND/util/Default Character Health");
        File characterFile = new File("/Users/matthiasamend/Desktop/DND/characters");
        CharacterDocument characterDocument = new CharacterDocument("Test", characterFile);
        characterDocument.getCharacter().addLimb("Head", Vitality.VITAL, 25);
        characterDocument.getCharacter().addLimb("Torso", Vitality.VITAL, 60);
        characterDocument.getCharacter().addLimb("Left Arm", Vitality.NOT_VITAL, 30);
        characterDocument.getCharacter().addLimb("Right Arm", Vitality.NOT_VITAL, 30);
        characterDocument.getCharacter().addLimb("Left Leg", Vitality.NOT_VITAL, 40);
        characterDocument.getCharacter().addLimb("Right Leg", Vitality.NOT_VITAL, 40);
        characterDocument.writeHealthToFile(file);
    }

    @Test
    public void testHealthReading() {
        File file = new File("/Users/matthiasamend/Desktop/DND/util/Default Character Health");
        File characterFile = new File("/Users/matthiasamend/Desktop/DND/characters");
        CharacterDocument characterDocument = new CharacterDocument("Test", characterFile);
        characterDocument.readHealthFromFile(file);
        assertTrue(characterDocument.getCharacter().hasLimb("Head"));
    }



}
