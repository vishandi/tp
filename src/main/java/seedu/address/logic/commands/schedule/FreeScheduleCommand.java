package seedu.address.logic.commands.schedule;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class FreeScheduleCommand extends Command {

    public static final String COMMAND_WORD = "freeSchedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Retrieves information of friends "
            + "who are free at the specified time or date\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TIME + "TIME"
            + "[" + PREFIX_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TIME + "12:00 "
            + PREFIX_DATE + "2022-02-14";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Hello from freeSchedule");
    }
}
