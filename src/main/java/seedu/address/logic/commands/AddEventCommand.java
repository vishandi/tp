package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Adds an event to the person"
            + "identified by index number";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "AddEvent command not implemented yet";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, AddEvent: %2$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
