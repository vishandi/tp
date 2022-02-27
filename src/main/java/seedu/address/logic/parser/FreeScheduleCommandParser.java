package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.logic.commands.schedule.FreeScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.IsPersonFreePredicate;

public class FreeScheduleCommandParser implements Parser<FreeScheduleCommand> {
    @Override
    public FreeScheduleCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TIME, PREFIX_DATE);

        boolean hasTimePrefix = argMultimap.getValue(PREFIX_TIME).isPresent();
        boolean hasDatePrefix = argMultimap.getValue(PREFIX_DATE).isPresent();

        if (hasTimePrefix && hasDatePrefix) {
            LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            LocalTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());

            return new FreeScheduleCommand(new IsPersonFreePredicate(time, date));
        } else if (hasTimePrefix) {
            LocalTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());

            return new FreeScheduleCommand(new IsPersonFreePredicate(time, LocalDate.now()));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeScheduleCommand.MESSAGE_USAGE));
        }
    }
}
