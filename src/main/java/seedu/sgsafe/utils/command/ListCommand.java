package seedu.sgsafe.utils.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.sgsafe.domain.casefiles.Case;
import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.utils.storage.Storage;
import seedu.sgsafe.utils.ui.Display;

/**
 * Represents a command that lists case files in the SGSafe system.
 * <p>
 * This command supports multiple listing modes to filter cases:
 * {@link CaseListingMode#OPEN_ONLY}, {@link CaseListingMode#CLOSED_ONLY},
 * {@link CaseListingMode#ALL}, and {@link CaseListingMode#DEFAULT}.
 * It also supports verbose mode to display detailed case information.
 */
public class ListCommand extends Command {

    /** The mode that determines which cases to include in the listing. */
    private final CaseListingMode listingMode;

    /** Whether to display cases in verbose (multi-line) format. */
    private final boolean isVerbose;

    /**
     * Constructs a {@code ListCommand} with the specified listing mode and verbosity.
     *
     * @param listingMode the mode used to filter cases for display
     * @param isVerbose   whether to display cases in verbose format
     */
    public ListCommand(CaseListingMode listingMode, boolean isVerbose) {
        this.commandType = CommandType.LIST;
        this.listingMode = listingMode;
        this.isVerbose = isVerbose;
    }

    /**
     * Returns the listing mode associated with this command.
     *
     * @return the {@link CaseListingMode} used for filtering
     */
    public CaseListingMode getListingMode() {
        return this.listingMode;
    }

    /**
     * Executes the command by retrieving and displaying the filtered list of cases.
     * <p>
     * The case list is obtained from {@link CaseManager#getCaseList()},
     * formatted using {@link #getCaseDescriptions(ArrayList)}, and printed via {@link Display#printMessage(String[])}.
     */
    @Override
    public void execute(Storage storage) {
        ArrayList<Case> caseList = CaseManager.getCaseList();
        String[] caseDescriptions = getCaseDescriptions(caseList);
        Display.printMessage(caseDescriptions);
    }

    /**
     * Retrieves and formats the list of case descriptions based on the current {@link CaseListingMode}
     * and verbosity setting.
     * <p>
     * The output array includes:
     * <ul>
     *   <li>Index 0: a summary header indicating the number of matching cases</li>
     *   <li>Subsequent indices:
     *     <ul>
     *       <li>In summary mode: one line per case via {@link Case#getDisplayLine()}</li>
     *       <li>In verbose mode: multiple lines per case via {@link Case#getMultiLineVerboseDisplay()}</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     * @param caseList the full list of cases to filter and format
     * @return an array of formatted strings representing the filtered cases
     */
    String[] getCaseDescriptions(ArrayList<Case> caseList) {
        ArrayList<Case> matchingCases = filterCasesByMode(caseList);
        int count = matchingCases.size();
        List<String> outputLines = new ArrayList<>();

        outputLines.add(generateCaseHeaderMessage(count));
        List<String> formattedCaseLines = formatCases(matchingCases);
        outputLines.addAll(formattedCaseLines);

        return outputLines.toArray(new String[0]);
    }

    /**
     * Formats a list of cases based on the current verbosity setting.
     * <p>
     * In verbose mode, each case is rendered using {@link Case#getMultiLineVerboseDisplay()},
     * prefixed with a divider line. In summary mode, each case is rendered using {@link Case#getDisplayLine()}.
     *
     * @param cases the list of cases to format
     * @return a list of formatted strings representing each case
     */
    private List<String> formatCases(List<Case> cases) {
        List<String> lines = new ArrayList<>();

        for (Case currentCase : cases) {
            if (this.isVerbose) {
                String[] currentLines = currentCase.getMultiLineVerboseDisplay();
                lines.addAll(Arrays.asList(currentLines));
            } else {
                String currentLine = currentCase.getDisplayLine();
                lines.add(currentLine);
            }
        }

        return lines;
    }

    /**
     * Filters the provided list of cases based on the current {@link CaseListingMode}.
     *
     * @param caseList the full list of cases to filter
     * @return a new list containing only the cases that match the filter criteria
     */
    private ArrayList<Case> filterCasesByMode(ArrayList<Case> caseList) {
        return new ArrayList<>(
                caseList.stream()
                        .filter(this::isCaseVisible)
                        .toList()
        );
    }

    /**
     * Determines whether a given case should be included in the listing based on the current mode.
     *
     * @param caseRecord the case to evaluate
     * @return {@code true} if the case matches the filter criteria; {@code false} otherwise
     */
    private boolean isCaseVisible(Case caseRecord) {
        if (caseRecord.isDeleted()) {
            return false;
        }
        return switch (this.listingMode) {
        case OPEN_ONLY -> caseRecord.isOpen();
        case CLOSED_ONLY -> !caseRecord.isOpen();
        case ALL, DEFAULT -> true;
        };
    }

    /**
     * Generates a summary header message based on the number of matching cases and the current {@link CaseListingMode}.
     * <p>
     * The message format varies depending on the count and mode:
     * <ul>
     *   <li>If {@code caseCount == 0}: {@code "You currently have no cases [status]. Add some now!"}</li>
     *   <li>If {@code caseCount == 1}: {@code "You currently have 1 case [status]"}</li>
     *   <li>If {@code caseCount > 1}: {@code "You currently have n cases [status]"}</li>
     * </ul>
     * The status label reflects the listing mode:
     * <ul>
     *   <li>{@code OPEN_ONLY} → "open"</li>
     *   <li>{@code CLOSED_ONLY} → "closed"</li>
     *   <li>{@code ALL} or {@code DEFAULT} → "in total"</li>
     * </ul>
     *
     * @param caseCount the number of cases matching the current listing mode
     * @return a formatted summary message describing the case count and status
     */
    private String generateCaseHeaderMessage(int caseCount) {
        String statusLabel = switch (this.listingMode) {
        case OPEN_ONLY -> "open";
        case CLOSED_ONLY -> "closed";
        case ALL, DEFAULT -> "in total";
        };

        if (caseCount == 0) {
            return "You currently have no cases " + statusLabel + ". Add some now!";
        } else if (caseCount == 1) {
            return "You currently have 1 case " + statusLabel;
        } else {
            return "You currently have " + caseCount + " cases " + statusLabel;
        }
    }
}
