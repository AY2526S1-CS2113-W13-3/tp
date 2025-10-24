package seedu.sgsafe.utils.command;

import seedu.sgsafe.utils.storage.Storage;

public abstract class Command {
    protected CommandType commandType;

    public CommandType getCommandType() {
        return commandType;
    }

    public String[] getErrorMessage() {
        return null;
    }

    // Abstract method to be implemented by subclasses to execute the command
    public abstract void execute(Storage storage);
}
