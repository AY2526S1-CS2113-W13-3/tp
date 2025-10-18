package seedu.sgsafe.domain.casefiles;

import java.util.ArrayList;
import java.util.Map;

import seedu.sgsafe.utils.command.CloseCommand;
import seedu.sgsafe.utils.command.DeleteCommand;
import seedu.sgsafe.utils.ui.Display;

import seedu.sgsafe.utils.exceptions.IndexOutOfBoundsException;

/**
 * Manages the collection of {@link Case} objects in the SGSafe system.
 * Provides functionality to store, retrieve, and display case records.
 */
public class CaseManager {

    /**
     * The central list of case records maintained by the application.
     * Each {@link Case} represents a single incident or report.
     */
    private static ArrayList<Case> caseList = new ArrayList<>();

    public static int getCaseListSize() {
        return caseList.size();
    }

    public static ArrayList<Case> getCaseList() {
        return caseList;
    }

    /**
     * Generates a unique 6-character hexadecimal ID for a new case.
     * <p>
     * The ID is derived from the current size of {@code caseList}, formatted as a
     * zero-padded lowercase hexadecimal string. This ensures compact, readable,
     * and collision-free identifiers as long as cases are not removed or reordered.
     * <p>
     * Example outputs:
     * <ul>
     *   <li>{@code 000000} — first case</li>
     *   <li>{@code 00000a} — tenth case</li>
     *   <li>{@code 0000ff} — 256th case</li>
     * </ul>
     *
     * @return a 6-character hexadecimal string representing the new case ID
     */
    private static String generateHexId() {
        int raw = caseList.size();
        return String.format("%06x", raw); // zero-padded 6-digit hex
    }

    /**
     * Adds a new case to the case list.
     *
     * @param newCase the {@link Case} object to be added
     */
    public static void addCase(Case newCase) {
        assert newCase != null : "AddCommand should not be null";
        caseList.add(newCase);
    }

    /**
     * Closes an existing case in the case list using the case number in the CloseCommand.
     * The method checks if the case number is valid, and throws an IndexOutOfBoundsException if
     * the case number is invalid
     *
     * @param command the {@link CloseCommand} containing the case number to be closed
     * @throws IndexOutOfBoundsException if the provided case number is invalid
     */
    public static void closeCase(CloseCommand command) {
        int caseNumber = command.getCaseNumber();
        if (caseNumber < 1 || caseNumber > caseList.size()) {
            throw new IndexOutOfBoundsException();
        }
        int caseIndex = caseNumber - 1;
        Case caseToClose = caseList.get(caseIndex);
        caseToClose.setClosed();
        Display.printMessage("Case closed:\n" + caseToClose.getDisplayLine());
    }

    /**
     * Finds and returns a {@link Case} object from the case list using its unique ID.
     *
     * @param id the hexadecimal ID of the case to find
     * @return the Case with the matching ID, or null if not found
     */
    public static Case getCaseById(String id) {
        return caseList.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Edits the details of an existing {@link Case} identified by its unique case ID.
     * <p>
     * This method first retrieves the corresponding {@link Case} object using
     * {@link #getCaseById(String)}. If a matching case is found, it applies the updates
     * provided in the {@code newFlagValues} map by calling {@link Case#update(Map)}.
     * Only the fields included in the map will be modified; all others remain unchanged.
     *
     * @param caseId        the hexadecimal ID of the case to edit
     * @param newFlagValues a map containing field names and their updated values
     *                      (e.g., "title", "date", "info", "victim", "officer")
     */
    public static void editCase(String caseId, Map<String, String> newFlagValues) {
        Case caseToEdit = getCaseById(caseId);
        if (caseToEdit == null) {
            Display.printMessage("Invalid case ID, please try again.");
            return;
        }
        caseToEdit.update(newFlagValues);
        Display.printMessage("Case edited:\n" + caseToEdit.getDisplayLine());
    }

    /**
     * Deletes an existing case in the case list using the case number in the DeleteCommand.
     * The method checks if the case number is valid, and throws an IndexOutOfBoundsException if
     * the case number is invalid
     *
     * @param command the {@link DeleteCommand} containing the case number.
     */
    public static void deleteCase(DeleteCommand command) {
        int caseNumber = command.getCaseNumber();
        if (caseNumber < 1 || caseNumber > caseList.size()) {
            throw new IndexOutOfBoundsException();
        }
        int caseIndex = caseNumber - 1;
        Case targetCase = caseList.get(caseIndex);
        Display.printMessage("Case deleted:\n" + targetCase.getDisplayLine());
        caseList.remove(caseIndex);
    }
}
