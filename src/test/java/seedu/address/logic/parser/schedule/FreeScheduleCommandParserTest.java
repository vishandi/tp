package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.schedule.FreeScheduleCommand;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.IsPersonFreePredicate;

class FreeScheduleCommandParserTest {

    private FreeScheduleCommandParser parser = new FreeScheduleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFreeScheduleCommand() {
        String time = "23:00";
        String date = "2022-03-13";

        // with time only
        FreeScheduleCommand expectedFreeScheduleTimeOnlyCommand =
                new FreeScheduleCommand(new IsPersonFreePredicate(LocalTime.parse(time), LocalDate.now()));
        assertParseSuccess(parser, " " + PREFIX_TIME + time, expectedFreeScheduleTimeOnlyCommand);

        // with time and date
        FreeScheduleCommand expectedFreeScheduleTimeAndDateCommand =
                new FreeScheduleCommand(new IsPersonFreePredicate(LocalTime.parse(time), LocalDate.parse(date)));
        assertParseSuccess(parser, " " + PREFIX_TIME + time + " " + PREFIX_DATE + date,
                expectedFreeScheduleTimeAndDateCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid time
        assertParseFailure(parser, " " + PREFIX_TIME + "1000", Event.TIME_MESSAGE_CONSTRAINTS);

        // valid time but invalid date
        assertParseFailure(parser, " " + PREFIX_TIME + "10:00 " + PREFIX_DATE + "14 March 2022",
                Event.DATE_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_timeAndDateSpecified_success() {
        String time = "09:00";
        String date = "2022-03-16";
        FreeScheduleCommand expectedCommand = new FreeScheduleCommand(
                new IsPersonFreePredicate(LocalTime.parse(time), LocalDate.parse(date)));
        assertParseSuccess(parser, " " + PREFIX_TIME + time + " " + PREFIX_DATE + date, expectedCommand);
    }

    @Test
    public void parse_timeSpecified_success() {
        String time = "09:00";
        FreeScheduleCommand expectedCommand = new FreeScheduleCommand(
                new IsPersonFreePredicate(LocalTime.parse(time), LocalDate.now()));
        assertParseSuccess(parser, " " + PREFIX_TIME + time, expectedCommand);
    }
}