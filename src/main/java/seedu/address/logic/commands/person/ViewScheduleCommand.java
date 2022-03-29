package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.SamePersonPredicate;


public class ViewScheduleCommand extends Command {
    public static final String COMMAND_WORD = "viewSchedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": views the person's schedule identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_PERSON_SUCCESS = "Viewing %1$s's Schedule...";

    private final Index targetIndex;

    public ViewScheduleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToView = lastShownList.get(targetIndex.getZeroBased());
        model.updateViewSchedulePerson(new SamePersonPredicate(personToView));
        return new CommandResult(
                String.format(MESSAGE_VIEW_PERSON_SUCCESS, personToView.getName().value)
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewScheduleCommand // instanceof handles nulls
                && targetIndex.equals(((ViewScheduleCommand) other).targetIndex)); // state check
    }
}
