package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SetSalaryCommand;

class SetSalaryCommandParserTest {
    private SetSalaryCommandParser parser = new SetSalaryCommandParser();

    @Test
    void parse_validFormat_success() {
        assertParseSuccess(parser, "E12345 100", new SetSalaryCommand("E12345", 100));
        assertParseSuccess(parser, "E12345 100.45", new SetSalaryCommand("E12345", 100.45));
        assertParseSuccess(parser, "E12345 100.3", new SetSalaryCommand("E12345", 100.3));
        assertParseSuccess(parser, "E12345 100.03", new SetSalaryCommand("E12345", 100.03));
        assertParseSuccess(parser, "E12345 100.126", new SetSalaryCommand("E12345", 100.126));
    }

    @Test
    void parse_negativeZero_parsedToNormalZero() {
        assertParseSuccess(parser, "E12345 -0", new SetSalaryCommand("E12345", 0));
        assertParseSuccess(parser, "E12345 -0.0", new SetSalaryCommand("E12345", 0));
        assertParseSuccess(parser, "E12345 -0.00", new SetSalaryCommand("E12345", 0));
        assertParseSuccess(parser, "E12345 -0.000", new SetSalaryCommand("E12345", 0));
    }

    @Test
    void parse_invalidFormat_failure() {
        assertParseFailure(parser, "E12345",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetSalaryCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "E12345,100",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetSalaryCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "E12345 100 200",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetSalaryCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_infiniteSalary_failure() {
        assertParseFailure(parser, "E12345 1e309", Messages.MESSAGE_SALARY_TOO_HIGH);
        assertParseFailure(parser, "E12345 Infinity", Messages.MESSAGE_SALARY_TOO_HIGH);
    }

    @Test
    void parse_nonNumericSalary_failure() {
        assertParseFailure(parser, "E12345 a", Messages.MESSAGE_INVALID_SALARY);
        assertParseFailure(parser, "E12345 +-0", Messages.MESSAGE_INVALID_SALARY);
    }

    @Test
    void parse_invalidId_failure() {
        assertParseFailure(parser, "12345 100", Messages.MESSAGE_INVALID_PERSON_ID);
    }

    @Test
    void parse_invalidSalary_failure() {
        assertParseFailure(parser, "E12345 -100", Messages.MESSAGE_INVALID_SALARY);
    }
}
