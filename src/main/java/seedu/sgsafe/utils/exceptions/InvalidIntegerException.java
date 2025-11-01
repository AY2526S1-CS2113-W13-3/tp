package seedu.sgsafe.utils.exceptions;

public class InvalidIntegerException extends InvalidCommandException {
    private static final String STARTING_MESSAGE = "The input for flag '";
    private static final String ENDING_MESSAGE = "' has to be 0 or a positive integer.";

    /**
     * Constructs an {@code InvalidCaseIdException} with a default error message
     * and tip explaining the valid case ID format.
     */
    public InvalidIntegerException(String flag) {
        super(STARTING_MESSAGE + flag + ENDING_MESSAGE);
    }
}
