package command;

import command.structure.ArgumentMatcher;
import command.structure.ArgumentType;
import command.structure.Flag;
import command.structure.FlagType;

import java.util.Arrays;
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
        if(commandType == null) {
            return ErrorType.INVALID_COMMAND;
        }
        String[] argumentStrings = command.getArguments();
        for(int argumentIndex = 0; argumentIndex < argumentStrings.length; argumentIndex++) {
            ArgumentType actualArgumentType = ArgumentMatcher.match(argumentStrings[argumentIndex]);
            if(actualArgumentType == ArgumentType.FLAG) {
                FlagType flagType = FlagType.getFlagType(argumentStrings[argumentIndex]);
                if(flagType == null) {
                    return ErrorType.INVALID_FLAG;
                }
                switch (flagType) {
                    case PATH, NAME -> {
                        if (!isNextArgumentTypeOf(argumentIndex, ArgumentType.TEXT, argumentStrings)) {
                            return ErrorType.INCORRECT_FOLLOWUP_ARGUMENT;
                        }
                    }
                    case HEALTH -> {
                        if(!isNextArgumentTypeOf(argumentIndex, ArgumentType.NUMBER, argumentStrings)) {
                            return ErrorType.INCORRECT_FOLLOWUP_ARGUMENT;
                        }
                    }
                    default -> {
                    }
                }
            }
        }
        if(!compareFlagTypes(commandType.getRequiredFlagTypes(), command.getFlagTypes())) {
            return ErrorType.INCORRECT_FLAG_TYPES;
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
     * Check if two flag type arrays contain the same arguments.
     * @param expectedFlags The expected flag array
     * @param actualFlags The actual flag type array
     * @return True if the arrays contain the same arguments, false otherwise
     */
    private static boolean compareFlagTypes(Flag[] expectedFlags, FlagType[] actualFlags) {
        if(actualFlags.length > expectedFlags.length) {
            return false;
        }
        List<FlagType> actualFlagTypes = Arrays.asList(actualFlags);
        for(Flag expectedFlag : expectedFlags) {
            if(!actualFlagTypes.contains(expectedFlag.type()) && expectedFlag.required()) {
                return false;
            }
        }
        return true;
    }

}
