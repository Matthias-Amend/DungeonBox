package game.objects;

import game.objects.health.Body;
import game.objects.health.Limb;

import java.util.HashMap;

/**
 * The Character object contains all relevant information about a player or enemy character.
 * This includes things like health, skills, inventory, etc
 * @author Matthias Amend
 */
public class Character {

    String name;

    Body body = new Body();

    /**
     * Create a new character object.
     * @param name The name of the character
     */
    public Character(String name) {
        this.name = name;
    }

    /**
     * Get the body attached to the character.
     * @return The body object
     */
    public Body getBody() {
        return body;
    }

}
