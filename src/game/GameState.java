package game;

import command.Command;
import command.CommandChecker;
import command.CommandType;
import command.ErrorType;

public class GameState {

    private boolean simulationRunning = true;

    /**
     * Execute a Command.
     * @param command The command
     */
    public ErrorType execute(Command command) {
        ErrorType errorType = CommandChecker.check(command);
        if(errorType != ErrorType.NO_ERROR) {
            return errorType;
        }
        CommandType commandType = command.getCommandType();
        switch(commandType) {
            case EXIT:
                simulationRunning = false;
                break;
            case CREATE:
                errorType = executeCreateCommand(command);
                break;
            default:
                break;
        }
        return errorType;
    }

    public ErrorType executeCreateCommand(Command command) {

        return ErrorType.NO_ERROR;
    }

    public boolean isSimulationRunning() {
        return simulationRunning;
    }

}
