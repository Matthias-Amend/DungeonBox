package game.objects.health;

import java.util.HashMap;
import java.util.List;

/**
 * A Body object represents an entities' health.
 * If a vital limb of a body is destroyed, the entity the body is attached to dies
 * Otherwise the entity dies upon the destruction of 2 non-vital limbs
 * @author Matthias Amend
 */
public class Body {

    private HashMap<String, Limb> bodyMap = new HashMap<>();

    /**
     * Add a new limb to the body object.
     * @param identifier The identifier of the limb object
     * @param vitality The vitality of the limb object
     * @param health The health value of the limb object
     */
    public void addLimb(String identifier, Vitality vitality, float health) {
        Limb limb = new Limb(identifier, vitality, health);
        bodyMap.put(identifier, limb);
    }

    /**
     * Change the limb health value by the specified value.
     * @param identifier The identifier of the limb
     * @param value The value by which to change the health value
     */
    public void modifyLimbHealth(String identifier, float value) {
        bodyMap.get(identifier).modifyHealthValue(value);
    }

    /**
     * Check if the body is still alive.
     * @return True all vital limbs have not been destroyed and less than 2 non-vital limbs have been destroyed, false otherwise
     */
    public boolean isBodyAlive() {
        int destroyedLimbCounter = 0;
        for(Limb limb : bodyMap.values()) {
            if(limb.isDestroyed()) {
                if(limb.getVitality() == Vitality.VITAL) {
                    return true;
                }
                destroyedLimbCounter++;
            }
        }
        if(destroyedLimbCounter >= 2) {
            return true;
        }
        return false;
    }

    /**
     * Get a limb with the specified identifier String from the body map
     * @param identifier The identifier string of the limb
     * @return The limb object, null if no limb with the specified identifier string exists
     */
    public Limb getLimb(String identifier) {
        return bodyMap.get(identifier);
    }

    /**
     * Get the body map
     * @return The body map
     */
    public HashMap<String, Limb> getBodyMap() {
        return bodyMap;
    }

}
