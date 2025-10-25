package seedu.sgsafe.utils.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import seedu.sgsafe.domain.casefiles.Case;
import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.utils.exceptions.IncorrectFlagException;

public class EditCommandTest {

    private Case sampleCase;

    @BeforeEach
    void setUp() {
        CaseManager.getCaseList().clear();

        sampleCase = new Case("000001", "Robbery", "2025-10-10", "Suspect armed", "Alice", "Officer Tan") {};
        CaseManager.addCase(sampleCase);
    }

    @Test
    void execute_validEdit_updatesCaseSuccessfully() {
        Map<String, String> updates = Map.of("title", "Updated Robbery", "officer", "Officer Lee");
        EditCommand command = new EditCommand("000001", updates);

        command.execute();

        Case editedCase = CaseManager.getCaseById("000001");
        assertEquals("Updated Robbery", editedCase.getTitle());
        assertEquals("Officer Lee", editedCase.getOfficer());
    }

    @Test
    void execute_invalidCaseId_printsCaseNotFoundMessage() {
        Map<String, String> updates = Map.of("title", "New Title");
        EditCommand command = new EditCommand("999999", updates);

        assertDoesNotThrow(command::execute);
    }

    @Test
    void execute_invalidFlags_doesNotUpdateCase() {
        Map<String, String> updates = Map.of("wrongFlag", "Something");
        EditCommand command = new EditCommand("000001", updates);

        assertDoesNotThrow(command::execute);
        Case unchangedCase = CaseManager.getCaseById("000001");
        assertEquals("Robbery", unchangedCase.getTitle());
    }

    @Test
    void editCase_invalidFlags_throwsIncorrectFlagException() {
        Map<String, String> updates = Map.of("invalidFlag", "New Value");
        assertThrows(IncorrectFlagException.class, () -> CaseManager.editCase("000001", updates));
    }
}
