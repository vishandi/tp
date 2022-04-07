package seedu.address.logic.parser.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDate;

import seedu.address.logic.commands.schedule.FindCommonTimingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.IsTagInPersonPredicate;
import seedu.address.model.person.Tag;

/**
 * Parses input arguments and creates a new FindCommonTimingCommand object.
 */
public class FindCommonTimingCommandParser implements Parser<FindCommonTimingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommonTimingCommand
     * and returns a FindCommonTimingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindCommonTimingCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TAG, PREFIX_DATE);

        boolean hasTagPrefix = argMultimap.getValue(PREFIX_TAG).isPresent();
        boolean hasDatePrefix = argMultimap.getValue(PREFIX_DATE).isPresent();
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommonTimingCommand.MESSAGE_USAGE));
        }

        if (hasTagPrefix && hasDatePrefix) {
            Tag tag = ParserUtil.parseTag((argMultimap.getValue(PREFIX_TAG).get()));
            LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

            return new FindCommonTimingCommand(new IsTagInPersonPredicate(tag), date);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommonTimingCommand.MESSAGE_USAGE));
        }
    }
}
