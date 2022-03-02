package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditUtil.EditPersonDescriptor;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditTypeCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Schedule;


/**
 * Deletes an event of an existing person in the schedule of address book.
 */
public class DeleteEventCommand extends EditTypeCommand {

    public static final String COMMAND_WORD = "deleteEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an event of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDICES (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "%1$s's No.%d event deleted successfully";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";

    private final Index targetIndex;
    private final Index targetEventIndex;

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param targetEventIndex of the event in the schedule to delete
     */
    public DeleteEventCommand(Index targetIndex, Index targetEventIndex) {
        this.targetIndex = targetIndex;
        this.targetEventIndex = targetEventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Schedule scheduleToEdit = personToEdit.getSchedule();

        if (targetEventIndex.getZeroBased() >= scheduleToEdit.getEvents().size()) {
            throw new CommandException(MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Schedule updatedSchedule = createDeletedSchedule(scheduleToEdit, targetEventIndex);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        editPersonDescriptor.setSchedule(updatedSchedule);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS,
                personToEdit.getName(),
                targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteEventCommand)) {
            return false;
        }

        // state check
        DeleteEventCommand e = (DeleteEventCommand) other;
        return targetIndex.equals(e.targetIndex)
                && targetEventIndex.equals(e.targetEventIndex);
    }
}
