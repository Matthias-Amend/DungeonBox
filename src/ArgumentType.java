import org.jline.utils.AttributedStyle;

public enum ArgumentType {

    COMMAND {
        @Override
        public AttributedStyle getStyle() {
            return AttributedStyle.BOLD.foreground(AttributedStyle.CYAN).bold();
        }
    },

    PARAMETER {
        @Override
        public AttributedStyle getStyle() {
            return AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW);
        }
    },

    TEXT {
        @Override
        public AttributedStyle getStyle() {
            return AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN);
        }
    },

    REGULAR {
        @Override
        public AttributedStyle getStyle() {
            return AttributedStyle.DEFAULT;
        }
    },

    ERROR {
        @Override
        public AttributedStyle getStyle() {
            return AttributedStyle.DEFAULT.foreground(AttributedStyle.RED);
        }
    };

    /**
     * Get the arguments attributed style.
     * This determines how the argument gets highlighted in the console
     * @return The arguments attributed style
     */
    public abstract AttributedStyle getStyle();

}
