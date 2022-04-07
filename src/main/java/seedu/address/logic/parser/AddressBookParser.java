package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.commands.person.ClearCommand;
import seedu.address.logic.commands.person.DeleteCommand;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.commands.person.FindCommand;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.commands.person.SetUserCommand;
import seedu.address.logic.commands.person.ViewGroupCommand;
import seedu.address.logic.commands.person.ViewScheduleCommand;
import seedu.address.logic.commands.schedule.AddEventCommand;
import seedu.address.logic.commands.schedule.ClearScheduleCommand;
import seedu.address.logic.commands.schedule.DeleteEventCommand;
import seedu.address.logic.commands.schedule.EditEventCommand;
import seedu.address.logic.commands.schedule.ExportScheduleCommand;
import seedu.address.logic.commands.schedule.FindCommonTimingCommand;
import seedu.address.logic.commands.schedule.ImportScheduleCommand;
import seedu.address.logic.commands.schedule.WhoIsFreeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.person.AddCommandParser;
import seedu.address.logic.parser.person.DeleteCommandParser;
import seedu.address.logic.parser.person.EditCommandParser;
import seedu.address.logic.parser.person.FindCommandParser;
import seedu.address.logic.parser.person.SetUserCommandParser;
import seedu.address.logic.parser.person.ViewGroupCommandParser;
import seedu.address.logic.parser.person.ViewScheduleCommandParser;
import seedu.address.logic.parser.schedule.AddEventCommandParser;
import seedu.address.logic.parser.schedule.ClearScheduleCommandParser;
import seedu.address.logic.parser.schedule.DeleteEventCommandParser;
import seedu.address.logic.parser.schedule.EditEventCommandParser;
import seedu.address.logic.parser.schedule.ExportScheduleCommandParser;
import seedu.address.logic.parser.schedule.FindCommonTimingCommandParser;
import seedu.address.logic.parser.schedule.ImportScheduleCommandParser;
import seedu.address.logic.parser.schedule.WhoIsFreeCommandParser;


/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord.toLowerCase()) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case ViewGroupCommand.COMMAND_WORD_LOWER:
            return new ViewGroupCommandParser().parse(arguments);
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case AddEventCommand.COMMAND_WORD_LOWER:
            return new AddEventCommandParser().parse(arguments);
        case EditEventCommand.COMMAND_WORD_LOWER:
            return new EditEventCommandParser().parse(arguments);
        case DeleteEventCommand.COMMAND_WORD_LOWER:
            return new DeleteEventCommandParser().parse(arguments);
        case WhoIsFreeCommand.COMMAND_WORD_LOWER:
            return new WhoIsFreeCommandParser().parse(arguments);
        case ClearScheduleCommand.COMMAND_WORD_LOWER:
            return new ClearScheduleCommandParser().parse(arguments);
        case ViewScheduleCommand.COMMAND_WORD_LOWER:
            return new ViewScheduleCommandParser().parse(arguments);
        case ImportScheduleCommand.COMMAND_WORD_LOWER:
            return new ImportScheduleCommandParser().parse(arguments);
        case ExportScheduleCommand.COMMAND_WORD_LOWER:
            return new ExportScheduleCommandParser().parse(arguments);
        case SetUserCommand.COMMAND_WORD_LOWER:
            return new SetUserCommandParser().parse(arguments);
        case FindCommonTimingCommand.COMMAND_WORD_LOWER:
            return new FindCommonTimingCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
