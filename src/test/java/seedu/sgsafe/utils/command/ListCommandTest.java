package seedu.sgsafe.utils.command;

// @@author xelisce
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.sgsafe.domain.casefiles.Case;
import seedu.sgsafe.domain.casefiles.CaseManager;
import seedu.sgsafe.domain.casefiles.type.OthersCase;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Unit tests for {@link ListCommand}, verifying correct filtering, formatting,
 * verbosity, and edge case handling across all {@link CaseListingMode} variants.
 */
class ListCommandTest {

    private ArrayList<Case> caseList;

    @BeforeEach
    void resetCaseList() throws Exception {
        Field caseListField = CaseManager.class.getDeclaredField("caseList");
        caseListField.setAccessible(true);
        caseList = (ArrayList<Case>) caseListField.get(null);
        caseList.clear();
    }

    @Test
    void list_withNoCases_returnsHeaderOnly() {
        ListCommand command = new ListCommand(CaseListingMode.ALL, false);
        String[] output = command.getCaseDescriptions(caseList);

        assertEquals(1, output.length);
        assertEquals("You currently have no cases in total. Add some now!", output[0]);
    }

    @Test
    void list_withOneOpenCase_returnsCorrectHeaderAndLine() {
        LocalDate date = LocalDate.of(2025, 10, 1);
        caseList.add(new OthersCase("000001", "Robbery", date, "Masked suspect", "John Doe", "Officer Tan"));

        ListCommand command = new ListCommand(CaseListingMode.OPEN_ONLY, false);
        String[] output = command.getCaseDescriptions(caseList);

        assertEquals(2, output.length);
        assertEquals("You currently have 1 case open", output[0]);
        assertTrue(output[1].contains("[O]"));
        assertTrue(output[1].contains("Robbery"));
    }

    @Test
    void list_withMixedCases_filtersByStatusCorrectly() {
        LocalDate date = LocalDate.of(2025, 10, 1);
        caseList.add(new OthersCase("000001", "Robbery", date, "Masked suspect", "John Doe", "Officer Tan"));
        caseList.add(new OthersCase("000002", "Fraud", date, "Email scam", "Jane Doe", "Officer Lim"));

        Case closedCase = new OthersCase("000003", "Trespass", date,
                "Unauthorized entry", "Jake Doe", "Officer Ong");
        closedCase.setClosed();
        caseList.add(closedCase);

        String[] openOutput = new ListCommand(CaseListingMode.OPEN_ONLY, false).getCaseDescriptions(caseList);
        assertEquals("You currently have 2 cases open", openOutput[0]);
        assertEquals(3, openOutput.length);
        assertTrue(openOutput[1].contains("Robbery"));
        assertTrue(openOutput[2].contains("Fraud"));

        String[] closedOutput = new ListCommand(CaseListingMode.CLOSED_ONLY, false).getCaseDescriptions(caseList);
        assertEquals("You currently have 1 case closed", closedOutput[0]);
        assertEquals(2, closedOutput.length);
        assertTrue(closedOutput[1].contains("Trespass"));
        assertTrue(closedOutput[1].contains("[C]"));
    }

    @Test
    void list_withAllMode_returnsAllCases() {
        LocalDate date = LocalDate.of(2025, 10, 1);
        caseList.add(new OthersCase("000001", "Robbery", date, "Masked suspect", "John Doe", "Officer Tan"));
        caseList.add(new OthersCase("000002", "Fraud", date, "Email scam", "Jane Doe", "Officer Lim"));

        Case closedCase = new OthersCase("000003", "Trespass", date,
                "Unauthorized entry", "Jake Doe", "Officer Ong");
        closedCase.setClosed();
        caseList.add(closedCase);

        String[] output = new ListCommand(CaseListingMode.ALL, false).getCaseDescriptions(caseList);
        assertEquals("You currently have 3 cases in total", output[0]);
        assertEquals(4, output.length);
        assertTrue(output[1].contains("Robbery"));
        assertTrue(output[2].contains("Fraud"));
        assertTrue(output[3].contains("Trespass"));
    }

    @Test
    void list_withDefaultMode_behavesLikeAll() {
        LocalDate date = LocalDate.of(2025, 10, 1);
        caseList.add(new OthersCase("000001", "Robbery", date, "Masked suspect", "John Doe", "Officer Tan"));
        caseList.add(new OthersCase("000002", "Fraud", date, "Email scam", "Jane Doe", "Officer Lim"));

        String[] output = new ListCommand(CaseListingMode.DEFAULT, false).getCaseDescriptions(caseList);
        assertEquals("You currently have 2 cases in total", output[0]);
        assertEquals(3, output.length);
        assertTrue(output[1].contains("Robbery"));
        assertTrue(output[2].contains("Fraud"));
    }

    @Test
    void list_verboseMode_includesDetailedInfo() {
        LocalDate date = LocalDate.of(2025, 10, 1);
        caseList.add(new OthersCase("000001", "Robbery", date, "Masked suspect", "John Doe", "Officer Tan"));

        ListCommand command = new ListCommand(CaseListingMode.ALL, true);
        String[] output = command.getCaseDescriptions(caseList);

        assertTrue(output[1].startsWith("======== CASE ID 000001 ========"));
        assertTrue(output[2].startsWith("Status  : Open"));
        assertTrue(output[3].contains("Robbery"));
        assertTrue(output[4].contains("01/10/2025"));
        assertTrue(output[5].contains("Masked suspect"));
        assertTrue(output[6].contains("John Doe"));
        assertTrue(output[7].contains("Officer Tan"));
    }

    @Test
    void list_summaryMode_omitsDetailedInfo() {
        LocalDate date = LocalDate.of(2025, 10, 1);
        caseList.add(new OthersCase("000001", "Robbery", date, "Masked suspect", "John Doe", "Officer Tan"));

        ListCommand command = new ListCommand(CaseListingMode.ALL, false);
        String[] output = command.getCaseDescriptions(caseList);

        assertEquals(2, output.length);
        assertTrue(output[1].contains("Robbery"));
        assertTrue(output[1].contains("John Doe"));
        assertTrue(output[1].contains("Officer Tan"));
    }

    @Test
    void list_verboseMixed_includesDetails() {
        LocalDate date = LocalDate.of(2025, 10, 1);
        caseList.add(new OthersCase("000001", "Robbery", date, "Masked suspect", "John Doe", "Officer Tan"));
        caseList.add(new OthersCase("000002", "Fraud", date, "Email scam", "Jane Doe", "Officer Lim"));

        Case closedCase = new OthersCase("000003", "Trespass", date,
                "Unauthorized entry", "Jake Doe", "Officer Ong");
        closedCase.setClosed();
        caseList.add(closedCase);

        ListCommand command = new ListCommand(CaseListingMode.ALL, true);
        String[] output = command.getCaseDescriptions(caseList);

        boolean foundEmailScam = false;
        boolean foundUnauthorizedEntry = false;

        for (String line : output) {
            if (line.contains("Email scam")) {
                foundEmailScam = true;
            }
            if (line.contains("Unauthorized entry")) {
                foundUnauthorizedEntry = true;
            }
        }

        assertTrue(foundEmailScam, "Expected 'Email scam' to appear in verbose output");
        assertTrue(foundUnauthorizedEntry, "Expected 'Unauthorized entry' to appear in verbose output");
    }

    @Test
    void list_summaryMixed_omitsDetails() {
        LocalDate date = LocalDate.of(2025, 10, 1);
        caseList.add(new OthersCase("000001", "Robbery", date, "Masked suspect", "John Doe", "Officer Tan"));
        caseList.add(new OthersCase("000002", "Fraud", date, "Email scam", "Jane Doe", "Officer Lim"));

        Case closedCase = new OthersCase("000003", "Trespass", date,
                "Unauthorized entry", "Jake Doe", "Officer Ong");
        closedCase.setClosed();
        caseList.add(closedCase);

        ListCommand command = new ListCommand(CaseListingMode.ALL, false);
        String[] output = command.getCaseDescriptions(caseList);

        assertEquals(4, output.length);
        assertFalse(output[1].contains("Masked suspect"));
        assertFalse(output[2].contains("Email scam"));
        assertFalse(output[3].contains("Unauthorized entry"));
    }

    @Test
    void list_verboseMode_truncatesLongInfo() {
        LocalDate date = LocalDate.of(2025, 10, 1);
        String longInfo = "X".repeat(150);
        caseList.add(new OthersCase("000004", "Forgery", date, longInfo, "Alex", "Officer Lee"));

        ListCommand command = new ListCommand(CaseListingMode.ALL, true);
        String[] output = command.getCaseDescriptions(caseList);

        boolean foundTruncated = false;
        for (String line : output) {
            if (line.startsWith("Info    : ")) {
                assertTrue(line.endsWith("..."));
                foundTruncated = true;
            }
        }
        assertTrue(foundTruncated);
    }

    @Test
    void list_verboseMode_handlesMissingFieldsGracefully() {
        LocalDate date = LocalDate.of(2025, 10, 1);
        caseList.add(new OthersCase("000005", "Vandalism", date, "Graffiti", null, null));

        ListCommand command = new ListCommand(CaseListingMode.ALL, true);
        String[] output = command.getCaseDescriptions(caseList);

        assertTrue(output[6].startsWith("Victim  : "));
        assertTrue(output[7].startsWith("Officer : "));
    }
}
