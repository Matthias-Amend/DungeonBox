package command;

import java.util.ArrayList;
import java.util.List;

/**
 * The command object contains all information about the command entered by the user
 * @param commandString
 */
public record Command(String commandString) {

    /**
     * Get the first argument string in the command string.
     * The first argument is always treated as the commandID
     * @return The commandID string
     */
    public String getCommandID() {
        return getArguments()[0];
    }

    /**
     * Split the command string into individual argument strings.
     * @return An array of argument strings
     */
    public String[] getArguments() {
        List<String> arguments = new ArrayList<>();
        int startingIndex = 0;
        while(startingIndex < commandString.length()) {
            int endingIndex = 0;
            if(commandString.charAt(startingIndex) == ' ') {
                startingIndex++;
            } else {
                if(commandString.charAt(startingIndex) == '"') {
                    endingIndex = commandString.indexOf('"', startingIndex + 1) + 1;
                } else {
                    endingIndex = commandString.indexOf(' ', startingIndex);
                }
                if(endingIndex == -1) {
                    endingIndex = commandString.length();
                }
                String argument = commandString.substring(startingIndex, endingIndex);
                if(!argument.isBlank()) {
                    arguments.add(argument);
                }
                startingIndex = endingIndex;
            }
        }
        return arguments.toArray(new String[0]);
    }

    /**
     * Get the command type of the command.
     * @return The command type of the command
     */
    public CommandType getCommandType() {
        return CommandType.getCommandType(this);
    }

}
