package seedu.sgsafe.utils.exceptions;

/**
 * Exception thrown when the setting command format is invalid.
 * This exception provides detailed error messages to guide the user
 * on the correct format and usage of the setting command.
 */
public class InvalidSettingCommandException extends InvalidCommandException {
    private static final String ERROR_MESSAGE = "Your setting command format is incorrect.";
    private static final String CORRECT_FORMAT_MESSAGE = "The correct format for a setting command is";
    private static final String EXAMPLE_COMMAND = "setting --type TYPE --value VALUE";
    private static final String TIP = "TYPE should either be dateinput or dateoutput or timestampoutput.";
    private static final String TIP2 = "VALUE should be a valid date format pattern that java supports.";

    /**
     * Constructs an InvalidSettingCommandException with a detailed error message.
     *
     * @param isTypeError A boolean indicating whether the error is related to the "type" flag.
     */
    public InvalidSettingCommandException(Boolean isTypeError) {
        super(buildMessage(isTypeError));
    }

    private static String[] buildMessage(Boolean isTypeError) {
        if (isTypeError) {
            return new String[]{TIP};
        }
        return new String[]{ERROR_MESSAGE, CORRECT_FORMAT_MESSAGE, EXAMPLE_COMMAND, TIP, TIP2};
    }
}
