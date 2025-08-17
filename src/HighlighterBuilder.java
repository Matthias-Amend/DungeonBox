import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.reader.impl.DefaultHighlighter;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.util.HashMap;

public class HighlighterBuilder {

    public static Highlighter build() {
        return new DefaultHighlighter() {
            @Override
            public AttributedString highlight(LineReader reader, String buffer) {
                AttributedStringBuilder builder = new AttributedStringBuilder();
                Command command = new Command(buffer);
                String[] arguments = command.splitArguments();
                for(String argument : arguments) {
                    ArgumentType argumentType = ArgumentMatcher.matchArgumentType(argument);
                    builder.styled(argumentType.getStyle(), argument);
                    builder.append(" ");
                }
                return builder.toAttributedString();
            }
        };
    }

}
