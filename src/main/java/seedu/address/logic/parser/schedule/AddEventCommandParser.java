package seedu.address.logic.parser.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.schedule.AddEventCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.schedule.Event.DEFAULT_DURATION;
import static seedu.address.model.schedule.Event.DEFAULT_TIME;
import static seedu.address.model.schedule.Event.FULL_DAY_EVENT_DURATION;
import static seedu.address.model.schedule.Event.MISSING_TIME_MESSAGE;
import static seedu.address.model.schedule.RecurFrequency.DEFAULT_RECURRENCE;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedule.AddEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.schedule.RecurFrequency;

/**
 * Parses input arguments and creates a new AddEventCommand object.
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_DESCRIPTION, PREFIX_DATE,
                        PREFIX_TIME, PREFIX_DURATION, PREFIX_RECUR_FREQUENCY);

        boolean hasEventDescription = arePrefixesPresent(argMultimap, PREFIX_EVENT_DESCRIPTION);
        boolean hasEventDate = arePrefixesPresent(argMultimap, PREFIX_DATE);
        boolean hasEventDuration = arePrefixesPresent(argMultimap, PREFIX_DURATION);
        boolean hasEventTime = arePrefixesPresent(argMultimap, PREFIX_TIME);

        if (!(hasEventDescription && hasEventDate)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        } else if (hasEventDuration && !hasEventTime) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MISSING_TIME_MESSAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        EventDescription eventDescription = ParserUtil.parseEventDescription(argMultimap.getValue(
                PREFIX_EVENT_DESCRIPTION).get());
        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        LocalTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).orElse(DEFAULT_TIME));
        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).orElse(DEFAULT_DURATION));
        if (!hasEventTime) {
            duration = ParserUtil.parseDuration(FULL_DAY_EVENT_DURATION);
        }
        RecurFrequency recurFrequency = ParserUtil.parseRecurFrequency(argMultimap.getValue(
                PREFIX_RECUR_FREQUENCY).orElse(DEFAULT_RECURRENCE));
        Event event = new Event(eventDescription, date, time, duration, recurFrequency);

        return new AddEventCommand(index, event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
