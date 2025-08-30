package game.objects.health;

/**
 * A Limb represents a single part of a body.
 * Limbs can be damaged or healed and once a limb reaches 0 health it is destroyed
 * If a limb is considered to be vital, destruction means that the entity the limb is attached to dies
 * Otherwise the entity dies after two non-vital limbs have been destroyed
 * @author Matthias Amend
 */
public class Limb {

    private String identifier;
    private Vitality vitality;
    private float maxHealth;
    private float health;

    public Limb(String identifier, Vitality vitality, float health) {
        this.identifier = identifier;
        this.vitality = vitality;
        this.maxHealth = health;
        this.health = maxHealth;
    }

    /**
     * Change the health value.
     * @param value The value by which to change the health value
     */
    public void modifyHealthValue(float value) {
        health += value;
    }

    /**
     * Check if the limb has been destroyed.
     * A limb is destroyed when it reaches 0 health points
     * @return True if the limb has been destroyed, false otherwise
     */
    public boolean isDestroyed() {
        return health <= 0;
    }

    /**
     * Get the vitality of the limb.
     * @return The vitality of the limb
     */
    public Vitality getVitality() {
        return vitality;
    }
}
