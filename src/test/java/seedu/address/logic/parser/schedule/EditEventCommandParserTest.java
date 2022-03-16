package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_PI_DAY;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_TWO_HOURS;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESCRIPTION_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DURATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RECUR_FREQUENCY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.RECUR_FREQUENCY_DESC_WEEKLY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RECUR_FREQUENCY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditUtil;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.commands.schedule.EditEventCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.recurfrequency.RecurFrequency;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

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

        EditUtil.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
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

        EditUtil.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventDescription(VALID_EVENT_DESCRIPTION)
                .withDate(VALID_EVENT_DATE).withTime(VALID_EVENT_TIME).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, INDEX_FIRST_EVENT, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
