package seedu.sgsafe.utils.command;

import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.utils.exceptions.CaseNotFoundException;
import seedu.sgsafe.utils.storage.Storage;
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

    @Override
    public void execute(Storage storage) {
        try {
            String caseToDelete = CaseManager.deleteCase(this.caseId);
            Display.printMessage("Case Deleted: " + caseToDelete);
        } catch (CaseNotFoundException e) {
            Display.printMessage(e.getMessage());
        }
    }
}
