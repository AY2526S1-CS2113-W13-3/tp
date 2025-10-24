package seedu.sgsafe.utils.command;

import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.utils.exceptions.CaseNotFoundException;
import seedu.sgsafe.utils.storage.Storage;
import seedu.sgsafe.utils.ui.Display;

/**
 * Represents a command to reopen a closed case in the SGSafe system.
 * This command stores the case ID to identify which case to update
 * <p>
 * When executed, this command marks the specified case as open using the
 * {@link CaseManager}, and then displays a confirmation message containing
 * the case's details via the {@link Display} class.
 */
public class OpenCommand extends Command {
    private final String caseId;

    public OpenCommand(String caseId) {
        this.commandType = CommandType.OPEN;
        this.caseId = caseId;
    }

    @Override
    public void execute(Storage storage) {
        try {
            String displayLine = CaseManager.openCase(caseId);
            Display.printMessage("Case reopened:", displayLine);
            storage.saveToFile();
        } catch (CaseNotFoundException e) {
            Display.printMessage(e.getMessage());
        }
    }
}
