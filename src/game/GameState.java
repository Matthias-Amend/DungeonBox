package game;

import command.Command;
import command.CommandChecker;
import command.CommandType;
import command.ErrorType;
import command.structure.FlagType;
import file.CharacterDocument;
import file.Document;
import file.EntryDocument;
import game.objects.health.Vitality;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameState {

    private boolean simulationRunning = true;

    private List<Document> documents = new ArrayList<Document>();

    String rootPath = "";

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
            case SET_ROOT:
                errorType = executeSetRoot(command);
                break;
            case WRITE:
                errorType = executeWrite(command);
                break;
            case ADD_LIMB:
                errorType = executeAddLimb(command);
                break;
        }
        return errorType;
    }

    /**
     * Create a new Document.
     * @param command The create command object
     * @return The error type, NO_ERROR if no error occurred
     */
    private ErrorType executeCreateCommand(Command command) {
        String commandString = command.commandString();
        String pathString = command.getFlagVariable(FlagType.PATH);
        String titleString = command.getFlagVariable(FlagType.NAME);
        titleString = Command.stripQuotations(titleString);
        pathString = Command.stripQuotations(pathString);
        File root = new File(rootPath);
        File directory = new File(root, pathString);
        if(!directory.isDirectory()) {
            return ErrorType.INVALID_PATH;
        }
        if(commandString.contains("-c") || commandString.contains("--character")) {
            CharacterDocument document = new CharacterDocument(titleString, directory);
            document.create();
            documents.add(document);
        } else if(commandString.contains("-e") || commandString.contains("--entry")) {
            EntryDocument document = new EntryDocument(titleString, directory);
            document.create();
            documents.add(document);
        } else if(commandString.contains("-d") || commandString.contains("--directory")) {
            File newDirectory = new File(directory, titleString);
            boolean success = newDirectory.mkdir();
        }
        return ErrorType.NO_ERROR;
    }

    /**
     * Set the root path.
     * @param command The set root command object
     * @return The error type, NO_ERROR if no error occurred
     */
    private ErrorType executeSetRoot(Command command) {
        String pathString = command.getFlagVariable(FlagType.PATH);
        pathString = Command.stripQuotations(pathString);
        File root = new File(pathString);
        if(!root.isDirectory()) {
            return ErrorType.INVALID_PATH;
        }
        rootPath = pathString;
        return ErrorType.NO_ERROR;
    }

    /**
     * Write the content string of a document to the document file.
     * @param command The write command object
     * @return The error type, NO_ERROR if no error occurred
     */
    private ErrorType executeWrite(Command command) {
        String pathString = command.getFlagVariable(FlagType.PATH);
        File root = new File(rootPath);
        pathString = Command.stripQuotations(pathString);
        Document document = getDocumentAtPath(new File(root, pathString));
        if(document == null) {
            return ErrorType.INVALID_PATH;
        }
        document.write();
        return ErrorType.NO_ERROR;
    }

    private ErrorType executeAddLimb(Command command) {
        String pathString = command.getFlagVariable(FlagType.PATH);
        pathString = Command.stripQuotations(pathString);
        File root = new File(rootPath);
        Document document = getDocumentAtPath(new File(root, pathString));
        if(document == null) {
            return ErrorType.INVALID_PATH;
        }
        if(document.getClass() != CharacterDocument.class) {
            return ErrorType.INVALID_PATH;
        }
        String identifier = Command.stripQuotations(command.getFlagVariable(FlagType.NAME));
        float health = Float.parseFloat(command.getFlagVariable(FlagType.HEALTH));
        Vitality vitality = Vitality.NOT_VITAL;
        if(command.containsFlag(FlagType.VITAL)) {
            vitality = Vitality.VITAL;
        }
        ((CharacterDocument) document).addLimb(identifier, vitality, health);
        return ErrorType.NO_ERROR;
    }

    /**
     * Get the document at the specified path.
     * @param path The path
     * @return The document at the specified path, if no document at the specified path exists returns null
     */
    private Document getDocumentAtPath(File path) {
        for(Document document : documents) {
            File documentPath = new File(document.getPath(), document.getTitle());
            if(documentPath.equals(path)) {
                return document;
            }
        }
        return null;
    }

    public boolean isSimulationRunning() {
        return simulationRunning;
    }

}
