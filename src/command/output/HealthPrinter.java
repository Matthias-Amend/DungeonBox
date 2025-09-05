package command.output;

import game.objects.character.Character;
import game.objects.character.health.Limb;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.util.ArrayList;
import java.util.List;

public class HealthPrinter {

    private static final AttributedStyle LIMB_IDENTIFIER_STYLE = AttributedStyle.DEFAULT.foreground(AttributedStyle.BRIGHT);
    private static final AttributedStyle LIMB_HIGH_HEALTH_STYLE = AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN);
    private static final AttributedStyle LIMB_MID_HEALTH_STYLE = AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW);
    private static final AttributedStyle LIMB_LOW_HEALTH_STYLE = AttributedStyle.DEFAULT.foreground(AttributedStyle.RED);
    private static final AttributedStyle ARMORED_STYLE = AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN);

    public static AttributedString printHealth(Character character) {
        List<Limb> limbs = new ArrayList<>(character.getBody().values());
        AttributedStringBuilder healthBuilder = new AttributedStringBuilder();
        for(int limbIndex = 0; limbIndex < limbs.size(); limbIndex++) {
            healthBuilder.append(getLimbHealthString(limbs.get(limbIndex)));
            if(limbIndex < limbs.size() - 1) {
                healthBuilder.append("\n");
            }
        }
        return healthBuilder.toAttributedString();
    }

    /**
     * Convert a Limb to an attributed string.
     * @param limb The limb
     * @return The attributed string
     */
    private static AttributedString getLimbHealthString(Limb limb) {
        AttributedStringBuilder limbBuilder = new AttributedStringBuilder();
        limbBuilder.styled(LIMB_IDENTIFIER_STYLE, "<" + limb.getIdentifier() + ">").append(": ");
        float currentHealth = limb.getHealth();
        float maximumHealth = limb.getMaxHealth();
        boolean isArmored = limb.isArmored();
        if(currentHealth >= ((float) 2/3) * maximumHealth) {
            limbBuilder.styled(LIMB_HIGH_HEALTH_STYLE, "[" + currentHealth + "/" + maximumHealth + "]").append(" ");
        } else if (currentHealth >= ((float) 1/3) * maximumHealth) {
            limbBuilder.styled(LIMB_MID_HEALTH_STYLE, "[" + currentHealth + "/" + maximumHealth + "]").append(" ");
        } else {
            limbBuilder.styled(LIMB_LOW_HEALTH_STYLE, "[" + currentHealth + "/" + maximumHealth + "]").append(" ");
        }
        if(isArmored) {
            limbBuilder.styled(ARMORED_STYLE, "[Armored]");
        }
        return limbBuilder.toAttributedString();
    }

}
