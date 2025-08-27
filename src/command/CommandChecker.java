package command;

import command.structure.ArgumentMatcher;
import command.structure.ArgumentType;
import command.structure.FlagType;

import java.util.HashSet;
import java.util.List;

/**
 * The CommandChecker class takes care of command formatting error handling.
 * Before a command can be executed, the checker analyzes the command format and determines whether the command can be executed or nor
 * @author Matthias Amend
 */
public class CommandChecker {

    /**
     * Check if a commands formatting is correct.
     * If a command is missing crucial arguments, the function returns a respective error code
     * @param command The command
     * @return an error type if the formatting is incorrect, otherwise NO_ERROR
     */
    public static ErrorType check(Command command) {
        CommandType commandType = command.getCommandType();
        ArgumentType[] expectedArguments = commandType.getArgumentStructure();
        String[] argumentStrings = command.getArguments();
        ArgumentType[] actualArguments = new ArgumentType[argumentStrings.length];
        for(int argumentIndex = 0; argumentIndex < argumentStrings.length; argumentIndex++) {
            ArgumentType actualArgumentType = ArgumentMatcher.match(argumentStrings[argumentIndex]);
            actualArguments[argumentIndex] = actualArgumentType;
            if(actualArgumentType == ArgumentType.FLAG) {
                FlagType flagType = FlagType.getFlagType(argumentStrings[argumentIndex]);
                if(flagType == null) {
                    return ErrorType.INVALID_FLAG;
                }
                switch (flagType) {
                    case PATH -> {
                        if (!isNextArgumentTypeOf(argumentIndex, ArgumentType.ENTRY, argumentStrings)) {
                            return ErrorType.INCORRECT_FOLLOWUP_ARGUMENT;
                        }
                    }
                    case NAME -> {
                        if (!isNextArgumentTypeOf(argumentIndex, ArgumentType.TEXT, argumentStrings)) {
                            return ErrorType.INCORRECT_FOLLOWUP_ARGUMENT;
                        }
                    }
                    default -> {
                    }
                }
            }
        }
        if(!compareArgumentArrays(expectedArguments, actualArguments)) {
            return ErrorType.INCORRECT_ARGUMENTS;
        }
        return ErrorType.NO_ERROR;
    }

    /**
     * Check if the following argument is of a specific type.
     * @param currentIndex The current argument index
     * @param type The expected type of the next argument
     * @param argumentStrings The array of all user argument strings
     * @return True if the next argument is of the expected type, false otherwise
     */
    private static boolean isNextArgumentTypeOf(int currentIndex, ArgumentType type, String[] argumentStrings) {
        int nextIndex = currentIndex + 1;
        if(nextIndex < argumentStrings.length) {
            return ArgumentMatcher.match(argumentStrings[nextIndex]) == type;
        }
        return false;
    }

    /**
     * Check if two argument type arrays contain the same arguments.
     * @param expectedArguments The expected argument array
     * @param actualsArguments The actual argument array
     * @return True if the arrays contain the same arguments, false otherwise
     */
    private static boolean compareArgumentArrays(ArgumentType[] expectedArguments, ArgumentType[] actualsArguments) {
        List<ArgumentType> expectedArgumentTypes = List.of(expectedArguments);
        List<ArgumentType> actualArgumentTypes = List.of(actualsArguments);
        return new HashSet<>(actualArgumentTypes).containsAll(expectedArgumentTypes);
    }

}
