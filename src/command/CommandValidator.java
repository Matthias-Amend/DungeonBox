package command;

import command.tokenizer.Token;
import command.tokenizer.TokenType;

import java.util.List;

public class CommandValidator {

    /**
     * Validate a command.
     * Check if a command only contains valid tokens and compare the commands tokens to the expected tokens
     * @param command The command object
     * @return NO_ERROR if no error occurred, otherwise returns a valid error type
     */
    public static ErrorType validate(Command command) {
        Token[] commandTokens = command.getTokens();
        CommandType commandType = CommandType.getCommandType(command.getCommandID());
        if(commandType == null) {
            return ErrorType.INVALID_COMMAND;
        }
        List<TokenType> requiredTokens = commandType.getRequiredTokens();
        List<TokenType> optionalTokens = commandType.getOptionalTokens();
        if(commandTokens.length < requiredTokens.size()) {
            return ErrorType.INCORRECT_ARGUMENTS;
        }
        for(int index = 0; index < requiredTokens.size(); index++) {
            if(commandTokens[index].tokenType() != requiredTokens.get(index)) {
                return ErrorType.INCORRECT_ARGUMENTS;
            }
        }
        for(int index = requiredTokens.size(); index < commandTokens.length; index++) {
            TokenType tokenType = commandTokens[index].tokenType();
            if(!optionalTokens.contains(tokenType)) {
                return ErrorType.INCORRECT_ARGUMENTS;
            }
        }
        return ErrorType.NO_ERROR;
    }

}
