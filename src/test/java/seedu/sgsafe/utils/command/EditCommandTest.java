package seedu.sgsafe.utils.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Map;

import seedu.sgsafe.domain.casefiles.Case;
import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.domain.casefiles.type.violent.RobberyCase;
import seedu.sgsafe.utils.exceptions.InvalidEditFlagException;

public class EditCommandTest {

    private Case sampleCase;

    @BeforeEach
    void setUp() {
        CaseManager.getCaseList().clear();
        LocalDate date = LocalDate.of(2025, 10, 10);
        sampleCase = new RobberyCase("000001", "Robbery", date, "Suspect armed", "Alice", "Officer Tan") {};
        CaseManager.addCase(sampleCase);
    }

    @Test
    void execute_validEdit_updatesCaseSuccessfully() {
        Map<String, Object> updates = Map.of("title", "Updated Robbery", "officer", "Officer Lee");
        EditCommand command = new EditCommand("000001", updates);

        command.execute();

        Case editedCase = CaseManager.getCaseById("000001");
        assertEquals("Updated Robbery", editedCase.getTitle());
        assertEquals("Officer Lee", editedCase.getOfficer());
    }

    @Test
    void execute_invalidCaseId_printsCaseNotFoundMessage() {
        Map<String, Object> updates = Map.of("title", "New Title");
        EditCommand command = new EditCommand("999999", updates);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            command.execute();
        } finally {
            System.setOut(originalOut);
        }

        String output = outputStream.toString().trim();
        assertTrue(output.contains("No case found with ID: 999999"));
    }

    @Test
    void execute_invalidFlags_doesNotUpdateCase() {
        Map<String, Object> updates = Map.of("wrongFlag", "Something");
        EditCommand command = new EditCommand("000001", updates);

        assertDoesNotThrow(command::execute);
        Case unchangedCase = CaseManager.getCaseById("000001");
        assertEquals("Robbery", unchangedCase.getTitle());
    }

    @Test
    void execute_invalidFlags_throwsInvalidEditFlagException() {
        Map<String, Object> updates = Map.of("invalidFlag", "New Value");
        assertThrows(InvalidEditFlagException.class, () -> CaseManager.editCase("000001", updates));
    }

    @Test
    void execute_negativeInt_throwsException() {
        Map<String, Object> updates = Map.of("date", -20231010);
        assertThrows(Exception.class, () -> CaseManager.editCase("000001", updates));
    }
}
