import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class Main {

    private static boolean running = true;

    public static void main(String[] args) throws IOException {
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        while(running) {
            String input = lineReader.readLine("> ");
            if(input.equalsIgnoreCase("exit")) {
                running = false;
                break;
            }
        }
    }

}
