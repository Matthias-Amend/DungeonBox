import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public record Command(String commandString) {

    /**
     * Get the commandID string of the command string.
     * The commandID string represents what the command is meant to do
     * @return The commandID string
     */
    public String getCommandID() {
        return splitArguments()[0];
    }

    /**
     * Check if the commandID string of the command matches a valid commandID string.
     * @return True if the commandID string matches a valid commandID string, false otherwise
     */
    public boolean hasValidCommandID() {
        String commandID = getCommandID();
        for(CommandType commandType : CommandType.values()) {
            if(commandID.equals(commandType.getCommandID())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Split the command string into individual argument strings.
     * @return An array of argument strings
     */
    public String[] splitArguments() {
        List<String> arguments = new ArrayList<>();
        int startIndex = 0;
        int endIndex = 0;
        while(startIndex < commandString.length()) {
            if(commandString.charAt(startIndex) == '"') {
                endIndex = commandString.indexOf('"', startIndex + 1) + 1;
                if(endIndex == 0) {endIndex = commandString.length();}
                String argument = commandString.substring(startIndex, endIndex);
                if(!argument.isBlank()) {
                    arguments.add(argument);
                }
                startIndex = endIndex + 1;
            } else {
                endIndex = commandString.indexOf(' ', startIndex + 1);
                if(endIndex == -1) {endIndex = commandString.length();}
                String argument = commandString.substring(startIndex, endIndex);
                if(!argument.isBlank()) {
                    arguments.add(argument);
                    startIndex = endIndex + 1;
                } else {
                    startIndex = endIndex;
                }
            }
        }
        return arguments.toArray(new String[0]);
    }
}
