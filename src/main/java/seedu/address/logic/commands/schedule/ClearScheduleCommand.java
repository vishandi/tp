package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.SamePersonPredicate;
import seedu.address.model.schedule.Schedule;


/**
 * Clears the {@code Schedule} of an existing person in the address book.
 */
public class ClearScheduleCommand extends Command {

    public static final String COMMAND_WORD = "clearSchedule";
    public static final String COMMAND_WORD_LOWER = "clearschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears the schedule of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_CLEAR_SCHEDULE_SUCCESS = "%s's schedule has been cleared!";

    private final Index targetIndex;

    /**
     * @param targetIndex of the person in the filtered person list to edit
     */
    public ClearScheduleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());

        model.setSchedule(personToEdit, new Schedule(new ArrayList<>()));
        model.updateViewSchedulePerson(new SamePersonPredicate(personToEdit));
        return new CommandResult(String.format(MESSAGE_CLEAR_SCHEDULE_SUCCESS, personToEdit.getName()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClearScheduleCommand)) {
            return false;
        }

        // state check
        ClearScheduleCommand e = (ClearScheduleCommand) other;
        return targetIndex.equals(e.targetIndex);
    }
}
