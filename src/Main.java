import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class Main {

    private static boolean running = true;

    public static void main(String[] args) throws IOException {
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        Highlighter highlighter = HighlighterBuilder.build();
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).highlighter(highlighter).build();
        while(running) {
            String input = reader.readLine("> ");
            if(input.matches(CommandType.EXIT.getCommandID())) {
                running = false;
                break;
            } else {
                terminal.writer().println(input);
            }
        }
        terminal.close();
    }

}
