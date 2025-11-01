package seedu.sgsafe.utils.exceptions;

public class InvalidEditCommandException extends InvalidCommandException {
    private static final String ERROR_MESSAGE = "Your edit command format is incorrect. ";
    private static final String TIP = "The 'edit' command requires a valid case ID. " +
            "Flags are optional but must be paired with their new values.";
    private static final String EXAMPLE = "For example, try: \"edit 000000\" or \"edit 000000 --title new title \"";

    public InvalidEditCommandException() {
        super(ERROR_MESSAGE, TIP, EXAMPLE);
    }
}
