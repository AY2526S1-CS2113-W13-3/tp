package seedu.sgsafe.utils.exceptions;

import java.util.List;

public class IncorrectFlagException extends InvalidCommandException {
    private static final String ERROR_MESSAGE = "Your command format is incorrect.";
    private static final String USAGE = "There is an incorrect usage of flag. Use flags like --title TITLE";
    private static final String TIP = "Please see which flag is required for this command.";

    private final List<String> invalidFlags;

    public IncorrectFlagException() {
        super(ERROR_MESSAGE, USAGE, TIP);
        this.invalidFlags = null;
    }

    /**
     * Constructs an {@code IncorrectFlagException} with a list of invalid flags.
     *
     * @param invalidFlags the list of invalid flag names
     */
    public IncorrectFlagException(List<String> invalidFlags) {
        super(ERROR_MESSAGE, USAGE, TIP);
        this.invalidFlags = invalidFlags;
    }

    public List<String> getInvalidFlags() {
        return invalidFlags;
    }
}
