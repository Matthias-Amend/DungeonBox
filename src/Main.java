import command.Command;
import command.CommandChecker;
import command.CommandType;
import command.ErrorType;
import command.structure.ArgumentMatcher;
import game.GameState;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.List;

public class Main {

    private static GameState gameState = new GameState();

    public static void main(String[] args) throws IOException {
        /*Terminal terminal = TerminalBuilder.builder().system(true).build();
        LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        while(gameState.isSimulationRunning()) {
            String input = lineReader.readLine("> ");
            Command command = new Command(input);
            gameState.execute(command);
        }*/
    }

}
