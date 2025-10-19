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
     * Updates an existing {@link Case} with new field values.
     * <p>
     * Finds the case by its {@code caseId} using {@link #getCaseById(String)} and applies
     * the updates from {@code newFlagValues} via {@link Case#update(Map)}.
     * Assumes the case exists (checked by assertion).
     *
     * @param caseId the 6-character hexadecimal case ID
     * @param newFlagValues map of field names to new values
     * @return the updated case’s display line
     */
    public static String editCase(String caseId, Map<String, String> newFlagValues) {
        Case caseToEdit = getCaseById(caseId);
        assert  caseToEdit != null : "Case should not be null";
        caseToEdit.update(newFlagValues);
        return caseToEdit.getDisplayLine();
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
