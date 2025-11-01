package seedu.sgsafe.utils.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import seedu.sgsafe.utils.command.CaseListingMode;
import seedu.sgsafe.utils.command.Command;
import seedu.sgsafe.utils.command.CommandType;
import seedu.sgsafe.utils.command.ListCommand;
import seedu.sgsafe.utils.command.AddCommand;
import seedu.sgsafe.utils.command.ByeCommand;
import seedu.sgsafe.utils.command.SettingCommand;
import seedu.sgsafe.utils.command.SettingType;
import seedu.sgsafe.utils.exceptions.IncorrectFlagException;
import seedu.sgsafe.utils.exceptions.EmptyCommandException;
import seedu.sgsafe.utils.exceptions.InputLengthExceededException;
import seedu.sgsafe.utils.exceptions.DuplicateFlagException;
import seedu.sgsafe.utils.exceptions.InvalidAddCommandException;
import seedu.sgsafe.utils.exceptions.InvalidByeCommandException;
import seedu.sgsafe.utils.exceptions.InvalidCaseIdException;
import seedu.sgsafe.utils.exceptions.InvalidDateInputException;
import seedu.sgsafe.utils.exceptions.InvalidEditCommandException;
import seedu.sgsafe.utils.exceptions.InvalidCloseCommandException;
import seedu.sgsafe.utils.exceptions.InvalidDeleteCommandException;
import seedu.sgsafe.utils.exceptions.InvalidFormatStringException;
import seedu.sgsafe.utils.exceptions.InvalidIntegerException;
import seedu.sgsafe.utils.exceptions.InvalidReadCommandException;

import seedu.sgsafe.utils.exceptions.InvalidOpenCommandException;
import seedu.sgsafe.utils.exceptions.InvalidListCommandException;
import seedu.sgsafe.utils.exceptions.InvalidSettingCommandException;
import seedu.sgsafe.utils.exceptions.UnknownCommandException;
import seedu.sgsafe.utils.settings.Settings;

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
    void parseInput_listWithMixedCase_returnsListCommand() {
        Command command = Parser.parseInput("\tLiSt\n");
        assertEquals(CommandType.LIST, command.getCommandType());
        assertInstanceOf(ListCommand.class, command);
        assertEquals(CaseListingMode.DEFAULT, ((ListCommand) command).getListingMode());

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
        assertThrows(InvalidListCommandException.class, () -> Parser.parseInput("list --status banana"));
    }

    @Test
    void parseInput_listStatusMissingValue_throwsIncorrectFlagException() {
        assertThrows(IncorrectFlagException.class, () -> Parser.parseInput("list --status"));
    }

    @Test
    void parseInput_listStatusExtraArgs_throwsListCommandException() {
        assertThrows(InvalidListCommandException.class, () -> Parser.parseInput("list --status open extra"));
    }

    // ----------- TESTS FOR EDIT COMMANDS ----------- //

    @Test
    void parseInput_missingFlagValue_throwsInvalidEditCommandException() {
        assertThrows(IncorrectFlagException.class, () -> Parser.parseInput("edit abc123 --date"));
    }

    @Test
    void parseInput_duplicateFlags_throwsDuplicateFlagException() {
        assertThrows(DuplicateFlagException.class,
                () -> Parser.parseInput("edit 000001 --title First --title Second"));
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

    @Test
    void parseInput_invalidDateFormat_throwsIncorrectFlagException() {
        assertThrows(InvalidDateInputException.class, () -> Parser.parseInput("edit 000001 --date 32-13-20"));
    }

    @Test
    void parseInput_nonIntegerValueForSpeedLimit_throwsInvalidIntegerException() {
        assertThrows(InvalidIntegerException.class, () -> Parser.parseInput("edit 000001 --speed-limit fast"));
    }

    @Test
    void parseInput_negativeValueForVictimNumber_throwsInvalidIntegerException() {
        assertThrows(InvalidIntegerException.class, () -> Parser.parseInput("edit 000001 --number-of-victims -50"));
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
        Settings.setInputDateFormat("dd/MM/yyyy");
        Command command = Parser.parseInput(
                "add --category Theft --title CaseTitle --date 12/02/2022 " +
                        "--info SomeInfo --victim JohnDoe --officer JaneDoe");
        assertEquals(CommandType.ADD, command.getCommandType());
    }

    @Test
    void parseInput_addMissingCompulsoryFlag_throwsInvalidAddCommandException() {
        assertThrows(InvalidAddCommandException.class,
                () -> Parser.parseInput("add --title CaseTitle --date 12/01/2022"));
        assertThrows(InvalidAddCommandException.class,
                () -> Parser.parseInput("add --title CaseTitle --info SomeInfo --officer JaneDoe"));
        assertThrows(InvalidAddCommandException.class,
                () -> Parser.parseInput("add  --date 12/01/2022 --victim JohnDoe --officer JaneDoe"));
    }

    @Test
    void parseInput_addWithExtraWhitespace_returnsAddCommand() {
        Settings.setInputDateFormat("dd/MM/yyyy");
        LocalDate date = LocalDate.of(2022, 01, 12);
        Command command = Parser.parseInput(
                "  add   --category  Others --title   CaseTitle   --date   12/01/2022   " +
                        "--info   SomeInfo   --victim   JohnDoe   --officer   JaneDoe  ");
        assertEquals(CommandType.ADD, command.getCommandType());
        assertEquals("others", ((AddCommand) command).getCaseCategory());
        assertEquals("CaseTitle", ((AddCommand) command).getCaseTitle());
        assertEquals(date, ((AddCommand) command).getCaseDate());
        assertEquals("SomeInfo", ((AddCommand) command).getCaseInfo());
        assertEquals("JohnDoe", ((AddCommand) command).getCaseVictim());
        assertEquals("JaneDoe", ((AddCommand) command).getCaseOfficer());
    }

    @Test
    void parseInput_addWithDuplicateFlags_throwDuplicateFlagException() {
        assertThrows(DuplicateFlagException.class,
                () -> Parser.parseInput("add --title CaseTitle --date 12/01/2022 --title CaseTitle2"));
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
                String.format("add --title CaseTitle --date 12/01/2022 --info %s --victim JohnDoe --officer JaneDoe",
                        longInfo);
        assertThrows(InputLengthExceededException.class, () -> Parser.parseInput(input));
    }

    @Test
    void parseInput_addValidWithEscape_returnsAddCommand() {
        Settings.setInputDateFormat("yyyy-MM-dd");
        LocalDate dateToVerify = LocalDate.of(2025, 12, 12);
        Command command = Parser.parseInput(
                "  add --category theft --title   CaseTitle\\--longinfo   --date   2025-12-12   --info   SomeInfo   " +
                        "--victim   JohnDoe   --officer   JaneDoe  ");
        assertEquals(CommandType.ADD, command.getCommandType());
        assertEquals("theft", ((AddCommand) command).getCaseCategory());
        assertEquals("CaseTitle--longinfo", ((AddCommand) command).getCaseTitle());
        assertEquals(dateToVerify, ((AddCommand) command).getCaseDate());
        assertEquals("SomeInfo", ((AddCommand) command).getCaseInfo());
        assertEquals("JohnDoe", ((AddCommand) command).getCaseVictim());
        assertEquals("JaneDoe", ((AddCommand) command).getCaseOfficer());
    }

    // ----------- TESTS FOR DELETE COMMANDS ----------- //
    @Test
    void parseInput_deleteValid_returnsDeleteCommand() {
        String input = "delete abcdef";
        Command command = Parser.parseInput(input);
        assertEquals(CommandType.DELETE, command.getCommandType());
    }

    @Test
    void parseInput_delete_throwsInvalidDeleteCommandException() {
        String input = "delete";
        assertThrows(InvalidDeleteCommandException.class, () -> Parser.parseInput(input));
    }

    @Test
    void parseInput_deleteTooShortCaseId_throwsInvalidDeleteCommandException() {
        String input = "delete abc";
        assertThrows(InvalidDeleteCommandException.class, () -> Parser.parseInput(input));
    }

    @Test
    void parseInput_deleteTooLongCaseId_throwsInvalidDeleteCommandException() {
        String input = "delete abc1234";
        assertThrows(InvalidDeleteCommandException.class, () -> Parser.parseInput(input));
    }

    @Test
    void parseInput_deleteAdditionalArguments_throwsInvalidDeleteCommandException() {
        String input = "delete abc123 456";
        assertThrows(InvalidDeleteCommandException.class, () -> Parser.parseInput(input));
    }

    // ----------- TESTS FOR READ COMMANDS ----------- //

    @Test
    void parseInput_readValid_returnsReadCommand() {
        Command command = Parser.parseInput("read 000001");
        assertEquals(CommandType.READ, command.getCommandType());
    }

    @Test
    void parseInput_readWithWhitespace_returnsReadCommand() {
        Command command = Parser.parseInput("   read   000001   ");
        assertEquals(CommandType.READ, command.getCommandType());
    }

    @Test
    void parseInput_readMissingArgument_throwsInvalidReadCommandException() {
        assertThrows(InvalidReadCommandException.class, () -> Parser.parseInput("read"));
        assertThrows(InvalidReadCommandException.class, () -> Parser.parseInput("read   "));
    }

    @Test
    void parseInput_readWrongCaseId_throwsInvalidReadCommandException() {
        assertThrows(InvalidReadCommandException.class, () -> Parser.parseInput("read A01"));
        assertThrows(InvalidReadCommandException.class, () -> Parser.parseInput("read 1"));
    }

    @Test
    void parseInput_readExtraArguments_throwsInvalidReadCommandException() {
        assertThrows(InvalidReadCommandException.class, () -> Parser.parseInput("read 000000 extraArg"));
        assertThrows(InvalidReadCommandException.class, () -> Parser.parseInput("read 000000   extraArg"));
    }

    // ----------- TESTS FOR BYE COMMANDS ----------- //

    @Test
    void parseInput_byeCommand_returnsByeCommand() {
        Command cmd = Parser.parseInput("bye");
        assertEquals(ByeCommand.class, cmd.getClass());
    }

    @Test
    void parseInput_byeCommandWithExtraArgs_throwsInvalidByeCommandException() {
        assertThrows(InvalidByeCommandException.class,
                () -> Parser.parseInput("bye now"));
    }

    // ----------- TESTS FOR SETTING COMMANDS ----------- //

    @Test
    void parseInput_settingWithValidInputFormat_returnsSettingCommand() {
        Command command = Parser.parseInput("setting --type dateinput --value dd/MM/yyyy");
        assertEquals(CommandType.SETTING, command.getCommandType());
        assertInstanceOf(SettingCommand.class, command);
        assertEquals(SettingType.DATEINPUT, ((SettingCommand) command).getSettingType());
        assertEquals("dd/MM/yyyy", ((SettingCommand) command).getNewDateFormat());
    }

    @Test
    void parseInput_settingWithValidOutputFormat_returnsSettingCommand() {
        Command command = Parser.parseInput("setting --type dateoutput --value yyyy-MM-dd");
        assertEquals(CommandType.SETTING, command.getCommandType());
        assertInstanceOf(SettingCommand.class, command);
        assertEquals(SettingType.DATEOUTPUT, ((SettingCommand) command).getSettingType());
        assertEquals("yyyy-MM-dd", ((SettingCommand) command).getNewDateFormat());
    }

    @Test
    void parseInput_settingMissingTypeFlag_throwsInvalidSettingCommandException() {
        assertThrows(InvalidSettingCommandException.class,
                () -> Parser.parseInput("setting --value dd/MM/yyyy"));
    }

    @Test
    void parseInput_settingMissingBothFlags_throwsInvalidSettingCommandException() {
        assertThrows(InvalidSettingCommandException.class,
                () -> Parser.parseInput("setting"));
    }

    @Test
    void parseInput_settingEmptyRemainder_throwsInvalidSettingCommandException() {
        assertThrows(InvalidSettingCommandException.class,
                () -> Parser.parseInput("setting   "));
    }

    @Test
    void parseInput_settingWithChineseCharacters_throwsInvalidFormatStringException() {
        assertThrows(InvalidFormatStringException.class,
                () -> Parser.parseInput("setting --type dateinput --value yyyy年MM月dd日"));
    }

    @Test
    void parseInput_settingWithCyrillicCharacters_throwsInvalidFormatStringException() {
        assertThrows(InvalidFormatStringException.class,
                () -> Parser.parseInput("setting --type dateoutput --value dd-MM-yyyyг"));
    }

    @Test
    void parseInput_settingWithUnicodeSymbols_throwsInvalidFormatStringException() {
        assertThrows(InvalidFormatStringException.class,
                () -> Parser.parseInput("setting --type dateinput --value dd★MM★yyyy"));
    }

    @Test
    void parseInput_settingWithEmojiCharacters_throwsInvalidFormatStringException() {
        assertThrows(InvalidFormatStringException.class,
                () -> Parser.parseInput("setting --type dateoutput --value dd/MM/yyyy😊"));
    }

    @Test
    void parseInput_settingExceedingMaxLength_throwsInputLengthExceededException() {
        String longValue = "yyyy" +
                "-MM".repeat(2500);
        assertThrows(InputLengthExceededException.class,
                () -> Parser.parseInput("setting --type dateinput --value " +
                        longValue));
    }

}
