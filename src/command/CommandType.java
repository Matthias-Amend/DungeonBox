package command;

import command.structure.ArgumentType;

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
        public ArgumentType[] getArgumentStructure() {
            return new ArgumentType[]{ArgumentType.COMMAND};
        }
    },

    CREATE {
        @Override
        public String getCommandID() {
            return "create";
        }

        @Override
        public ArgumentType[] getArgumentStructure() {
            //Argument structure is {create [-p, --path] path [-c, --character, -e, --entry] [-n, --name] "name"}
            return new ArgumentType[]{ArgumentType.COMMAND, ArgumentType.FLAG, ArgumentType.ENTRY, ArgumentType.FLAG, ArgumentType.FLAG, ArgumentType.TEXT};
        }
    };

    /**
     * Get the commandID of the command type.
     * @return The commandID string
     */
    public abstract String getCommandID();

    /**
     * Get the expected structure of the command.
     * @return An array of individual arguments the command expects
     */
    public abstract ArgumentType[] getArgumentStructure();

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
