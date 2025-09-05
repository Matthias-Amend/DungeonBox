import command.*;
import command.output.ErrorPrinter;
import command.output.ExecutionReceipt;
import game.GameState;
import org.jline.builtins.Completers;
import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class Main {

    private static GameState gameState = new GameState();

    private static ErrorPrinter errorPrinter = new ErrorPrinter();

    public static void main(String[] args) throws IOException {
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        Highlighter highlighter = HighlighterBuilder.build();
        ImprovedFileNameCompleter fileNameCompleter = new ImprovedFileNameCompleter();
        LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).highlighter(highlighter).completer(fileNameCompleter).build();
        while(gameState.isSimulationRunning()) {
            fileNameCompleter.setRoot(gameState.getRoot());
            String input = lineReader.readLine("> ");
            Command command = new Command(input);
            ExecutionReceipt receipt = gameState.execute(command, terminal);
            errorPrinter.printErrorMessage(receipt, terminal);
        }
    }

}
