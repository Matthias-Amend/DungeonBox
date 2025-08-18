import java.util.ArrayList;
import java.util.List;

public record Command(String commandString) {

    /**
     * Get the commandID string of the command string.
     * The commandID string represents what the command is meant to do
     * @return The commandID string
     */
    public String getCommandID() {
        return createFormattingArray()[0];
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
     * Split the command string into individual argument strings and whitespace strings.
     * @return An array of argument strings and whitespace strings
     */
    public String[] createFormattingArray() {
        List<String> arguments = new ArrayList<>();
        int startIndex = 0;
        int endIndex = 0;
        while(startIndex < commandString.length()) {
            if(commandString.charAt(startIndex) == ' ') {
                arguments.add(" ");
                startIndex++;
            } else if(commandString.charAt(startIndex) == '"') {
                endIndex = commandString.indexOf('"', startIndex + 1) + 1;
                if(endIndex == 0) {endIndex = commandString.length();}
                String argument = commandString.substring(startIndex, endIndex);
                arguments.add(argument);
                startIndex = endIndex;
            } else {
                endIndex = commandString.indexOf(' ', startIndex + 1);
                if(endIndex == -1) {endIndex = commandString.length();}
                String argument = commandString.substring(startIndex, endIndex);
                arguments.add(argument);
                startIndex = endIndex;
            }
        }
        return arguments.toArray(new String[0]);
    }

    /**
     * Split the command string into individual argument strings.
     * @return An array of argument strings
     */
    public String[] getArguments() {
        String[] formattingArray = createFormattingArray();
        List<String> arguments = new ArrayList<>();
        for(String argument : formattingArray) {
            if(!argument.isBlank()) {
                arguments.add(argument);
            }
        }
        return arguments.toArray(new String[0]);
    }

}
