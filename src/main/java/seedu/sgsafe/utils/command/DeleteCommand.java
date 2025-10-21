package seedu.sgsafe.utils.command;

import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.utils.ui.Display;

/**
 * Represents a command to edit an existing case in the SGSafe system.
 * This command stores the case number to identify which case to update
 */
public class DeleteCommand extends Command {
    // The case number of the case to delete
    private final int caseNumber;

    // Constructor that sets the case number
    public DeleteCommand(int caseNumber) {
        assert caseNumber > 0;
        this.commandType = CommandType.DELETE;
        this.caseNumber = caseNumber;
    }

    @Override
    public void execute() {
        String caseToDelete = CaseManager.deleteCase(caseNumber);
        Display.printMessage("Case Deleted: " + caseToDelete);
    }
}
