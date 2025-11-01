package seedu.sgsafe.utils.command;

import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.utils.exceptions.CaseNotFoundException;
import seedu.sgsafe.utils.ui.Display;

/**
 * Represents a command to close an existing case in the SGSafe system.
 * This command stores the case ID to identify which case to update
 * <p>
 * When executed, this command marks the specified case as closed using the
 * {@link CaseManager}, and then displays a confirmation message containing
 * the case's details via the {@link Display} class.
 */
public class CloseCommand extends Command {
    private final String caseId;

    public CloseCommand(String caseId) {
        this.commandType = CommandType.CLOSE;
        this.caseId = caseId;
    }

    @Override
    public void execute() {
        try {
            String displayLine = CaseManager.closeCase(caseId);
            Display.printMessage("Case closed:", displayLine);
        } catch (CaseNotFoundException e) {
            Display.printMessage(e.getErrorMessage());
        }
    }
}
