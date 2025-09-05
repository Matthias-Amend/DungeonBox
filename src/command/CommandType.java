package command;

import command.tokenizer.TokenType;
import command.tokenizer.validation.*;
import file.documents.DocumentType;

import java.util.List;

/**
 * The CommandType enum contains all possible types of {@link Command commands} that a user can execute.
 * @author Matthias Amend
 */
public enum CommandType {

    EXIT("exit", List.of(new CommandToken("exit"))),

    CREATE("create", List.of(new CommandToken("create"),
            new OptionToken("creation type", List.of("-c", "--character", "-e", "--entry")),
            new PathToken("path", false, true, DocumentType.UNSPECIFIED),
            new StringToken("file name"))),

    SET_ROOT("set_root", List.of(new CommandToken("set_root"),
            new PathToken("root path",false, true, DocumentType.UNSPECIFIED))),

    WRITE("write", List.of(new CommandToken("write"),
            new PathToken("write path",true, true, DocumentType.CHARACTER))),

    READ("read", List.of(new CommandToken("read"),
            new PathToken("read path",true, true, DocumentType.CHARACTER))),

    ADD_LIMB("add_limb", List.of(new CommandToken("add_limb"),
            new PathToken("character path",true, true, DocumentType.CHARACTER),
            new StringToken("limb name"),
            new NumberToken("health", null, 0f)),
            List.of(new OptionToken("vitality", List.of("-v", "--vital")))),

    REMOVE_LIMB("remove_limb", List.of(new CommandToken("remove_limb"),
            new PathToken("character path",true, true, DocumentType.CHARACTER),
            new StringToken("limb name"))),

    MODIFY_HEALTH("modify_health", List.of(new CommandToken("modify health"),
            new PathToken("character path", true, true, DocumentType.CHARACTER),
            new StringToken("limb name"),
            new NumberToken("value",null, null))),

    PRINT_HEALTH("print_health", List.of(new CommandToken("print_health"),
            new PathToken("character path", true, true, DocumentType.CHARACTER))),

    EXPORT_HEALTH("export_health", List.of(new CommandToken("export_health"),
            new PathToken("character path", true, true, DocumentType.CHARACTER),
            new PathToken("target directory", false, true, DocumentType.UNSPECIFIED),
            new StringToken("file name"))),

    IMPORT_HEALTH("import_health", List.of(new CommandToken("import_health"),
            new PathToken("character path", true, true, DocumentType.CHARACTER),
            new PathToken("source directory", true, true, DocumentType.UNSPECIFIED)));

    private final String commandID;
    private final List<ValidatorToken> requiredTokens;
    private final List<ValidatorToken> optionalTokens;

    CommandType(String commandID, List<ValidatorToken> requiredTokens) {
        this.commandID = commandID;
        this.requiredTokens = requiredTokens;
        this.optionalTokens = List.of();
    }

    CommandType(String commandID, List<ValidatorToken> requiredTokens, List<ValidatorToken> optionalTokens) {
        this.commandID = commandID;
        this.requiredTokens = requiredTokens;
        this.optionalTokens = optionalTokens;
    }

    public String getCommandID() {
        return commandID;
    }

    public List<ValidatorToken> getRequiredTokens() {
        return requiredTokens;
    }

    public List<ValidatorToken> getOptionalTokens() {
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

    /**
     * Get the command structure of a command type.
     * @param commandType The command type
     * @return The structure string
     */
    public static String getCommandStructureString(CommandType commandType) {
        StringBuilder structureBuilder = new StringBuilder();
        List<ValidatorToken> requiredTokens = commandType.getRequiredTokens();
        List<ValidatorToken> optionalTokens = commandType.getOptionalTokens();
        for(ValidatorToken token : requiredTokens) {
            switch (token.getTokenType()) {
                case COMMAND -> structureBuilder.append(token.getIdentifier());
                default -> {
                    structureBuilder.append("<").append(token.getIdentifier()).append(">");
                    structureBuilder.append("[").append(token.getTokenType()).append("]");
                }
            }
            structureBuilder.append(" ");
        }
        return structureBuilder.toString();
    }

}
