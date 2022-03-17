package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_PI_DAY;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_TWO_HOURS;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESCRIPTION_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DURATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.RECUR_FREQUENCY_DESC_WEEKLY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RECUR_FREQUENCY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedule.AddEventCommand;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;
import seedu.address.testutil.EventBuilder;

class AddEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " "
                + EVENT_DESCRIPTION_DESC_CS2101 + DATE_DESC_PI_DAY + TIME_DESC_MORNING
                + DURATION_DESC_TWO_HOURS + RECUR_FREQUENCY_DESC_WEEKLY;

        EventBuilder eventBuilder = new EventBuilder();
        Event event = eventBuilder.withEventDescription(VALID_EVENT_DESCRIPTION).withDate(VALID_EVENT_DATE)
                .withTime(VALID_EVENT_TIME).withDuration(VALID_EVENT_DURATION)
                .withRecurFrequency(VALID_RECUR_FREQUENCY).build();

        AddEventCommand expectedCommand = new AddEventCommand(targetIndex, event);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid event description
        assertParseFailure(parser, "1 " + INVALID_EVENT_DESCRIPTION_DESC
                + DATE_DESC_PI_DAY, EventDescription.MESSAGE_CONSTRAINTS);

        //invalid date
        assertParseFailure(parser, "1 " + EVENT_DESCRIPTION_DESC_CS2101
                + INVALID_EVENT_DATE_DESC, Event.DATE_MESSAGE_CONSTRAINTS);

        //invalid time
        assertParseFailure(parser, "1 " + EVENT_DESCRIPTION_DESC_CS2101 + DATE_DESC_PI_DAY
                + INVALID_EVENT_TIME_DESC, Event.TIME_MESSAGE_CONSTRAINTS);

        //invalid duration
        assertParseFailure(parser, "1 " + EVENT_DESCRIPTION_DESC_CS2101 + DATE_DESC_PI_DAY + TIME_DESC_MORNING
                + INVALID_DURATION_DESC, Event.DURATION_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingFields_failure() {
        //no index specified
        assertParseFailure(parser, EVENT_DESCRIPTION_DESC_CS2101 + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        //missing event description
        assertParseFailure(parser, "1 " + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        //missing event date
        assertParseFailure(parser, "1 " + EVENT_DESCRIPTION_DESC_CS2101, MESSAGE_INVALID_FORMAT);

        //specified duration, but missing event time
        assertParseFailure(parser, "1 " + EVENT_DESCRIPTION_DESC_CS2101 + DATE_DESC_PI_DAY
                + DURATION_DESC_TWO_HOURS, String.format(MESSAGE_INVALID_COMMAND_FORMAT, Event.MISSING_TIME_MESSAGE));
    }

    @Test
    public void parse_invalidPreambles_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + EVENT_DESCRIPTION_DESC_CS2101 + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EVENT_DESCRIPTION_DESC_CS2101 + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        // multiple indices
        assertParseFailure(parser, "1 1" + EVENT_DESCRIPTION_DESC_CS2101 + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "some_random_string " + EVENT_DESCRIPTION_DESC_CS2101
                + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ " + EVENT_DESCRIPTION_DESC_CS2101 + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);
    }
}
