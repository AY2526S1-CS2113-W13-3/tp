package seedu.sgsafe.utils.command;

import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.utils.exceptions.CaseNotFoundException;
import seedu.sgsafe.utils.ui.Display;

public class ReadCommand extends Command {
    private final String caseId;

    public ReadCommand(String caseId) {
        this.commandType = CommandType.READ;
        this.caseId = caseId;
    }

    @Override
    public void execute() {
        String [] display = null;
        try {
            display = CaseManager.readCase(caseId);
        } catch (CaseNotFoundException e) {
            Display.printMessage(e.getErrorMessage());
        }
        if (display != null) {
            Display.printMessage(display);
        }
    }
}
