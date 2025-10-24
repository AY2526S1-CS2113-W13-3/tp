package seedu.sgsafe.utils.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.Map;



import seedu.sgsafe.utils.command.CaseListingMode;
import seedu.sgsafe.utils.command.Command;
import seedu.sgsafe.utils.command.CommandType;
import seedu.sgsafe.utils.command.ListCommand;
import seedu.sgsafe.utils.command.AddCommand;
import seedu.sgsafe.utils.command.EditCommand;
import seedu.sgsafe.utils.exceptions.IncorrectFlagException;
import seedu.sgsafe.utils.exceptions.EmptyCommandException;
import seedu.sgsafe.utils.exceptions.InputLengthExceededException;
import seedu.sgsafe.utils.exceptions.DuplicateFlagException;
import seedu.sgsafe.utils.exceptions.InvalidAddCommandException;
import seedu.sgsafe.utils.exceptions.InvalidCaseIdException;
import seedu.sgsafe.utils.exceptions.InvalidEditCommandException;
import seedu.sgsafe.utils.exceptions.InvalidCloseCommandException;

import seedu.sgsafe.utils.exceptions.InvalidOpenCommandException;
import seedu.sgsafe.utils.exceptions.UnknownCommandException;

/**
 * Unit tests for {@link Parser}, verifying correct command parsing and exception handling.
 */
class ParserTest {

    // ----------- TESTS FOR GENERAL INVALID COMMANDS ----------- //

    @Test
    void parseInput_invalidKeyword_throwsUnknownCommandException() {
        assertThrows(UnknownCommandException.class, () -> Parser.parseInput("foobar"));
    }

    @Test
    void parseInput_fooList_throwsUnknownCommandException() {
        assertThrows(UnknownCommandException.class, () -> Parser.parseInput("foo list"));
    }

    @Test
    void parseInput_emptyInput_throwsEmptyCommandException() {
        assertThrows(EmptyCommandException.class, () -> Parser.parseInput("   "));
    }

    // ----------- TESTS FOR LIST COMMANDS ----------- //

    @Test
    void parseInput_listWithWhitespace_returnsListCommand() {
        Command command = Parser.parseInput("  list  ");
        assertEquals(CommandType.LIST, command.getCommandType());
        assertInstanceOf(ListCommand.class, command);
        assertEquals(CaseListingMode.DEFAULT, ((ListCommand) command).getListingMode());
    }

    @Test
    void parseInput_listWithExtraArgAndWhitespace_throwsIncorrectFlagException() {
        assertThrows(IncorrectFlagException.class, () -> Parser.parseInput("  list extra  "));
    }

    @Test
    void parseInput_foolistWithWhitespace_throwsUnknownCommandException() {
        assertThrows(UnknownCommandException.class, () -> Parser.parseInput("  foolist  "));
    }

    @Test
    void parseInput_listWithTabAndNewline_returnsListCommand() {
        Command command = Parser.parseInput("\tlist\n");
        assertEquals(CommandType.LIST, command.getCommandType());
        assertInstanceOf(ListCommand.class, command);
        assertEquals(CaseListingMode.DEFAULT, ((ListCommand) command).getListingMode());
    }

    @Test
    void parseInput_listWithMixedCase_returnsUnknownCommandException() {
        assertThrows(UnknownCommandException.class, () -> Parser.parseInput("LiSt"));
    }

    @Test
    void parseInput_listStatusClosed_returnsClosedOnlyMode() {
        Command command = Parser.parseInput("list --status closed");
        assertEquals(CommandType.LIST, command.getCommandType());
        assertInstanceOf(ListCommand.class, command);
        assertEquals(CaseListingMode.CLOSED_ONLY, ((ListCommand) command).getListingMode());
    }

    @Test
    void parseInput_listStatusOpen_returnsOpenOnlyMode() {
        Command command = Parser.parseInput("list --status open");
        assertEquals(CommandType.LIST, command.getCommandType());
        assertInstanceOf(ListCommand.class, command);
        assertEquals(CaseListingMode.OPEN_ONLY, ((ListCommand) command).getListingMode());
    }

    @Test
    void parseInput_listStatusAll_returnsAllMode() {
        Command command = Parser.parseInput("list --status all");
        assertEquals(CommandType.LIST, command.getCommandType());
        assertInstanceOf(ListCommand.class, command);
        assertEquals(CaseListingMode.ALL, ((ListCommand) command).getListingMode());
    }

    @Test
    void parseInput_listStatusInvalid_throwsListCommandException() {
        assertThrows(IncorrectFlagException.class, () -> Parser.parseInput("list --status banana"));
    }

    @Test
    void parseInput_listStatusMissingValue_throwsIncorrectFlagException() {
        assertThrows(IncorrectFlagException.class, () -> Parser.parseInput("list --status"));
    }

    @Test
    void parseInput_listStatusExtraArgs_throwsListCommandException() {
        assertThrows(IncorrectFlagException.class, () -> Parser.parseInput("list --status open extra"));
    }

    // ----------- TESTS FOR EDIT COMMANDS ----------- //

    @Test
    void parseInput_validEditCommand_returnsEditCommand() {
        Command command = Parser.parseInput("edit 000000 --title NewTitle --date 2025-10-10");
        assertEquals(CommandType.EDIT, command.getCommandType());

        EditCommand editCommand = (EditCommand) command;

        Map<String, String> newValues = editCommand.getNewFlagValues();
        assertEquals("NewTitle", newValues.get("title"));
        assertEquals("2025-10-10", newValues.get("date"));
    }

    @Test
    void parseInput_missingFlagValue_throwsInvalidEditCommandException() {
        assertThrows(IncorrectFlagException.class, () -> Parser.parseInput("edit abc123 --date"));
    }

    @Test
    void parseInput_invalidFlag_throwsInvalidEditCommandException() {
        assertThrows(IncorrectFlagException.class, () -> Parser.parseInput("edit ffffff --invalidFlag newValue"));
    }

    @Test
    void parseInput_missingCaseId_throwsInvalidCaseIdException() {
        assertThrows(InvalidCaseIdException.class, () -> Parser.parseInput("edit --title newTitle"));
    }

    @Test
    void parseInput_missingInput_throwsInvalidEditCommandException() {
        assertThrows(InvalidEditCommandException.class, () -> Parser.parseInput("edit "));
    }

    @Test
    void parseInput_wrongCaseId_throwsInvalidCaseIdException() {
        assertThrows(InvalidCaseIdException.class, () -> Parser.parseInput("edit WrongcaseId --title newTitle"));
    }

    // ----------- TESTS FOR CLOSE COMMANDS ----------- //

    @Test
    void parseInput_closeValid_returnsCloseCommand() {
        Command command = Parser.parseInput("close 000001");
        assertEquals(CommandType.CLOSE, command.getCommandType());
    }

    @Test
    void parseInput_closeWithWhitespace_returnsCloseCommand() {
        Command command = Parser.parseInput("   close   000001   ");
        assertEquals(CommandType.CLOSE, command.getCommandType());
    }

    @Test
    void parseInput_closeMissingArgument_throwsInvalidCloseCommandException() {
        assertThrows(InvalidCloseCommandException.class, () -> Parser.parseInput("close"));
        assertThrows(InvalidCloseCommandException.class, () -> Parser.parseInput("close   "));
    }

    @Test
    void parseInput_closeWrongCaseId_throwsInvalidCaseIdException() {
        assertThrows(InvalidCaseIdException.class, () -> Parser.parseInput("close A01"));
        assertThrows(InvalidCaseIdException.class, () -> Parser.parseInput("close 1"));
    }

    // ----------- TESTS FOR OPEN COMMANDS ----------- //

    @Test
    void parseInput_openValid_returnsOpenCommand() {
        Command command = Parser.parseInput("open 000001");
        assertEquals(CommandType.OPEN, command.getCommandType());
    }

    @Test
    void parseInput_openWithWhitespace_returnsOpenCommand() {
        Command command = Parser.parseInput("   open   000001   ");
        assertEquals(CommandType.OPEN, command.getCommandType());
    }

    @Test
    void parseInput_openMissingArgument_throwsInvalidOpenCommandException() {
        assertThrows(InvalidOpenCommandException.class, () -> Parser.parseInput("open"));
        assertThrows(InvalidOpenCommandException.class, () -> Parser.parseInput("open   "));
    }

    @Test
    void parseInput_openWrongCaseId_throwsInvalidCaseIdException() {
        assertThrows(InvalidCaseIdException.class, () -> Parser.parseInput("open A01"));
        assertThrows(InvalidCaseIdException.class, () -> Parser.parseInput("open 1"));
    }

    // ----------- TESTS FOR ADD COMMANDS ----------- //

    @Test
    void parseInput_addValid_returnsAddCommand() {
        Command command = Parser.parseInput(
                "add --category Theft --title CaseTitle --date 2025-12-12 " +
                        "--info SomeInfo --victim JohnDoe --officer JaneDoe");
        assertEquals(CommandType.ADD, command.getCommandType());
    }

    @Test
    void parseInput_addMissingCompulsoryFlag_throwsInvalidAddCommandException() {
        assertThrows(InvalidAddCommandException.class,
                () -> Parser.parseInput("add --title CaseTitle --date 2025-12-12"));
        assertThrows(InvalidAddCommandException.class,
                () -> Parser.parseInput("add --title CaseTitle --info SomeInfo --officer JaneDoe"));
        assertThrows(InvalidAddCommandException.class,
                () -> Parser.parseInput("add  --date 2025-12-12 --victim JohnDoe --officer JaneDoe"));
    }

    @Test
    void parseInput_addWithExtraWhitespace_returnsAddCommand() {
        Command command = Parser.parseInput(
                "  add   --category  Others --title   CaseTitle   --date   2025-12-12   " +
                        "--info   SomeInfo   --victim   JohnDoe   --officer   JaneDoe  ");
        assertEquals(CommandType.ADD, command.getCommandType());
        assertEquals("others", ((AddCommand) command).getCaseCategory());
        assertEquals("CaseTitle", ((AddCommand) command).getCaseTitle());
        assertEquals("2025-12-12", ((AddCommand) command).getCaseDate());
        assertEquals("SomeInfo", ((AddCommand) command).getCaseInfo());
        assertEquals("JohnDoe", ((AddCommand) command).getCaseVictim());
        assertEquals("JaneDoe", ((AddCommand) command).getCaseOfficer());
    }

    @Test
    void parseInput_addWithDuplicateFlags_throwDuplicateFlagException() {
        assertThrows(DuplicateFlagException.class,
                () -> Parser.parseInput("add --title CaseTitle --date 2025-12-12 --title CaseTitle2"));
    }

    @Test
    void parseInput_addWithFlagError_throwIncorrectFlagException() {
        assertThrows(IncorrectFlagException.class, () -> Parser.parseInput(
                "add --title --date  --info SomeInfo --victim JohnDoe --officer JaneDoe"));
    }

    @Test
    void parseInput_addExceedInputLength_throwIncorrectFlagException() {
        StringBuilder longInfo = new StringBuilder();
        longInfo.append("a".repeat(8000));
        String input =
                String.format("add --title CaseTitle --date 2025-12-12 --info %s --victim JohnDoe --officer JaneDoe",
                        longInfo);
        assertThrows(InputLengthExceededException.class, () -> Parser.parseInput(input));
    }

    @Test
    void parseInput_addValidWithEscape_returnsAddCommand() {
        Command command = Parser.parseInput(
                "  add --category theft --title   CaseTitle\\--longinfo   --date   2025-12-12   --info   SomeInfo   " +
                        "--victim   JohnDoe   --officer   JaneDoe  ");
        assertEquals(CommandType.ADD, command.getCommandType());
        assertEquals("theft", ((AddCommand) command).getCaseCategory());
        assertEquals("CaseTitle--longinfo", ((AddCommand) command).getCaseTitle());
        assertEquals("2025-12-12", ((AddCommand) command).getCaseDate());
        assertEquals("SomeInfo", ((AddCommand) command).getCaseInfo());
        assertEquals("JohnDoe", ((AddCommand) command).getCaseVictim());
        assertEquals("JaneDoe", ((AddCommand) command).getCaseOfficer());
    }
}
