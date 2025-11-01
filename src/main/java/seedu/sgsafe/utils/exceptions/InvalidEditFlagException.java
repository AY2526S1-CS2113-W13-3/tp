package seedu.sgsafe.utils.exceptions;

import java.util.List;

public class InvalidEditFlagException extends InvalidCommandException{
    private static final String ERROR_MESSAGE = "The case was not edited due to invalid flag(s): ";
    private static final String SEPARATOR = ", ";
    private static final String TIP = "Please see which flags can be used by typing: edit ";

    //@@author shennontay
    private final List<String> invalidFlags;

    /**
     * Constructs an {@code InvalidEditFlagException} with a list of invalid flags and the case ID.
     *
     * @param invalidFlags the list of invalid flag names
     * @param caseId the case ID of the case that the user attempted to edit
     */
    public InvalidEditFlagException(List<String> invalidFlags,  String caseId) {
        super(ERROR_MESSAGE + String.join(SEPARATOR, invalidFlags), TIP + caseId );
        this.invalidFlags = invalidFlags;
    }

    public List<String> getInvalidFlags() {
        return invalidFlags;
    }
}
