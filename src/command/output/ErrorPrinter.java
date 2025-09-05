package command.output;

import command.CommandType;
import command.ErrorType;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.io.File;

public class ErrorPrinter {

    private static final AttributedStyle ERROR_STYLE = AttributedStyle.BOLD.foreground(AttributedStyle.RED).bold();
    private static final AttributedStyle PATH_STYLE = AttributedStyle.DEFAULT.background(AttributedStyle.GREEN);
    private static final AttributedStyle COMMAND_STRUCTURE_STYLE = AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN);
    private static final AttributedStyle OPTION_STYLE = AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW);
    private static final AttributedStyle COMMAND_STYLE = AttributedStyle.DEFAULT.foreground(AttributedStyle.MAGENTA);

    public void printErrorMessage(ExecutionReceipt receipt, Terminal terminal) {
        AttributedStringBuilder errorBuilder = new AttributedStringBuilder();
        ErrorType errorType = receipt.error();
        switch (errorType) {
            case INVALID_PATH, INVALID_DOCUMENT, FILE_ERROR -> {
                errorBuilder.styled(ERROR_STYLE, errorType + " error occurred:" + "\n");
                errorBuilder.append("The following file path is not valid! Please verify that the required files meet the commands specifications.\n");
                errorBuilder.append("\t").styled(COMMAND_STRUCTURE_STYLE, CommandType.getCommandStructureString(receipt.commandType()) + "\n");
                errorBuilder.append("\t").styled(PATH_STYLE, receipt.errorString()).append(" is not a valid file path");
                terminal.writer().println(errorBuilder.toAttributedString().toAnsi());
            }
            case INVALID_OPTION -> {
                errorBuilder.styled(ERROR_STYLE, errorType + "error occurred:" + "\n");
                errorBuilder.append("One or more of the mentioned Options are not valid! Please make sure that the option is supported by the command.\n");
                errorBuilder.append("\t").styled(COMMAND_STRUCTURE_STYLE, CommandType.getCommandStructureString(receipt.commandType()) + "\n");
                errorBuilder.append("\t").styled(OPTION_STYLE, "[" + receipt.errorString() + "]");
                errorBuilder.append(" is not a valid option for the ")
                        .styled(COMMAND_STYLE, receipt.commandType().toString()).append(" command");
                terminal.writer().println(errorBuilder.toAttributedString().toAnsi());
            }
            case CHARACTER_ERROR -> {
                errorBuilder.styled(ERROR_STYLE, errorType + " error occurred:" + "\n");
                errorBuilder.append("The targeted character document is not valid! Make sure that you're referencing the correct character document.\n");
                errorBuilder.append("\t").styled(COMMAND_STRUCTURE_STYLE, CommandType.getCommandStructureString(receipt.commandType()) + "\n");
                terminal.writer().println(errorBuilder.toAttributedString().toAnsi());
            }
            case INCORRECT_ARGUMENTS -> {
                errorBuilder.styled(ERROR_STYLE, errorType + " error occurred:" + "\n");
                errorBuilder.append("The amount of entered arguments is invalid for the called command! Please make sure that the command structure is correct.\n");
                errorBuilder.append("\t").styled(COMMAND_STRUCTURE_STYLE, CommandType.getCommandStructureString(receipt.commandType()) + "\n");
                terminal.writer().println(errorBuilder.toAttributedString().toAnsi());
            }
            case INVALID_COMMAND -> {
                errorBuilder.styled(ERROR_STYLE, errorType + " error occurred:" + "\n");
                errorBuilder.append("The entered command is not valid! Make sure you're calling a valid command.\n");
                errorBuilder.append("\t").styled(COMMAND_STYLE, "[" + receipt.errorString() + "]").append(" is not a valid command!");
                terminal.writer().println(errorBuilder.toAttributedString().toAnsi());
            }
        }
    }

}
