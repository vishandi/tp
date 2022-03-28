package seedu.address.logic.parser.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.nio.file.Path;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedule.ImportScheduleCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportScheduleCommand object.
 */
public class ImportScheduleCommandParser implements Parser<ImportScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportScheduleCommand
     * and returns an ImportScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH);

        if (!argMultimap.getValue(PREFIX_FILEPATH).isPresent() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportScheduleCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportScheduleCommand.MESSAGE_USAGE), pe);
        }

        Path filePath = ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_FILEPATH).get());

        return new ImportScheduleCommand(index, filePath);
    }
}
