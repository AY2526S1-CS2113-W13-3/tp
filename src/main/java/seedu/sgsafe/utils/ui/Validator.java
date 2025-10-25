package seedu.sgsafe.utils.ui;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * The `Validator` class provides utility methods for validating flags and inputs.
 * It includes methods to check if flags are valid, if required flags are present,
 * and if a given input is empty.
 */
public class Validator {

    // Logger for logging validation events
    private static final Logger logger = Logger.getLogger(Validator.class.getName());

    //@@author shennontay

    /**
     * Checks if all the flags in the provided map are within the list of valid flags.
     *
     * @param flagValues A map containing flag names as keys and their values as values.
     * @param validFlags A list of valid flag names.
     * @return {@code true} if all flags are valid, {@code false} otherwise.
     */
    public Boolean haveValidFlags(Map<String, String> flagValues, List<String> validFlags) {
        for (String flag : flagValues.keySet()) {
            if (!validFlags.contains(flag)) {
                logger.log(Level.WARNING, "Flags provided are invalid for the command.");
                return false;
            }
        }
        return true;
    }

    //@@author zhengjie2002

    /**
     * Checks if all the required flags are present in the provided map.
     *
     * @param flagValues    A map containing flag names as keys and their values as values.
     * @param requiredFlags A list of required flag names.
     * @return {@code true} if all required flags are present, {@code false} otherwise.
     */
    public Boolean haveAllRequiredFlags(Map<String, String> flagValues, List<String> requiredFlags) {
        for (String requiredFlag : requiredFlags) {
            if (!flagValues.containsKey(requiredFlag)) {
                logger.log(Level.WARNING, "Missing required flags for the command.");
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given input string is empty.
     *
     * @param input The input string to check.
     * @return {@code true} if the input is empty, {@code false} otherwise.
     */
    public Boolean inputIsEmpty(String input) {
        if (input.isEmpty()) {
            logger.log(Level.WARNING, "Command input is empty");
            return true;
        }
        return false;
    }

    //@@author shennontay
    /**
     * Checks whether the provided case ID is a valid 6-character hexadecimal string.
     *
     * @param caseId the case ID to validate
     * @return {@code true} if the case ID is valid; {@code false} otherwise
     */
    public boolean isValidCaseId(String caseId) {
        if (caseId == null) {
            return false;
        }
        // Regex explanation:
        // ^ and $ → start and end of string anchors
        // [0-9A-Fa-f]{6} → exactly 6 characters of 0-9 or A-F (any case)
        return caseId.matches("^[0-9A-Fa-f]{6}$");
    }
}
