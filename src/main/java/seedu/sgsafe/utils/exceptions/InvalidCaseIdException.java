package seedu.sgsafe.utils.exceptions;

/**
 * Represents an exception thrown when a case ID provided by the user
 * does not match the required format. Examples include an input with
 * five characters or with special symbols.
 * <p>
 * A valid case ID must be exactly six characters long and contain only
 * hexadecimal digits (0–9, A–F).
 */
public class InvalidCaseIdException extends InvalidCommandException {
    private static final String ERROR_MESSAGE = "The case ID is missing or the format is incorrect.";
    private static final String TIP = "Case ID should be exactly 6 characters of 0-9 or A-F.";

    /**
     * Constructs an {@code InvalidCaseIdException} with a default error message
     * and tip explaining the valid case ID format.
     */
    public InvalidCaseIdException() {
        super(ERROR_MESSAGE,
                TIP);
    }
}
