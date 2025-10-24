package seedu.sgsafe.utils.command;

import seedu.sgsafe.domain.casefiles.Case;
import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.utils.storage.Storage;
import seedu.sgsafe.utils.ui.Display;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Represents a command to add a new case.
 * This command contains details about the case, including its title, date, information,
 * and optionally the victim and officer involved.
 */
public class AddCommand extends Command {

    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());

    // Format string for generating case IDs
    private static final String CASE_ID_FORMAT = "%06x";

    // Title of the case
    private final String caseTitle;

    // Date of the case
    private final String caseDate;

    // Additional information about the case
    private final String caseInfo;

    // Name of the victim involved in the case (optional)
    private final String caseVictim;

    // Name of the officer handling the case (optional)
    private final String caseOfficer;

    /**
     * Constructs an AddCommand with the specified case details.
     *
     * @param caseTitle   The title of the case. Cannot be null.
     * @param caseDate    The date of the case. Cannot be null.
     * @param caseInfo    Additional information about the case. Cannot be null.
     * @param caseVictim  The name of the victim involved in the case. Can be null.
     * @param caseOfficer The name of the officer handling the case. Can be null.
     */
    public AddCommand(String caseTitle, String caseDate, String caseInfo, String caseVictim, String caseOfficer) {
        this.commandType = CommandType.ADD;
        this.caseTitle = caseTitle;
        this.caseDate = caseDate;
        this.caseInfo = caseInfo;
        this.caseVictim = caseVictim;
        this.caseOfficer = caseOfficer;
        logger.log(Level.INFO, "AddCommand created");
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public String getCaseDate() {
        return caseDate;
    }

    public String getCaseInfo() {
        return caseInfo;
    }

    public String getCaseVictim() {
        return caseVictim;
    }

    public String getCaseOfficer() {
        return caseOfficer;
    }

    // @@author xelisce

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
        int raw = CaseManager.getCaseListSize();
        return String.format(CASE_ID_FORMAT, raw); // zero-padded 6-digit hex
    }

    // @@author zhengjie2002
    @Override
    public void execute(Storage storage) {
        String id = generateHexId();
        Case newCase = new Case(id, caseTitle, caseDate, caseInfo, caseVictim, caseOfficer);
        CaseManager.addCase(newCase);
        Display.printMessage("New case added:", newCase.getDisplayLine());
        storage.saveToFile();
    }
}
