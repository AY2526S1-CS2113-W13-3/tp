package seedu.sgsafe.utils.exceptions;

public class IncorrectFlagException extends InvalidCommandException {
    private static final String ERROR_MESSAGE = "Your command format is incorrect.";
    private static final String USAGE = "There is an incorrect usage of flag. Use flags like --title TITLE";
    private static final String TIP = "Please see which flag is required for this command.";

    public IncorrectFlagException() {
        super(ERROR_MESSAGE, USAGE, TIP);
    }
}
