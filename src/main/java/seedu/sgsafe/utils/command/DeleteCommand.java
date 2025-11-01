package seedu.sgsafe.utils.command;

import seedu.sgsafe.domain.casefiles.CaseFormatter;
import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.utils.exceptions.CaseNotFoundException;
import seedu.sgsafe.utils.ui.Display;

/**
 * Represents a command to edit an existing case in the SGSafe system.
 * This command stores the case id to identify which case to update
 */
public class DeleteCommand extends Command {
    // The case number of the case to delete
    private final String caseId;

    // Constructor that sets the case number
    public DeleteCommand(String caseId) {
        assert caseId != null;
        this.commandType = CommandType.DELETE;
        this.caseId = caseId;
    }

    /**
     * Getter function for caseId.
     *
     * @return caseId
     */
    public String getCaseId() {
        return caseId;
    }

    @Override
    public void execute() {
        try {
            String caseToDelete = CaseManager.deleteCase(this.caseId);
            Display.printMessage("Case deleted:", generateListTableHeaderMessage(),  caseToDelete);
        } catch (CaseNotFoundException e) {
            Display.printMessage(e.getErrorMessage());
        }
    }

    private String generateListTableHeaderMessage() {
        return String.format(CaseFormatter.SUMMARY_FORMAT_STRING, "STATUS", "CATEGORY", "ID", "DATE", "TITLE");
    }
}
