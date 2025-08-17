import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.reader.impl.DefaultHighlighter;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;

public class HighlighterBuilder {

    public static Highlighter build() {
        return new DefaultHighlighter() {
            @Override
            public AttributedString highlight(LineReader reader, String buffer) {
                AttributedStringBuilder builder = new AttributedStringBuilder();
                Command command = new Command(buffer);
                String[] formattingArray = command.createFormattingArray();
                for(String string : formattingArray) {
                    if(string.isBlank()) {
                        builder.append(string);
                    } else {
                        ArgumentType argumentType = ArgumentMatcher.matchArgumentType(string);
                        builder.styled(argumentType.getStyle(), string);
                    }
                }
                return builder.toAttributedString();
            }
        };
    }

}
