public record Command(String commandString) {

    /**
     * Get the commandID string of the command string.
     * The commandID string represents what the command is meant to do
     * @return The commandID string
     */
    public String getCommandID() {
        return commandString.split(" ")[0];
    }

    /**
     * Check if the commandID string of the command matches a valid commandID string.
     * @return True if the commandID string matches a valid commandID string, false otherwise
     */
    public boolean hasValidCommandID() {
        String commandID = getCommandID();
        for(CommandType commandType : CommandType.values()) {
            if(commandID.equals(commandType.getCommandID())) {
                return true;
            }
        }
        return false;
    }

}
