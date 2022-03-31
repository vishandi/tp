package seedu.address.logic.parser.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import seedu.address.logic.commands.schedule.WhoIsFreeCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Tag;
import seedu.address.model.schedule.IsPersonFreePredicate;

/**
 * Parses input arguments and creates a new WhoIsFreeCommand object.
 */
public class WhoIsFreeCommandParser implements Parser<WhoIsFreeCommand> {
    @Override
    public WhoIsFreeCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TIME, PREFIX_DATE, PREFIX_TAG);

        boolean hasTimePrefix = argMultimap.getValue(PREFIX_TIME).isPresent();
        boolean hasDatePrefix = argMultimap.getValue(PREFIX_DATE).isPresent();

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhoIsFreeCommand.MESSAGE_USAGE));
        }

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (hasTimePrefix && hasDatePrefix) {
            LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            LocalTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());

            return new WhoIsFreeCommand(new IsPersonFreePredicate(time, date, tagList));
        } else if (hasTimePrefix) {
            LocalTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());

            return new WhoIsFreeCommand(new IsPersonFreePredicate(time, LocalDate.now(), tagList));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhoIsFreeCommand.MESSAGE_USAGE));
        }

    }
}
