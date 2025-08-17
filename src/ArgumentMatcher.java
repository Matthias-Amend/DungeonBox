public class ArgumentMatcher {

    /**
     * Match an argument string to a specific argument type.
     * The argument type determines how the argument gets treated in the console upon entry
     * @param argument The argument string
     * @return The argument type
     */
    public static ArgumentType matchArgumentType(String argument) {
        ArgumentType argumentType = ArgumentType.REGULAR;
        if(isCommandID(argument)) {
            argumentType = ArgumentType.COMMAND;
        }
        if(isParameter(argument)) {
            argumentType = ArgumentType.PARAMETER;
        }
        if(isText(argument)) {
            argumentType = ArgumentType.TEXT;
        }
        return argumentType;
    }

    /**
     * Check if the argument string represents a commandID string.
     * @param argument The argument string
     * @return True if the argument string is a valid commandID, false otherwise
     */
    private static boolean isCommandID(String argument) {
        for(CommandType commandType : CommandType.values()) {
            if(argument.matches(commandType.getCommandID())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the argument string represents a parameter.
     * @param argument The argument string
     * @return True if the argument string is a parameter, false otherwise
     */
    private static boolean isParameter(String argument) {
        String parameterRegex = "-[a-zA-Z]|--[a-zA-Z]+";
        return argument.matches(parameterRegex);
    }

    /**
     * Check if the argument string represents a text.
     * @param argument The argument string
     * @return True if the argument string is a text, false otherwise
     */
    private static boolean isText(String argument) {
        String textRegex = "\"[a-zA-Z.,!? ]*\"?";
        return argument.matches(textRegex);
    }

}
