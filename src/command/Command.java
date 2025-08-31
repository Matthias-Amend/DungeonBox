package command;

import command.tokenizer.Token;
import command.tokenizer.Tokenizer;

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
        return getTokens()[0].value();
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
     * Get the command type of the command.
     * @return The command type of the command
     */
    public CommandType getCommandType() {
        return CommandType.getCommandType(getCommandID());
    }

    /**
     * Get a list of all arguments in the command string as an array of tokens.
     * Each token contains the token type variable as well as a value
     * @return The tokenized argument array
     */
    public Token[] getTokens() {
        return Tokenizer.tokenize(commandString);
    }

}
