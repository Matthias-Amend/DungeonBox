import command.*;
import game.GameState;
import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class Main {

    private static GameState gameState = new GameState();

    public static void main(String[] args) throws IOException {
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        Highlighter highlighter = HighlighterBuilder.build();
        LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).highlighter(highlighter).build();
        while(gameState.isSimulationRunning()) {
            String input = lineReader.readLine("> ");
            Command command = new Command(input);
            ErrorType error = gameState.execute(command);
            if(error != ErrorType.NO_ERROR) {
                terminal.writer().println(error.toString() + " occurred");
            }
        }
    }

}
