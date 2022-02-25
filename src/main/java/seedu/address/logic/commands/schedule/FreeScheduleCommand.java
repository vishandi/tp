package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.schedule.EventCollideWithTimeDatePredicate;

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

    private final EventCollideWithTimeDatePredicate predicate;

    public FreeScheduleCommand(EventCollideWithTimeDatePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FreeScheduleCommand // instanceof handles nulls
                && predicate.equals(((FreeScheduleCommand) other).predicate)); // state check
    }
}
