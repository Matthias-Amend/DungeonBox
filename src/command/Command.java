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
        Token[] tokens = getTokens();
        if(tokens.length != 0) {
            return getTokens()[0].value();
        }
        return null;
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
