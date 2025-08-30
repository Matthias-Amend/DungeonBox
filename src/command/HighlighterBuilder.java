package command;

import command.structure.ArgumentMatcher;
import command.structure.ArgumentType;
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
                String[] formattingArray = command.getFormattingArray();
                for(String argument : formattingArray) {
                    if(argument.isBlank()) {
                        builder.append(argument);
                    } else {
                        ArgumentType argumentType = ArgumentMatcher.match(argument);
                        switch (argumentType) {
                            case COMMAND -> {
                                builder.styled(AttributedStyle.DEFAULT.foreground(AttributedStyle.MAGENTA), argument);
                            }
                            case FLAG -> {
                                builder.styled(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW), argument);
                            }
                            case TEXT -> {
                                builder.styled(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN), argument);
                            }
                            default -> {
                                builder.append(argument);
                            }
                        }
                    }
                }
                return builder.toAttributedString();
            }
        };
    }

}
