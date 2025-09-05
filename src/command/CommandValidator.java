package command;

import command.output.ExecutionReceipt;
import command.tokenizer.Token;
import command.tokenizer.TokenType;
import command.tokenizer.validation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandValidator {

    private File root;

    /**
     * Validate a command.
     * Check if a command only contains valid tokens and compare the commands tokens to the expected tokens
     * @param command The command object
     * @return NO_ERROR if no error occurred, otherwise returns a valid error type
     */
    public ExecutionReceipt validate(Command command) {
        Token[] commandTokens = command.getTokens();
        CommandType commandType = CommandType.getCommandType(command.getCommandID());
        if(commandType == null) {
            return new ExecutionReceipt(commandType, ErrorType.INVALID_COMMAND, commandTokens[0].value());
        }
        List<ValidatorToken> requiredTokens = commandType.getRequiredTokens();
        List<ValidatorToken> optionalTokens = commandType.getOptionalTokens();
        if(commandTokens.length < requiredTokens.size() || commandTokens.length > requiredTokens.size() + optionalTokens.size()) {
            return new ExecutionReceipt(commandType, ErrorType.INCORRECT_ARGUMENTS, null);
        }
        for(int index = 0; index < requiredTokens.size(); index++) {
            Token token = commandTokens[index];
            ValidatorToken expectedToken = requiredTokens.get(index);
            ErrorType errorType = checkArgument(token, expectedToken);
            if(errorType != ErrorType.NO_ERROR) {
                return new ExecutionReceipt(commandType, errorType, token.value());
            }
        }
        List<Integer> matchedIndices = new ArrayList<>();
        for(int index = requiredTokens.size(); index < commandTokens.length; index++) {
            Token token = commandTokens[index];
            for(int optionalTokenIndex = 0; optionalTokenIndex < optionalTokens.size(); optionalTokenIndex++) {
                ValidatorToken optionalToken = optionalTokens.get(optionalTokenIndex);
                if(!matchedIndices.contains(optionalTokenIndex)) {
                    if(checkArgument(token, optionalToken) == ErrorType.NO_ERROR) {
                        matchedIndices.add(optionalTokenIndex);
                    }
                }
            }
        }
        if(commandTokens.length - requiredTokens.size() != matchedIndices.size()) {
            return new ExecutionReceipt(commandType, ErrorType.INCORRECT_ARGUMENTS, null);
        }
        return new ExecutionReceipt(commandType, ErrorType.NO_ERROR, null);
    }

    private ErrorType checkArgument(Token token, ValidatorToken expectedToken) {
        TokenType validatorTokenType = expectedToken.getTokenType();
        switch (validatorTokenType) {
            case COMMAND -> {
                if(!isCommandTokenValid(token, (CommandToken) expectedToken)) {
                    return ErrorType.INVALID_COMMAND;
                }
            }
            case STRING -> {
                if(!isStringTokenValid(token, (StringToken) expectedToken)) {
                    return ErrorType.INCORRECT_ARGUMENTS;
                }
            }
            case NUMBER -> {
                if(!isNumberTokenValid(token, (NumberToken) expectedToken)) {
                    return ErrorType.INCORRECT_ARGUMENTS;
                }
            }
            case OPTION -> {
                if(!isOptionTokenValid(token, (OptionToken) expectedToken)) {
                    return ErrorType.INVALID_OPTION;
                }
            }
            case ARGUMENT -> {
                if(!isPathTokenValid(token, (PathToken) expectedToken)) {
                    return ErrorType.INVALID_PATH;
                }
            }
            case UNKNOWN -> {
                return ErrorType.INCORRECT_ARGUMENTS;
            }
        }
        return ErrorType.NO_ERROR;
    }

    /**
     * Set the root path.
     * @param root The root path
     */
    public void setRoot(File root) {
        this.root = root;
    }

    private boolean isPathTokenValid(Token token, PathToken pathToken) {
        if(token.tokenType() != pathToken.getTokenType()) {
            return false;
        }
        String tokenValue = token.value();
        tokenValue = tokenValue.replaceAll("\\\\", "");
        File path = new File(tokenValue);
        File pathFromRoot = new File(root, tokenValue);
        if(pathToken.exists() == path.exists() || pathToken.exists() == pathFromRoot.exists()) {
            //TODO implement file type checking
            return pathToken.isFile() == path.isFile() || pathToken.isFile() == pathFromRoot.isFile();
        } else if (!pathToken.exists() && (path.getParentFile().exists() || pathFromRoot.getParentFile().exists())) {
            //TODO implement file type checking
            return pathToken.isFile() == path.isFile() || pathToken.isFile() == pathFromRoot.isFile();
        }
        return false;
    }

    private boolean isStringTokenValid(Token token, StringToken stringToken) {
        return token.tokenType() == stringToken.getTokenType();
    }

    private boolean isNumberTokenValid(Token token, NumberToken numberToken) {
        if(token.tokenType() != numberToken.getTokenType()) {
            return false;
        }
        float value = Float.valueOf(token.value());
        if(numberToken.lowerBound() != null) {
            if(value < numberToken.lowerBound()) {
                return false;
            }
        }
        if(numberToken.upperBound() != null) {
            if(value > numberToken.upperBound()) {
                return false;
            }
        }
        return true;
    }

    private boolean isCommandTokenValid(Token token, CommandToken commandToken) {
        return token.tokenType() == commandToken.getTokenType();
    }

    private boolean isOptionTokenValid(Token token, OptionToken optionToken) {
        if(token.tokenType() != optionToken.getTokenType()) {
            return false;
        }
        String value = token.value();
        return optionToken.validOptions().contains(value);
    }

    private boolean isArgumentTokenValid(Token token, ArgumentToken argumentToken) {
        return token.tokenType() == argumentToken.getTokenType();
    }



}
