package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.schedule.DeleteEventCommand;

public class DeleteEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //only one index specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        //negative index
        assertParseFailure(parser, "-5 3", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "3 -5", MESSAGE_INVALID_FORMAT);
    }

    //TODO: add more tests
}
