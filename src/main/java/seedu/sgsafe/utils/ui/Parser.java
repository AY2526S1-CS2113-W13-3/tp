package seedu.sgsafe.utils.ui;

import seedu.sgsafe.utils.command.AddCommand;
import seedu.sgsafe.utils.command.CaseListingMode;
import seedu.sgsafe.utils.command.Command;
import seedu.sgsafe.utils.command.EditCommand;
import seedu.sgsafe.utils.command.InvalidCommand;
import seedu.sgsafe.utils.command.InvalidCommandType;
import seedu.sgsafe.utils.command.ListCommand;

import seedu.sgsafe.utils.exceptions.EmptyCommandException;
import seedu.sgsafe.utils.exceptions.InvalidEditCommandException;
import seedu.sgsafe.utils.exceptions.UnknownCommandException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Responsible for interpreting raw user input and converting it into structured {@link Command} objects.
 * Acts as the first step in the command execution pipeline by identifying the command type and validating arguments.
 */
public class Parser {

    // Regular expression used to split input into flags and their values. It retains the delimiter.
    private static final String FLAG_SEPARATOR_REGEX = "\\s+(?=--)";

    // Prefix used to identify flags in the input
    private static final String FLAG_PREFIX = "--";

    // List of valid flags to be taken as input from the user
    private static final List<String> VALID_FLAGS = List.of("title", "date", "info", "victim", "officer");

    /**
     * Parses raw user input into a {@link Command} object.
     * <p>
     * This method trims the input, extracts the command keyword, and delegates to specialized parsers
     * based on the keyword. If the input is empty or unrecognized, an {@link InvalidCommand} is returned.
     *
     * @param userInput the full input string entered by the user
     * @return a {@link Command} representing the parsed action, or an {@link InvalidCommand} if parsing fails
     * @throws EmptyCommandException if the input is empty or contains only whitespace
     */
    public static Command parseInput(String userInput) {
        userInput = userInput.strip();
        if (userInput.isEmpty()) {
            throw new EmptyCommandException();
        }

        int spaceIndex = userInput.indexOf(" ");
        String keyword = userInput;
        if (spaceIndex != -1) {
            keyword = userInput.substring(0, spaceIndex).trim();
        }

        String remainder = "";
        if (spaceIndex != -1 && userInput.length() > spaceIndex + 1) {
            remainder = userInput.substring(spaceIndex + 1).trim();
        }

        return switch (keyword) {
        case "list" -> parseListCommand(remainder);
        case "add" -> parseAddCommand(remainder);
        case "edit" -> parseEditCommand(remainder);
        default -> throw new UnknownCommandException();
        };
    }

    /**
     * Parses the {@code list} command and validates its arguments.
     * <p>
     * If the remainder of the input is non-empty, the command is considered invalid.
     * Otherwise, a {@link ListCommand} is returned.
     *
     * @param remainder the portion of the input following the {@code list} keyword
     * @return a valid {@link ListCommand} or an {@link InvalidCommand} if arguments are invalid
     */
    private static Command parseListCommand(String remainder) {
        if (!remainder.isEmpty()) {
            return new InvalidCommand(InvalidCommandType.LIST_COMMAND_INVALID_ARGUMENTS);
        }
        return new ListCommand(CaseListingMode.ALL);
    }

    /**
     * Parses the {@code add} command and validates its arguments.
     * <p>
     * This method extracts flags and their values from the input, ensuring that required fields
     * (title, date, and info) are present. If any validation fails, an {@link InvalidCommand} is returned.
     *
     * @param remainder the portion of the input following the {@code add} keyword
     * @return a valid {@link AddCommand} or an {@link InvalidCommand} if arguments are invalid
     */
    private static Command parseAddCommand(String remainder) {
        if (remainder.isEmpty()) {
            return new InvalidCommand(InvalidCommandType.ADD_COMMAND_NO_ARGUMENTS);
        }

        Map<String, String> flagValues = extractFlagValues(remainder);

        if (flagValues == null) {
            return new InvalidCommand(InvalidCommandType.ADD_COMMAND_INVALID_ARGUMENTS);
        }

        if (validateAddCommandFlags(flagValues)) {
            return new InvalidCommand(InvalidCommandType.ADD_COMMAND_INVALID_ARGUMENTS);
        }

        return new AddCommand(flagValues.get("title"), flagValues.get("date"), flagValues.get("info"),
                flagValues.get("victim"), flagValues.get("officer"));
    }

    /**
     * Validates the presence of required flags for the {@code add} command.
     * <p>
     * The required flags are {@code title}, {@code date}, and {@code info}.
     *
     * @param flagValues a map of flag names with their corresponding values
     * @return {@code true} if any required flag is missing, {@code false} otherwise
     */
    private static Boolean validateAddCommandFlags(Map<String, String> flagValues) {
        return (!flagValues.containsKey("title") || !flagValues.containsKey("date") || !flagValues.containsKey("info"));
    }

    /**
     * Extracts flags and their corresponding values from the input string.
     * <p>
     * The input is split based on the defined flag separator regex, and each part is processed
     * to isolate the flag name and its value. The results are stored in a map.
     *
     * @param input the portion of the input containing flags and their values
     * @return a map of flag names to their corresponding values
     */
    private static Map<String, String> extractFlagValues(String input) {

        String[] parts = input.split(FLAG_SEPARATOR_REGEX);
        Map<String, String> flagValues = new HashMap<>();

        for (String part : parts) {

            String trimmedPart = part.replaceFirst(FLAG_PREFIX, "").trim();
            if (trimmedPart.isEmpty()) {
                return null;
            }

            int spaceIndex = trimmedPart.indexOf(" ");
            if (spaceIndex == -1) {
                return null; //
            }

            String flag = trimmedPart.substring(0, spaceIndex).trim();
            String value = trimmedPart.substring(spaceIndex + 1).trim();

            if (flagValues.containsKey(flag)) {
                return null;
            }
            flagValues.put(flag, value);
        }
        return flagValues;
    }

    /**
     * Parses the 'edit' command input, validates its format, and constructs an EditCommand object.
     * Throws an InvalidEditCommandException if the input is missing, incorrectly formatted, or contains invalid flags.
     */
    private static Command parseEditCommand(String remainder) {
        if (remainder.isEmpty() || !isValidEditCommandInput(remainder)) {
            throw new InvalidEditCommandException("The 'edit' command requires a case number, followed by at least one flag and its value.");
        }

        int firstSpaceIndex = remainder.indexOf(" ");
        if (firstSpaceIndex == -1) {
            throw new InvalidEditCommandException("Missing case number or flags in 'edit' command.");
        }

        String caseNumberString = remainder.substring(0, firstSpaceIndex);
        int caseNumberInteger = Integer.parseInt(caseNumberString);
        String replacements = remainder.substring(firstSpaceIndex + 1).trim();

        Map<String, String> flagValues = extractFlagValues(replacements);

        if (flagValues == null) {
            throw new InvalidEditCommandException("The 'edit' command requires at least one flag and every flag's corresponding value.");
        }

        for (String flag : flagValues.keySet()) {
            if (!VALID_FLAGS.contains(flag)) {
                throw new InvalidEditCommandException("The flag '" + flag + "' is not recognized.");
            }
        }

        return new EditCommand(caseNumberInteger, flagValues);
    }

    /**
     * Checks whether the provided input string matches the valid format for an 'edit' command.
     * The valid format must begin with a case number followed by one or more flags and their values.
     *
     * @param input the user input string to validate
     * @return true if the input matches the required format, false otherwise
     */
    public static boolean isValidEditCommandInput(String input) {
        final String INPUT_PATTERN = "^\\d+\\s+(--\\s*\\w+(?:\\s+\\S+)+)(?:\\s+--\\s*\\w+(?:\\s+\\S+)+)*$";
        return Pattern.matches(INPUT_PATTERN, input.strip());
    }

}
