package command;

import command.structure.ArgumentType;
import command.structure.Flag;
import command.structure.FlagType;

/**
 * The CommandType enum contains all possible types of {@link Command commands} that a user can execute.
 * @author Matthias Amend
 */
public enum CommandType {

    EXIT {
        @Override
        public String getCommandID() {
            return "exit";
        }

        @Override
        public Flag[] getRequiredFlagTypes() {
            return new Flag[0];
        }
    },

    CREATE {
        @Override
        public String getCommandID() {
            return "create";
        }

        @Override
        public Flag[] getRequiredFlagTypes() {
            return new Flag[]{new Flag(FlagType.PATH, false), new Flag(FlagType.NAME, true),
                    new Flag(FlagType.CREATE_TYPE, true)};
        }
    },

    SET_ROOT {
        @Override
        public String getCommandID() {
            return "set_root";
        }

        @Override
        public Flag[] getRequiredFlagTypes() {
            return new Flag[]{new Flag(FlagType.PATH, true)};
        }
    },

    WRITE {
        @Override
        public String getCommandID() {
            return "write";
        }

        @Override
        public Flag[] getRequiredFlagTypes() {
            return new Flag[]{new Flag(FlagType.PATH, true)};
        }
    },

    ADD_LIMB {
        @Override
        public String getCommandID() {
            return "add_limb";
        }

        @Override
        public Flag[] getRequiredFlagTypes() {
            return new Flag[]{new Flag(FlagType.PATH, true), new Flag(FlagType.NAME, true),
                    new Flag(FlagType.HEALTH, true), new Flag(FlagType.VITAL, false)};
        }
    };

    /**
     * Get the commandID of the command type.
     * @return The commandID string
     */
    public abstract String getCommandID();

    /**
     * Get an array of flags required by a specific command
     * @return The required flag array
     */
    public abstract Flag[] getRequiredFlagTypes();

    /**
     * Get the command type of a specific command object.
     * @param command The command
     * @return The command type
     */
    public static CommandType getCommandType(Command command) {
        for(CommandType commandType : CommandType.values()) {
            if(command.getCommandID().equals(commandType.getCommandID())) {
                return commandType;
            }
        }
        return null;
    }

}
