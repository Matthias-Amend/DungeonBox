package command;

import command.structure.FlagType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    @Test
    public void testGetCommandID() {
        String testCommandString = "create -n \"Matthias Amend\" -p DungeonBox -c";
        Command testCommand = new Command(testCommandString);
        String actualCommandID = testCommand.getCommandID();
        String expectedCommandID = "create";
        assertEquals(expectedCommandID, actualCommandID);
    }

    @Test
    public void testFormattingArray() {
        String testCommandString = "create -n \"Matthias Amend\" -p DungeonBox -c";
        Command testCommand = new Command(testCommandString);
        String[] actualFormattingArray = testCommand.getFormattingArray();
        String[] expectedFormattingArray = new String[]{"create", " ", "-n", " ", "\"Matthias Amend\"",
                " ", "-p", " ", "DungeonBox", " ", "-c"};
        assertArrayEquals(expectedFormattingArray, actualFormattingArray);
    }

    @Test
    public void testQuotationFormattingArray() {
        String testCommandString = "create -n \"Matthias Amend -c -p DungeonBox";
        Command testCommand = new Command(testCommandString);
        String[] actualFormattingArray = testCommand.getFormattingArray();
        String[] expectedFormattingArray = new String[]{"create", " ", "-n", " ", "\"Matthias Amend -c -p DungeonBox"};
        assertArrayEquals(expectedFormattingArray, actualFormattingArray);
    }

    public void testGetArguments() {
        String testCommandString = "create -n \"Matthias Amend\" -p DungeonBox -c";
        Command testCommand = new Command(testCommandString);
        String[] actualArgumentArray = testCommand.getArguments();
        String[] expectedArgumentArray = new String[]{"create", "-n", "\"Matthias Amend\"", "-p", "DungeonBox", "-c"};
        assertEquals(expectedArgumentArray, actualArgumentArray);
    }

    @Test
    public void testCommandType() {
        String testCommandString = "set_root -p DungeonBox";
        Command testCommand = new Command(testCommandString);
        CommandType actualCommandType = testCommand.getCommandType();
        CommandType expectedCommandType = CommandType.SET_ROOT;
        assertEquals(expectedCommandType, actualCommandType);
    }

    @Test
    public void testGetFlagVariableSucess() {
        String testCommandString = "set_root -p DungeonBox";
        Command testCommand = new Command(testCommandString);
        FlagType flagType = FlagType.PATH;
        String actualFlagVariable = testCommand.getFlagVariable(flagType);
        String expectedFlagVariable = "DungeonBox";
        assertEquals(expectedFlagVariable, actualFlagVariable);
    }

    @Test
    public void testGetFlagVariableFail() {
        String testCommandString = "set_root -p";
        Command testCommand = new Command(testCommandString);
        FlagType flagType = FlagType.PATH;
        String actualFlagVariable = testCommand.getFlagVariable(flagType);
        assertNull(actualFlagVariable);
    }

    @Test
    public void testGetFlagTypes() {
        String testCommandString = "create -n \"Matthias Amend\" -p DungeonBox -c";
        Command testCommand = new Command(testCommandString);
        FlagType[] actualFlagTypes = testCommand.getFlagTypes();
        FlagType[] expectedFlagTypes = new FlagType[]{FlagType.NAME, FlagType.PATH, FlagType.CREATE_TYPE};
        assertArrayEquals(expectedFlagTypes, actualFlagTypes);
    }

    @Test
    public void testStripQuotations() {
        String testString = "\"Hello World\"";
        String actualOutput = Command.stripQuotations(testString);
        String expectedOutput = "Hello World";
        assertEquals(expectedOutput, actualOutput);
    }

}
