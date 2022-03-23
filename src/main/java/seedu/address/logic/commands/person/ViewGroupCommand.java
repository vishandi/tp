package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.IsTagInPersonPredicate;

/**
 * Command that lists all contacts who share the same tag.
 */
public class ViewGroupCommand extends Command {

    public static final String COMMAND_WORD = "viewGroup";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the contacts who share the same tag "
            + "Parameters: "
            + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "owesMoney";
    private final IsTagInPersonPredicate predicate;

    public ViewGroupCommand(IsTagInPersonPredicate predicate) {
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
                || (other instanceof ViewGroupCommand // instanceof handles nulls
                && predicate.equals(((ViewGroupCommand) other).predicate)); // state check
    }
}
