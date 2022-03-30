package seedu.address.logic.parser.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.logic.commands.person.ViewGroupCommand;
import seedu.address.logic.commands.schedule.FindCommonTimingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.IsTagInPersonPredicate;
import seedu.address.model.person.Tag;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Parses input arguments and creates a new ViewGroupCommand object.
 */
public class FindCommonTimingCommandParser implements Parser<FindCommonTimingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewGroupCommand
     * and returns a ViewGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindCommonTimingCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TAG, PREFIX_DATE);

        boolean hasTagPrefix = argMultimap.getValue(PREFIX_TAG).isPresent();
        boolean hasDatePrefix = argMultimap.getValue(PREFIX_DATE).isPresent();

        if (hasTagPrefix && hasDatePrefix) {
            Tag tag = new Tag(argMultimap.getValue(PREFIX_TAG).get());
            LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

            return new FindCommonTimingCommand(new IsTagInPersonPredicate(tag), date);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommonTimingCommand.MESSAGE_USAGE));
        }
    }
}