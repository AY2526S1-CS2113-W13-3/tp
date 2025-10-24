package seedu.sgsafe.utils.exceptions;

public class InvalidCharacterException extends InvalidCommandException {
    private static final String INFO = "You have entered an invalid character.";
    private static final String TIP = "You cannot use '|' in your input.";
    public InvalidCharacterException() {
        super(INFO, TIP);
    }
}
