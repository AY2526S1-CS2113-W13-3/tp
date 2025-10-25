package seedu.sgsafe.utils.command;

import java.util.Map;

import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.utils.exceptions.CaseNotFoundException;
import seedu.sgsafe.utils.exceptions.IncorrectFlagException;
import seedu.sgsafe.utils.ui.Display;

/**
 * Represents a command to edit an existing case in the SGSafe system.
 * This command stores the case number to identify which case to update
 * and a set of new field values to replace the old ones.
 */
public class EditCommand extends Command {
    // The case number of the case to edit
    private final String caseId;

    // Map of new field values, where the key is the flag (e.g. "title", "date")
    private final Map<String, String> newFlagValues;

    // Constructor that sets the case number and new field values
    public EditCommand(String caseId, Map<String, String> newFlagValues) {
        this.commandType = CommandType.EDIT;
        this.caseId = caseId;
        this.newFlagValues = newFlagValues;
    }

    @Override
    public void execute() {
        try {
            String displayLine = CaseManager.editCase(caseId, newFlagValues);
            Display.printMessage("Case edited:", displayLine);
        } catch (CaseNotFoundException e) {
            Display.printMessage(e.getMessage());
        } catch (IncorrectFlagException e) {
            Display.printMessage("The case was not edited due to invalid flags: " +
                    String.join(", ", e.getInvalidFlags()));
        }
    }
}

