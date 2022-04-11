package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_PI_DAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.schedule.WhoIsFreeCommand;
import seedu.address.model.person.Tag;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.IsPersonFreePredicate;

class WhoIsFreeCommandParserTest {

    private WhoIsFreeCommandParser parser = new WhoIsFreeCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhoIsFreeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFreeScheduleCommand() {
        String time = "23:00";
        String date = "2022-03-13";
        Set<Tag> emptyTags = new HashSet<>();

        // with time only
        WhoIsFreeCommand expectedFreeScheduleTimeOnlyCommand =
                new WhoIsFreeCommand(new IsPersonFreePredicate(LocalTime.parse(time), LocalDate.now(), emptyTags));
        assertParseSuccess(parser, " " + PREFIX_TIME + time, expectedFreeScheduleTimeOnlyCommand);

        // with time and date
        WhoIsFreeCommand expectedFreeScheduleTimeAndDateCommand =
                new WhoIsFreeCommand(new IsPersonFreePredicate(
                        LocalTime.parse(time), LocalDate.parse(date), emptyTags));
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

        // valid time and date but empty tag
        assertParseFailure(parser, " " + TIME_DESC_MORNING + DATE_DESC_PI_DAY + " " + PREFIX_TAG,
                Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_timeAndDateSpecified_success() {
        Set<Tag> emptyTags = new HashSet<>();
        WhoIsFreeCommand expectedCommand = new WhoIsFreeCommand(new IsPersonFreePredicate(
                LocalTime.parse(VALID_EVENT_TIME), LocalDate.parse(VALID_EVENT_DATE), emptyTags));
        assertParseSuccess(parser, " " + TIME_DESC_MORNING + DATE_DESC_PI_DAY, expectedCommand);
    }

    @Test
    public void parse_timeSpecified_success() {
        Set<Tag> emptyTags = new HashSet<>();
        WhoIsFreeCommand expectedCommand = new WhoIsFreeCommand(
                new IsPersonFreePredicate(LocalTime.parse(VALID_EVENT_TIME), LocalDate.now(), emptyTags));
        assertParseSuccess(parser, " " + TIME_DESC_MORNING, expectedCommand);
    }

    @Test
    public void parse_tagsSpecified_success() {
        Tag friendTag = new Tag(VALID_TAG_FRIEND);
        Tag husbandTag = new Tag(VALID_TAG_HUSBAND);
        Set<Tag> tags = new HashSet<>(Arrays.asList(friendTag));

        // one tag
        WhoIsFreeCommand expectedCommand = new WhoIsFreeCommand(
                new IsPersonFreePredicate(LocalTime.parse(VALID_EVENT_TIME), LocalDate.now(), tags));
        assertParseSuccess(parser, " " + TIME_DESC_MORNING + TAG_DESC_FRIEND, expectedCommand);

        // multiple tags
        tags = new HashSet<>(Arrays.asList(friendTag, husbandTag));
        expectedCommand = new WhoIsFreeCommand(
                new IsPersonFreePredicate(LocalTime.parse(VALID_EVENT_TIME), LocalDate.now(), tags));
        assertParseSuccess(parser, " " + TIME_DESC_MORNING + TAG_DESC_FRIEND, expectedCommand);

    }
}
