package game.objects;

import game.objects.health.Limb;
import game.objects.health.Vitality;

import java.util.HashMap;

/**
 * The Character object contains all relevant information about a player or enemy character.
 * This includes things like health, skills, inventory, etc
 * @author Matthias Amend
 */
public class Character {

    String name;

    private HashMap<String, Limb> bodyMap = new HashMap<>();

    /**
     * Create a new character object.
     * @param name The name of the character
     */
    public Character(String name) {
        this.name = name;
    }

    /**
     * Add a Limb to the characters body.
     * @param identifier The identifier of the limb
     * @param vitality The vitality value of the limb
     * @param health The health value of the limb
     */
    public void addLimb(String identifier, Vitality vitality, float health) {
        Limb limb = new Limb(identifier, vitality, health);
        bodyMap.put(identifier, limb);
    }

    public void removeLimb(String identifier) {
        bodyMap.remove(identifier);
    }

    public boolean hasLimb(String identifier) {
        return bodyMap.containsKey(identifier);
    }

    /**
     * Modify a specific limbs health value.
     * @param identifier The identifier of the limb
     * @param value The value by which to change the limbs health
     */
    public void modifyLimbHealth(String identifier, float value) {
        bodyMap.get(identifier).modifyHealthValue(value);
    }

    /**
     * Get the body map attached to the character.
     * @return The body map
     */
    public HashMap<String, Limb> getBody() {
        return bodyMap;
    }

}
