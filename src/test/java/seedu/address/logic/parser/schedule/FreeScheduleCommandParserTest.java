package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_PI_DAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME;
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
        assertParseFailure(parser, " " + PREFIX_TIME + INVALID_EVENT_TIME_DESC, Event.TIME_MESSAGE_CONSTRAINTS);

        // valid time but invalid date
        assertParseFailure(parser, " " + TIME_DESC_MORNING + INVALID_EVENT_DATE_DESC,
                Event.DATE_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_timeAndDateSpecified_success() {
        FreeScheduleCommand expectedCommand = new FreeScheduleCommand(
                new IsPersonFreePredicate(LocalTime.parse(VALID_EVENT_TIME), LocalDate.parse(VALID_EVENT_DATE)));
        assertParseSuccess(parser, " " + TIME_DESC_MORNING + DATE_DESC_PI_DAY, expectedCommand);
    }

    @Test
    public void parse_timeSpecified_success() {
        FreeScheduleCommand expectedCommand = new FreeScheduleCommand(
                new IsPersonFreePredicate(LocalTime.parse(VALID_EVENT_TIME), LocalDate.now()));
        assertParseSuccess(parser, " " + TIME_DESC_MORNING, expectedCommand);
    }
}
