package game;

import command.Command;
import command.CommandType;
import command.CommandValidator;
import command.ErrorType;
import command.tokenizer.Token;
import file.documents.CharacterDocument;
import file.documents.Document;
import file.documents.DocumentType;
import file.documents.EntryDocument;
import game.objects.health.Vitality;
import org.jline.terminal.Terminal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static command.ErrorType.*;

public class GameState {

    private boolean simulationRunning = true;
    private List<Document> documents = new ArrayList<Document>();
    private File root = new File(System.getProperty("user.dir"));

    /**
     * Execute a Command.
     * @param command The command
     */
    public ErrorType execute(Command command, Terminal terminal) {
        ErrorType errorType = CommandValidator.validate(command);
        if(errorType != NO_ERROR) {
            return errorType;
        }
        CommandType commandType = command.getCommandType();
        switch(commandType) {
            case EXIT -> {
                simulationRunning = false;
            }
            case CREATE -> {
                errorType = executeCreateCommand(command);
                if(errorType == NO_ERROR) {
                    terminal.writer().println("New document created!");
                } else {
                    terminal.writer().println(errorType.toString() + " occurred while creating new document!");
                }
            }
            case SET_ROOT -> {
                errorType = executeSetRootCommand(command);
                if(errorType == NO_ERROR) {
                    terminal.writer().println("Set root path to " + root.getAbsolutePath());
                } else {
                    terminal.writer().println(errorType.toString() + " occurred while setting new root path!");
                }
            }
            case ADD_LIMB -> {
                errorType = executeAddLimbCommand(command);
                if(errorType == NO_ERROR) {
                    terminal.writer().println("Added limb to character!");
                } else {
                    terminal.writer().println(errorType.toString() + " occurred while adding limb to character!");
                }
            }
            case WRITE -> {
                errorType = executeWriteCommand(command);
                if(errorType == NO_ERROR) {
                    terminal.writer().println("Wrote to file!");
                } else {
                    terminal.writer().println(errorType.toString() + " occurred while writing to file!");
                }
            }
            case MODIFY_HEALTH -> {
                errorType = executeModifyHealthCommand(command);
                if(errorType == NO_ERROR) {
                    terminal.writer().println("Modified health value!");
                } else {
                    terminal.writer().println(errorType.toString() + " occurred while modifying health value!");
                }
            }
        }
        return errorType;
    }

    /**
     * Execute the create document command.
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ErrorType executeCreateCommand(Command command) {
        Token[] tokens = command.getTokens();
        String createTypeOption = tokens[1].value();
        if(!List.of("-c", "--character", "-e", "--entry").contains(createTypeOption)) {
            return INVALID_OPTION;
        }
        String pathString = tokens[2].value();
        File path = getTotalPathOfDirectory(pathString);
        if(path == null) {
            return INVALID_PATH;
        }
        String nameString = tokens[3].value();
        if(List.of("-c", "--character").contains(createTypeOption)) {
            CharacterDocument document = new CharacterDocument(nameString, path);
            document.create();
            documents.add(document);
        } else if(List.of("-e", "--entry").contains(createTypeOption)) {
            EntryDocument document = new EntryDocument(nameString, path);
            document.create();
            documents.add(document);
        }
        return NO_ERROR;
    }

    /**
     * Execute the set root path command.
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ErrorType executeSetRootCommand(Command command) {
        Token[] tokens = command.getTokens();
        String pathString = tokens[1].value();
        File path = new File(pathString);
        if(!path.isDirectory()) {
            return INVALID_PATH;
        }
        root = path;
        return NO_ERROR;
    }

    /**
     * Execute the add limb command.
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ErrorType executeAddLimbCommand(Command command) {
        Token[] tokens = command.getTokens();
        String pathString = tokens[1].value();
        File path = getTotalPathOfFile(pathString);
        if(path == null) {
            return INVALID_PATH;
        }
        if(!isFileOfType(path, DocumentType.CHARACTER)) {
            return INVALID_DOCUMENT;
        }
        String nameString = tokens[2].value();
        String healthString = tokens[3].value();
        float health = Float.parseFloat(healthString);
        Vitality vitality = Vitality.NOT_VITAL;
        if(tokens.length > 4) {
            String vitalityOptionString = tokens[4].value();
            if(List.of("-v", "--vital").contains(vitalityOptionString)) {
                vitality = Vitality.VITAL;
            } else {
                return INVALID_OPTION;
            }
        }
        CharacterDocument document = (CharacterDocument) getDocumentAtPath(path);
        document.getCharacter().addLimb(nameString, vitality, health);
        return NO_ERROR;
    }

    /**
     * Execute the write command.
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ErrorType executeWriteCommand(Command command) {
        Token[] tokens = command.getTokens();
        String pathString = tokens[1].value();
        File path = getTotalPathOfFile(pathString);
        if(path == null) {
            return INVALID_PATH;
        }
        getDocumentAtPath(path).write();
        return NO_ERROR;
    }

    /**
     * Execute the modify health command.
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ErrorType executeModifyHealthCommand(Command command) {
        Token[] tokens = command.getTokens();
        String pathString = tokens[1].value();
        File path = getTotalPathOfFile(pathString);
        if(path == null) {
            return INVALID_PATH;
        }
        if(!isFileOfType(path, DocumentType.CHARACTER)) {
            return INVALID_PATH;
        }
        CharacterDocument document = (CharacterDocument) getDocumentAtPath(path);
        String nameString = tokens[2].value();
        String valueString = tokens[3].value();
        float value = Float.valueOf(valueString);
        if(!document.getCharacter().hasLimb(nameString)) {
            return CHARACTER_ERROR;
        }
        document.getCharacter().modifyLimbHealth(nameString, value);
        return NO_ERROR;
    }

    /**
     * Add the user specified path to the root path if the user specified path is not already a valid directory.
     * If neither the users specified path nor the root path is a valid directory, null is returned
     * @param path The path
     * @return The total path to the directory
     */
    private File getTotalPathOfDirectory(String path) {
        File independentPath = new File(path);
        if(independentPath.isDirectory()) {
            return independentPath;
        } else {
            File pathFromRoot = new File(root, path);
            if(pathFromRoot.isDirectory()) {
                return pathFromRoot;
            }
        }
        return null;
    }

    /**
     * Add the user specified path to the root path if the user specified path is not already a valid File.
     * If neither the users specified path nor the root path is a valid File, null is returned
     * @param path The path
     * @return The total path to the file
     */
    private File getTotalPathOfFile(String path) {
        File independentPath = new File(path);
        if(independentPath.isFile()) {
            return independentPath;
        } else {
            File pathFromRoot = new File(root, path);
            if(pathFromRoot.isFile()) {
                return pathFromRoot;
            }
        }
        return null;
    }

    /**
     * Check if the specified file is of the correct document type.
     * @param path The path of the file
     * @param documentType The expected document type
     * @return True if the file is of the correct document type, false otherwise
     */
    private boolean isFileOfType(File path, DocumentType documentType) {
        for(Document document : documents) {
            File documentPath = new File(document.getPath(), document.getTitle());
            if(documentPath.equals(path) && document.getDocumentType() == documentType) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the document at the specified path.
     * @param path The path
     * @return The document located at the specified path
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
