/**
 * The CommandType enum contains all command types as enums.
 */
public enum CommandType {

    EXIT {
        @Override
        public String getCommandID() {
            return "exit";
        }
    },

    CREATE {
        @Override
        public String getCommandID() {
            return "create";
        }
    },

    SET_ROOT {
        @Override
        public String getCommandID() {
            return "set_root";
        }
    };

    /**
     * Get the commandID string of the command type.
     * @return The commandID string
     */
    public abstract String getCommandID();

}
