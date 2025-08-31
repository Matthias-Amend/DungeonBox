package command;

import command.tokenizer.TokenType;
import java.util.List;

/**
 * The CommandType enum contains all possible types of {@link Command commands} that a user can execute.
 * @author Matthias Amend
 */
public enum CommandType {

    EXIT("exit", List.of(TokenType.COMMAND)),

    CREATE("create", List.of(TokenType.COMMAND, TokenType.OPTION, TokenType.STRING, TokenType.STRING)),

    SET_ROOT("set_root", List.of(TokenType.COMMAND, TokenType.STRING)),

    WRITE("write", List.of(TokenType.COMMAND, TokenType.STRING)),

    ADD_LIMB("add_limb", List.of(TokenType.COMMAND, TokenType.STRING, TokenType.STRING, TokenType.NUMBER), List.of(TokenType.OPTION)),

    MODIFY_HEALTH("modify_health", List.of(TokenType.COMMAND, TokenType.STRING, TokenType.STRING, TokenType.NUMBER));

    private final String commandID;
    private final List<TokenType> requiredTokens;
    private final List<TokenType> optionalTokens;

    CommandType(String commandID, List<TokenType> requiredTokens) {
        this.commandID = commandID;
        this.requiredTokens = requiredTokens;
        this.optionalTokens = List.of();
    }

    CommandType(String commandID, List<TokenType> requiredTokens, List<TokenType> optionalTokens) {
        this.commandID = commandID;
        this.requiredTokens = requiredTokens;
        this.optionalTokens = optionalTokens;
    }

    public String getCommandID() {
        return commandID;
    }

    public List<TokenType> getRequiredTokens() {
        return requiredTokens;
    }

    public List<TokenType> getOptionalTokens() {
        return optionalTokens;
    }

    /**
     * Get the command type of a specific command object.
     * @param commandID The commandID string
     * @return The command type
     */
    public static CommandType getCommandType(String commandID) {
        for(CommandType commandType : CommandType.values()) {
            if(commandID.equals(commandType.getCommandID())) {
                return commandType;
            }
        }
        return null;
    }

}
