package seedu.sgsafe.utils.command;

import seedu.sgsafe.domain.casefiles.Case;
import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.utils.exceptions.CaseNotFoundException;
import seedu.sgsafe.utils.ui.Display;

import java.util.List;

/**
 * Shows valid flags for editing a case.
 * User can then use "edit <caseId> --flag value" to actually edit.
 */
public class EditPromptCommand extends Command {
    private final String caseId;

    public EditPromptCommand(String caseId) {
        this.commandType = CommandType.EDIT;
        this.caseId = caseId;
    }

    @Override
    public void execute() {
        try {
            Case targetCase = CaseManager.getCaseById(caseId);
            
            if (targetCase == null) {
                throw new CaseNotFoundException(caseId);
            }
            
            // Get valid flags for this case type
            List<String> validFlags = targetCase.getValidEditFlags();
            
            // Display case info and valid flags
            String flagList = String.join(", --", validFlags);
            Display.printMessage(
                "Case found: " + targetCase.getDisplayLine(),
                "",
                "Fields that can be edited: --" + flagList,
                "",
                "Usage: edit " + caseId + " --<flag> <value>",
                "Example: edit " + caseId + " --location home --date 2024-01-01"
            );
            
        } catch (CaseNotFoundException e) {
            Display.printMessage(e.getMessage());
        }
    }
}
