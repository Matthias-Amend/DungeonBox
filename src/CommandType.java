/**
 * The CommandType enum contains all command types as enums.
 */
public enum CommandType {
    EXIT {
        @Override
        public String getCommandID() {
            return "exit";
        }
    };

    /**
     * Get the commandID string of the command type.
     * @return The commandID string
     */
    public abstract String getCommandID();

}
