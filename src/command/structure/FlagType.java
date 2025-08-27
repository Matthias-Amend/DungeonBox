package command.structure;

public enum FlagType {

    PATH {
        @Override
        public String[] getFlagStrings() {
            return new String[]{"-p", "--path"};
        }
    },

    CREATE_TYPE {
        @Override
        public String[] getFlagStrings() {
            return new String[] {"-c", "--character", "-e", "--entry"};
        }
    },

    NAME {
        @Override
        public String[] getFlagStrings() {
            return new String[]{"-n", "--name"};
        }
    };

    /**
     * Get an array of valid flag strings.
     * If the user enters a flag and any of the flag strings matches the user string, the flag is considered valid
     * @return An array of flag strings
     */
    public abstract String[] getFlagStrings();

    /**
     * Compare a user flag strings to all possible flag strings and return the matching flag type.
     * @param flagString The user flag string
     * @return The flag type of the user flag string, if none match, return null
     */
    public static FlagType getFlagType(String flagString) {
        for(FlagType flagType : FlagType.values()) {
            for(String currentFlagString : flagType.getFlagStrings()) {
                if(flagString.equals(currentFlagString)) {
                    return flagType;
                }
            }
        }
        return null;
    }

}
