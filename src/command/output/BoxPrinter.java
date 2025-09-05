package command.output;

import game.objects.character.Character;

import java.util.*;
import java.util.stream.Collectors;

public class BoxPrinter {

    private static final int MIN_BUFFER = 4;
    private static final char BORDER_CORNER = '+';
    private static final char HORIZONTAL_BORDER = '-';
    private static final char VERTICAL_BORDER = '|';

    /**
     * Get a characters health values as a series of boxes.
     * Each limb is displayed in a single box with all relevant information like health value and vitality
     * @param character The character object
     * @return The output string
     */
    public static String getCharacterHealthOutput(Character character) {
        List<List<String>> boxes = character.getBody().values().stream()
                .map(limb -> List.of(
                        limb.getIdentifier(),
                        "hp: " + limb.getHealth() + "/" + limb.getMaxHealth(),
                        limb.getVitality().toString()
                ))
                .collect(Collectors.toList());

        return renderBoxes(boxes);
    }

    /**
     * Render a series of boxes which contain information.
     * @param boxes The 2D list of boxes
     * @return The output string
     */
    private static String renderBoxes(List<List<String>> boxes) {
        int boxHeight = boxes.stream().mapToInt(List::size).max().orElse(0);
        int boxWidth = boxes.stream()
                .flatMap(List::stream)
                .mapToInt(s -> s.length() + 2 * MIN_BUFFER)
                .max().orElse(0);

        StringBuilder sb = new StringBuilder();

        sb.append(horizontalBorder(boxes.size(), boxWidth)).append("\n");

        for (int row = 0; row < boxHeight; row++) {
            for (List<String> box : boxes) {
                String content = (row < box.size()) ? box.get(row) : "";
                sb.append(boxRow(content, boxWidth));
            }
            sb.append(VERTICAL_BORDER).append("\n");
        }

        sb.append(horizontalBorder(boxes.size(), boxWidth));
        return sb.toString();
    }

    /**
     * Get a row of a single box as a string.
     * @param value The string that is supposed to be displayed
     * @param width The width of a single box
     * @return The box row string
     */
    private static String boxRow(String value, int width) {
        int leftPad = (width - value.length()) / 2;
        int rightPad = width - value.length() - leftPad;
        return VERTICAL_BORDER + " ".repeat(leftPad) + value + " ".repeat(rightPad);
    }

    /**
     * get the horizontal border of the printed boxes.
     * @param count The amount of boxes
     * @param width The required width of each box
     * @return The horizontal border string
     */
    private static String horizontalBorder(int count, int width) {
        int totalWidth = count * (width + 1) + 1;
        StringBuilder sb = new StringBuilder(totalWidth);
        for (int i = 0; i < totalWidth; i++) {
            sb.append(i % (width + 1) == 0 ? BORDER_CORNER : HORIZONTAL_BORDER);
        }
        return sb.toString();
    }
}
