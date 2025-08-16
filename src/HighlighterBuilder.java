import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.reader.impl.DefaultHighlighter;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public class HighlighterBuilder {

    public static Highlighter build() {
        return new DefaultHighlighter() {
            @Override
            public AttributedString highlight(LineReader reader, String buffer) {
                AttributedStringBuilder builder = new AttributedStringBuilder();
                Command command = new Command(buffer);
                if(command.hasValidCommandID()) {
                    builder.styled(AttributedStyle.BOLD.foreground(AttributedStyle.RED), command.getCommandID());
                    builder.append(" ");
                } else {
                    builder.append(command.getCommandID());
                    builder.append(" ");
                }
                String[] arguments = buffer.split(" ");
                for(int bufferIndex = 1; bufferIndex < arguments.length; bufferIndex++) {
                    builder.append(arguments[bufferIndex]);
                    builder.append(" ");
                }
                return builder.toAttributedString();
            }
        };
    }

}
