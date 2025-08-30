package command;

import command.structure.FlagType;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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
     * Get the formatting array of the command.
     * The formatting array contains all arguments of the command as well as every whitespace
     * When highlighting the command arguments, the formatting array is necessary
     * @return The commands formatting array
     */
    public String[] getFormattingArray() {
        List<String> arguments = new ArrayList<>();
        int startingIndex = 0;
        while(startingIndex < commandString.length()) {
            int endingIndex = 0;
            if(commandString.charAt(startingIndex) == ' ') {
                arguments.add(" ");
                startingIndex++;
            } else {
                if(commandString.charAt(startingIndex) == '"') {
                    endingIndex = commandString.indexOf('"', startingIndex + 1);
                    if(endingIndex == -1) {
                        endingIndex = commandString.length();
                    } else {
                        endingIndex++;
                    }
                } else {
                    endingIndex = commandString.indexOf(' ', startingIndex);
                    if(endingIndex == -1) {
                        endingIndex = commandString.length();
                    }
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
     * Split the command string into individual argument strings.
     * @return An array of argument strings
     */
    public String[] getArguments() {
        String[] formattingArray = getFormattingArray();
        List<String> arguments = new ArrayList<>();
        for(String formattingString : formattingArray) {
            if(!formattingString.isBlank()) {
                arguments.add(formattingString);
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

    /**
     * Get the Variable associated with a specific flag.
     * If the flag type is PATH, then the path argument following the path flag is returned
     * @param flag The flag type
     * @return The argument string associated with the flag, or null if no argument is associated with the flag type
     */
    public String getFlagVariable(FlagType flag) {
        String[] arguments = getArguments();
        int flagIndex = 0;
        for(String argument : arguments) {
            if(FlagType.getFlagType(argument) == flag && flagIndex + 1 < arguments.length) {
                return arguments[flagIndex + 1];
            }
            flagIndex++;
        }
        return null;
    }

    /**
     * Get an array of all flag types in the command.
     * @return The flag types array
     */
    public FlagType[] getFlagTypes() {
        String[] arguments = getArguments();
        List<FlagType> flagTypes = new ArrayList<>();
        for(String argument : arguments) {
            FlagType currentFlagType = FlagType.getFlagType(argument);
            if(currentFlagType != null) {
                flagTypes.add(currentFlagType);
            }
        }
        return flagTypes.toArray(new FlagType[0]);
    }

    /**
     * Check if the command contains a specific flag.
     * @param flagType The flag type of the flag
     * @return True if the command contains the flag type, false otherwise
     */
    public boolean containsFlag(FlagType flagType) {
        List<FlagType> flagTypes = Arrays.asList(getFlagTypes());
        return flagTypes.contains(flagType);
    }

    public static String stripQuotations(String argument) {
        return argument.replaceAll("\"", "");
    }

}
