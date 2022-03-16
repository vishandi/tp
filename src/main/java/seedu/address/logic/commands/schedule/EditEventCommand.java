package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditUtil.EditEventDescriptor;
import seedu.address.logic.EditUtil.EditPersonDescriptor;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditTypeCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.Schedule;


/**
 * Edits an event of an existing person in the schedule of address book.
 */
public class EditEventCommand extends EditTypeCommand {

    public static final String COMMAND_WORD = "editEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a event of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDICES (must be a positive integer) "
            + "[" + PREFIX_EVENT_DESCRIPTION + "EVENT DESCRIPTION] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_DURATION + "DURATION] "
            + "[" + PREFIX_RECUR_FREQUENCY + "RECUR_FREQUENCY]\n"
            + "Example: " + COMMAND_WORD + " 1 2 "
            + PREFIX_TIME + "10:00";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index targetIndex;
    private final Index targetEventIndex;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param targetEventIndex of the event in the schedule to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditEventCommand(Index targetIndex, Index targetEventIndex, EditEventDescriptor editEventDescriptor) {
        this.targetIndex = targetIndex;
        this.targetEventIndex = targetEventIndex;
        this.editEventDescriptor = editEventDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Schedule scheduleToEdit = personToEdit.getSchedule();

        if (targetEventIndex.getZeroBased() >= scheduleToEdit.getEvents().size()) {
            throw new CommandException(MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Schedule updatedSchedule = createEditedSchedule(scheduleToEdit, targetEventIndex, editEventDescriptor);
        Event editedEvent = updatedSchedule.getEvent(targetEventIndex.getZeroBased());
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        editPersonDescriptor.setSchedule(updatedSchedule);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        // state check
        EditEventCommand e = (EditEventCommand) other;
        return targetIndex.equals(e.targetIndex)
                && targetEventIndex.equals(e.targetEventIndex)
                && editEventDescriptor.equals(e.editEventDescriptor);
    }
}
