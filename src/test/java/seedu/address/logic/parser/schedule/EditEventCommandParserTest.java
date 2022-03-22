package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_PI_DAY;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_TWO_HOURS;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESCRIPTION_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DURATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RECUR_FREQUENCY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.RECUR_FREQUENCY_DESC_WEEKLY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RECUR_FREQUENCY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_HENDRI;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditUtil.EditEventDescriptor;
import seedu.address.logic.commands.schedule.EditEventCommand;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.schedule.RecurFrequency;
import seedu.address.testutil.EditEventDescriptorBuilder;

class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_EVENT_DATE, MESSAGE_INVALID_FORMAT);

        // only one index specified
        assertParseFailure(parser, "1" + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1 1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreambles_failure() {
        // negative indices
        assertParseFailure(parser, "-5 -2" + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        // negative index followed by valid index
        assertParseFailure(parser, "-5 1" + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        // valid index followed by negative index
        assertParseFailure(parser, "1 -2" + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        // zero indices
        assertParseFailure(parser, "0 0" + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        // zero index followed by valid index
        assertParseFailure(parser, "0 1" + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        // valid index followed by zero index
        assertParseFailure(parser, "1 0" + DATE_DESC_PI_DAY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1 1" + INVALID_EVENT_DESCRIPTION_DESC,
                EventDescription.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 1" + INVALID_EVENT_DATE_DESC, Event.DATE_MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 1" + INVALID_EVENT_TIME_DESC, Event.TIME_MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 1" + INVALID_DURATION_DESC, Event.DURATION_MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 1" + INVALID_RECUR_FREQUENCY_DESC,
                RecurFrequency.INVALID_RECUR_FREQUENCY_MESSAGE);

        // invalid event description followed by valid time
        assertParseFailure(parser, "1 1" + INVALID_EVENT_DESCRIPTION_DESC + TIME_DESC_MORNING,
                EventDescription.MESSAGE_CONSTRAINTS);

        // valid date followed by invalid date
        assertParseFailure(parser, "1 1" + DATE_DESC_PI_DAY + INVALID_EVENT_DATE_DESC,
                Event.DATE_MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1 1" + INVALID_EVENT_DATE_DESC + INVALID_DURATION_DESC + EVENT_DESCRIPTION_DESC_CS2101,
                Event.DATE_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + INDEX_FIRST_EVENT.getOneBased()
                + EVENT_DESCRIPTION_DESC_CS2101 + DATE_DESC_PI_DAY + TIME_DESC_MORNING + DURATION_DESC_TWO_HOURS
                + RECUR_FREQUENCY_DESC_WEEKLY;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventDescription(VALID_EVENT_DESCRIPTION)
                .withDate(VALID_EVENT_DATE).withTime(VALID_EVENT_TIME).withDuration(VALID_EVENT_DURATION)
                .withRecurFrequency(VALID_RECUR_FREQUENCY).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, INDEX_FIRST_EVENT, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + INDEX_FIRST_EVENT.getOneBased()
                + EVENT_DESCRIPTION_DESC_CS2101 + DATE_DESC_PI_DAY + TIME_DESC_MORNING;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventDescription(VALID_EVENT_DESCRIPTION)
                .withDate(VALID_EVENT_DATE).withTime(VALID_EVENT_TIME).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, INDEX_FIRST_EVENT, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // more test cases required
    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_HENDRI;
        Index targetEventIndex = INDEX_FIRST_EVENT;
        // event description
        String userInput = targetIndex.getOneBased() + " " + targetEventIndex.getOneBased()
                + EVENT_DESCRIPTION_DESC_CS2101;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventDescription(VALID_EVENT_DESCRIPTION).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, targetEventIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + " " + targetEventIndex.getOneBased() + DATE_DESC_PI_DAY;
        descriptor = new EditEventDescriptorBuilder().withDate(VALID_EVENT_DATE).build();
        expectedCommand = new EditEventCommand(targetIndex, targetEventIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time
        userInput = targetIndex.getOneBased() + " " + targetEventIndex.getOneBased() + TIME_DESC_MORNING;
        descriptor = new EditEventDescriptorBuilder().withTime(VALID_EVENT_TIME).build();
        expectedCommand = new EditEventCommand(targetIndex, targetEventIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // duration
        userInput = targetIndex.getOneBased() + " " + targetEventIndex.getOneBased() + DURATION_DESC_TWO_HOURS;
        descriptor = new EditEventDescriptorBuilder().withDuration(VALID_EVENT_DURATION).build();
        expectedCommand = new EditEventCommand(targetIndex, targetEventIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // recur frequency
        userInput = targetIndex.getOneBased() + " " + targetEventIndex.getOneBased() + RECUR_FREQUENCY_DESC_WEEKLY;
        descriptor = new EditEventDescriptorBuilder().withRecurFrequency(VALID_RECUR_FREQUENCY).build();
        expectedCommand = new EditEventCommand(targetIndex, targetEventIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        Index targetEventIndex = INDEX_FIRST_EVENT;

        String userInput = targetIndex.getOneBased() + " " + targetEventIndex.getOneBased() + DATE_DESC_PI_DAY
                + DURATION_DESC_TWO_HOURS + TIME_DESC_MORNING + DATE_DESC_PI_DAY + DURATION_DESC_TWO_HOURS
                + TIME_DESC_MORNING;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDate(VALID_EVENT_DATE)
                .withTime(VALID_EVENT_TIME).withDuration(VALID_EVENT_DURATION).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, targetEventIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        Index targetEventIndex = INDEX_FIRST_EVENT;

        // no other valid values specified
        String userInput = targetIndex.getOneBased() + " " + targetEventIndex.getOneBased() + INVALID_EVENT_TIME_DESC
                + TIME_DESC_MORNING;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTime(VALID_EVENT_TIME).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, targetEventIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + " " + targetEventIndex.getOneBased() + DATE_DESC_PI_DAY
                + INVALID_EVENT_TIME_DESC + DURATION_DESC_TWO_HOURS + TIME_DESC_MORNING;
        descriptor = new EditEventDescriptorBuilder().withDate(VALID_EVENT_DATE).withTime(VALID_EVENT_TIME)
                .withDuration(VALID_EVENT_DURATION).build();
        expectedCommand = new EditEventCommand(targetIndex, targetEventIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
