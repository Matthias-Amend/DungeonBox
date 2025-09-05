package command;

import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.reader.impl.DefaultHighlighter;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public class HighlighterBuilder {

    public static final AttributedStyle OPTION_STYLE = AttributedStyle.BOLD.foreground(AttributedStyle.YELLOW).bold();
    public static final AttributedStyle STRING_STYLE = AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN);
    public static final AttributedStyle COMMAND_STYLE = AttributedStyle.BOLD.foreground(AttributedStyle.MAGENTA).bold();
    public static final AttributedStyle PATH_STYLE = AttributedStyle.DEFAULT.background(AttributedStyle.GREEN);

    public static Highlighter build() {
        return new DefaultHighlighter() {
            @Override
            public AttributedString highlight(LineReader reader, String buffer) {
                AttributedStringBuilder builder = new AttributedStringBuilder();
                int index = 0;
                while(index < buffer.length()) {
                    char character = buffer.charAt(index);
                    if(Character.isWhitespace(character)) {
                        builder.append(character);
                        index++;
                        continue;
                    }
                    if(character == '-') {
                        AttributedStringBuilder subBuilder = new AttributedStringBuilder();
                        subBuilder.append(String.valueOf(character));
                        index++;
                        if(index < buffer.length()) {
                            if(Character.isDigit(buffer.charAt(index))) {
                                while(index < buffer.length() && (Character.isDigit(buffer.charAt(index)) ||
                                        buffer.charAt(index) == '.')) {
                                    subBuilder.append(buffer.charAt(index));
                                    index++;
                                }
                                builder.append(subBuilder.toString());
                            } else if(Character.isLetter(buffer.charAt(index))) {
                                while(index < buffer.length() && Character.isLetter(buffer.charAt(index))) {
                                    subBuilder.append(buffer.charAt(index));
                                    index++;
                                }
                                builder.styled(OPTION_STYLE, subBuilder.toString());
                            } else {
                                while(index < buffer.length() && !Character.isWhitespace(buffer.charAt(index))) {
                                    subBuilder.append(buffer.charAt(index));
                                    index++;
                                }
                                builder.append(subBuilder.toString());
                            }
                        } else {
                            builder.append(subBuilder.toString());
                        }
                    } else if(character == '"') {
                        AttributedStringBuilder subBuilder = new AttributedStringBuilder();
                        subBuilder.styled(STRING_STYLE, String.valueOf(character));
                        index++;
                        while(index < buffer.length()) {
                            subBuilder.styled(STRING_STYLE, String.valueOf(buffer.charAt(index)));
                            if(buffer.charAt(index) == '"') {
                                index++;
                                break;
                            }
                            index++;
                        }
                        builder.append(subBuilder.toAttributedString());
                    } else if(character == '/') {
                        AttributedStringBuilder subBuilder = new AttributedStringBuilder();
                        subBuilder.styled(PATH_STYLE, String.valueOf(character));
                        index++;
                        while(index < buffer.length()) {
                            if(Character.isWhitespace(buffer.charAt(index)) && buffer.charAt(index - 1) != '\\') {
                                break;
                            }
                            subBuilder.styled(PATH_STYLE, String.valueOf(buffer.charAt(index)));
                            index++;
                        }
                        builder.append(subBuilder.toAttributedString());
                    }
                    else {
                        AttributedStringBuilder subBuilder = new AttributedStringBuilder();
                        subBuilder.append(character);
                        index++;
                        while(index < buffer.length() && !Character.isWhitespace(buffer.charAt(index))) {
                            subBuilder.append(buffer.charAt(index));
                            index++;
                        }
                        String entry = subBuilder.toString();
                        if(CommandType.getCommandType(entry) != null) {
                            builder.styled(COMMAND_STYLE, entry);
                        } else {
                            builder.append(entry);
                        }
                    }
                }
                return builder.toAttributedString();
            }
        };
    }

}
