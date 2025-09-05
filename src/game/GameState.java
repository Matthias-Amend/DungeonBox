package game;

import command.Command;
import command.CommandType;
import command.CommandValidator;
import command.output.BoxPrinter;
import command.output.ExecutionReceipt;
import command.output.HealthPrinter;
import command.tokenizer.Token;
import file.documents.CharacterDocument;
import file.documents.Document;
import game.objects.character.health.Vitality;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static command.ErrorType.*;

public class GameState {

    private boolean simulationRunning = true;
    private List<Document> documents = new ArrayList<Document>();
    private File root = new File(System.getProperty("user.dir"));

    private CommandValidator validator = new CommandValidator();


    /**
     * Execute a Command.
     * @param command The command
     */
    public ExecutionReceipt execute(Command command, Terminal terminal) {
        validator.setRoot(root);
        ExecutionReceipt receipt = validator.validate(command);
        if(receipt.error() != NO_ERROR) {
            return receipt;
        }
        CommandType commandType = command.getCommandType();
        switch(commandType) {
            case EXIT -> simulationRunning = false;
            case CREATE -> receipt = executeCreateCommand(command);
            case SET_ROOT -> receipt = executeSetRootCommand(command);
            case ADD_LIMB -> receipt = executeAddLimbCommand(command);
            case REMOVE_LIMB -> receipt = executeRemoveLimbCommand(command);
            case WRITE -> receipt = executeWriteCommand(command);
            case READ -> receipt = executeReadCommand(command);
            case MODIFY_HEALTH -> receipt = executeModifyHealthCommand(command);
            case PRINT_HEALTH -> receipt = executePrintHealthCommand(command, terminal);
            case EXPORT_HEALTH -> receipt = executeExportHealthCommand(command);
            case IMPORT_HEALTH -> receipt = executeImportHealthCommand(command);
        }
        return receipt;
    }

    /**
     * Execute the create document command.
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ExecutionReceipt executeCreateCommand(Command command) {
        Token[] tokens = command.getTokens();
        String createTypeOption = tokens[1].value();
        String pathString = tokens[2].value();
        String nameString = tokens[3].value();
        File path = getTotalPathOfDirectory(pathString);
        if(List.of("-c", "--character").contains(createTypeOption)) {
            CharacterDocument document = new CharacterDocument(nameString, path);
            document.create();
            documents.add(document);
        }
        return new ExecutionReceipt(command.getCommandType(), NO_ERROR, null);
    }

    /**
     * Execute the set root path command.
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ExecutionReceipt executeSetRootCommand(Command command) {
        Token[] tokens = command.getTokens();
        String pathString = tokens[1].value();
        File path = new File(pathString);
        root = path;
        validator.setRoot(root);
        return new ExecutionReceipt(command.getCommandType(), NO_ERROR, null);
    }

    /**
     * Execute the add limb command.
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ExecutionReceipt executeAddLimbCommand(Command command) {
        Token[] tokens = command.getTokens();
        String pathString = tokens[1].value();
        String nameString = tokens[2].value();
        String healthString = tokens[3].value();
        float health = Float.parseFloat(healthString);
        File path = getTotalPathOfFile(pathString);
        Vitality vitality = Vitality.NOT_VITAL;
        if(tokens.length > 4) {
            String vitalityOptionString = tokens[4].value();
            if(List.of("-v", "--vital").contains(vitalityOptionString)) {
                vitality = Vitality.VITAL;
            }
        }
        CharacterDocument document = (CharacterDocument) getDocumentAtPath(path);
        document.getCharacter().addLimb(nameString, vitality, health);
        return new ExecutionReceipt(command.getCommandType(), NO_ERROR, null);
    }

    /**
     * execute the remove limb command
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ExecutionReceipt executeRemoveLimbCommand(Command command) {
        Token[] tokens = command.getTokens();
        String pathString = tokens[1].value();
        String nameString = tokens[2].value();
        File path = getTotalPathOfFile(pathString);
        CharacterDocument document = (CharacterDocument) getDocumentAtPath(path);
        if(!document.getCharacter().hasLimb(nameString)) {
            return new ExecutionReceipt(command.getCommandType(), CHARACTER_ERROR, null);
        }
        document.getCharacter().removeLimb(nameString);
        return new ExecutionReceipt(command.getCommandType(), NO_ERROR, null);
    }

    /**
     * Execute the write command.
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ExecutionReceipt executeWriteCommand(Command command) {
        Token[] tokens = command.getTokens();
        String pathString = tokens[1].value();
        File path = getTotalPathOfFile(pathString);
        getDocumentAtPath(path).write();
        return new ExecutionReceipt(command.getCommandType(), NO_ERROR, null);
    }

    private ExecutionReceipt executeReadCommand(Command command) {
        Token[] tokens = command.getTokens();
        String pathString = tokens[1].value();
        File path = getTotalPathOfFile(pathString);
        if(pathString.endsWith(".character")) {
            String fileName = path.getName().replace(".character", "");
            CharacterDocument document = new CharacterDocument(fileName, path.getParentFile());
            if(!document.read()) {
                return new ExecutionReceipt(command.getCommandType(), FILE_ERROR, path.getAbsolutePath());
            }
            documents.add(document);
        } else {
            return new ExecutionReceipt(command.getCommandType(), INVALID_PATH, path.getAbsolutePath());
        }
        return new ExecutionReceipt(command.getCommandType(), NO_ERROR, null);
    }

    /**
     * Execute the modify health command.
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ExecutionReceipt executeModifyHealthCommand(Command command) {
        Token[] tokens = command.getTokens();
        String pathString = tokens[1].value();
        String nameString = tokens[2].value();
        String valueString = tokens[3].value();
        float value = Float.valueOf(valueString);
        File path = getTotalPathOfFile(pathString);
        CharacterDocument document = (CharacterDocument) getDocumentAtPath(path);
        if(!document.getCharacter().hasLimb(nameString)) {
            return new ExecutionReceipt(command.getCommandType(), CHARACTER_ERROR, null);
        }
        document.getCharacter().modifyLimbHealth(nameString, value);
        return new ExecutionReceipt(command.getCommandType(), NO_ERROR, null);
    }

    /**
     * execute the print health command.
     * @param command The command object
     * @param terminal The terminal object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ExecutionReceipt executePrintHealthCommand(Command command, Terminal terminal) {
        Token[] tokens = command.getTokens();
        String pathString = tokens[1].value();
        File path = getTotalPathOfFile(pathString);
        CharacterDocument document = (CharacterDocument) getDocumentAtPath(path);
        AttributedString output = HealthPrinter.printHealth(document.getCharacter());
        terminal.writer().println(output.toAnsi());
        return new ExecutionReceipt(command.getCommandType(), NO_ERROR, null);
    }

    /**
     * Execute the export health command
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ExecutionReceipt executeExportHealthCommand(Command command) {
        Token[] tokens = command.getTokens();
        String sourcePathString = tokens[1].value();
        String targetPathDirectoryString = tokens[2].value();
        String nameString = tokens[3].value();
        File sourcePath = getTotalPathOfFile(sourcePathString);
        File targetPathDirectory = getTotalPathOfDirectory(targetPathDirectoryString);
        CharacterDocument document = (CharacterDocument) getDocumentAtPath(sourcePath);
        File targetFile = new File(targetPathDirectory, nameString);
        try {
            targetFile.createNewFile();
        } catch (IOException e) {
            return new ExecutionReceipt(command.getCommandType(), INVALID_PATH, targetPathDirectory.getAbsolutePath());
        }
        boolean writingSucceeded = document.writeHealthToFile(targetFile);
        if(!writingSucceeded) {
            return new ExecutionReceipt(command.getCommandType(), FILE_ERROR, targetFile.getAbsolutePath());
        }
        return new ExecutionReceipt(command.getCommandType(), NO_ERROR, null);
    }

    /**
     * Execute the import health command.
     * @param command The command object
     * @return The Error that occurred during execution, NO_ERROR if no error occurred
     */
    private ExecutionReceipt executeImportHealthCommand(Command command) {
        Token[] tokens = command.getTokens();
        String targetPathString = tokens[1].value();
        String sourcePathString = tokens[2].value();
        File targetPath = getTotalPathOfFile(targetPathString);
        File sourcePath = getTotalPathOfFile(sourcePathString);
        CharacterDocument document = (CharacterDocument) getDocumentAtPath(targetPath);
        boolean readingSucceeded = document.readHealthFromFile(sourcePath);
        if(!readingSucceeded) {
            return new ExecutionReceipt(command.getCommandType(), FILE_ERROR, sourcePath.getAbsolutePath());
        }
        return new ExecutionReceipt(command.getCommandType(), NO_ERROR, null);
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
     * Get the document at the specified path.
     * @param path The path
     * @return The document located at the specified path
     */
    private Document getDocumentAtPath(File path) {
        for(Document document : documents) {
            File documentPath = document.getFileLocation();
            if(documentPath.equals(path)) {
                return document;
            }
        }
        return null;
    }

    public boolean isSimulationRunning() {
        return simulationRunning;
    }

    /**
     * Get the root directory path.
     * @return The root directory path as a file object
     */
    public File getRoot() {
        return root;
    }

}
