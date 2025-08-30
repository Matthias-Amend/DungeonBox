package command.structure;

import command.CommandType;

public class ArgumentMatcher {

    /**
     * Match an argument string with an argument type.
     * @param argumentString The argument string
     * @return The argument type of the string
     */
    public static ArgumentType match(String argumentString) {
        if(isFlag(argumentString)) {
            return ArgumentType.FLAG;
        }
        if(isText(argumentString)) {
            return ArgumentType.TEXT;
        }
        if(isCommand(argumentString)) {
            return ArgumentType.COMMAND;
        }
        if(isNumber(argumentString)) {
            return ArgumentType.NUMBER;
        }
        return ArgumentType.ENTRY;
    }

    /**
     * Check if the argument string matches any of the valid command id strings.
     * @param argumentString The argument string
     * @return True if the argument string matches a command id string, false otherwise
     */
    private static boolean isCommand(String argumentString) {
        for(CommandType commandType : CommandType.values()) {
            if(argumentString.equals(commandType.getCommandID())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the argument string matches the flag regex string.
     * @param argumentString The argument string
     * @return True if the argument string matches the flag regex string, false otherwise
     */
    private static boolean isFlag(String argumentString) {
        return argumentString.matches("-[a-zA-Z]|--[a-zA-Z]+");
    }

    /**
     * Check if the argument string matches the text regex string.
     * @param argumentString The argument String
     * @return True if the argument string matches the text regex string, false otherwise
     */
    private static boolean isText(String argumentString) {
        return argumentString.matches("\"[^\"]*\"?");
    }

    /**
     * Check if the argument string can be converted to a floating point number.
     * @param argumentString The argument string
     * @return True if the argument string is a float, false otherwise
     */
    private static boolean isNumber(String argumentString) {
        try {
            float result = Float.parseFloat(argumentString);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;

    }

}
