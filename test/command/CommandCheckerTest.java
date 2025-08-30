package command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandCheckerTest {

    @Test
    public void testInvalidCommandType() {
        String testCommandString = "creat -p DungeonBox -c -n \"Matthias Amend\"";
        Command command = new Command(testCommandString);
        ErrorType actualError = CommandChecker.check(command);
        ErrorType expectedError = ErrorType.INVALID_COMMAND;
        assertEquals(expectedError, actualError);
    }

    @Test
    public void testInvalidFlag() {
        String testCommandString = "create -z DungeonBox -c -n \"Matthias Amend\"";
        Command command = new Command(testCommandString);
        ErrorType actualError = CommandChecker.check(command);
        ErrorType expectedError = ErrorType.INVALID_FLAG;
        assertEquals(expectedError, actualError);
    }

    @Test
    public void testIncorrectFollowupArgument() {
        String testCommandString = "create -p \"DungeonBox\" -c -n \"Matthias Amend\"";
        Command command = new Command(testCommandString);
        ErrorType actualError = CommandChecker.check(command);
        ErrorType expectedError = ErrorType.INCORRECT_FOLLOWUP_ARGUMENT;
        assertEquals(expectedError, actualError);
    }

    @Test
    public void testIncorrectFlagTypes() {
        String testCommandString = "create -n \"Noah Hüttner\" -c -n \"Matthias Amend\"";
        Command command = new Command(testCommandString);
        ErrorType actualError = CommandChecker.check(command);
        ErrorType expectedError = ErrorType.INCORRECT_FLAG_TYPES;
        assertEquals(expectedError, actualError);
    }

    @Test
    public void testCorrectChecking() {
        String testCommandString = "create -p DungeonBox -c -n \"Matthias Amend\"";
        Command command = new Command(testCommandString);
        ErrorType actualError = CommandChecker.check(command);
        ErrorType expectedError = ErrorType.NO_ERROR;
        assertEquals(expectedError, actualError);
    }

}
