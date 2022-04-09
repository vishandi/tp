package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.schedule.Event.DURATION_RECUR_FREQ_MESSAGE_CONSTRAINTS;
import static seedu.address.model.schedule.Schedule.MESSAGE_DUPLICATE_EVENT;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditUtil.EditEventDescriptor;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.SamePersonPredicate;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.schedule.RecurFrequency;
import seedu.address.model.schedule.Schedule;


/**
 * Edits an event of an existing person in the schedule of address book.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "editEvent";
    public static final String COMMAND_WORD_LOWER = "editevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an event of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX EVENT_NUMBER (must be a positive integer) "
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
    private Event editedEvent; // this is to retrieve the correct edited event for display message

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
        model.setSchedule(personToEdit, updatedSchedule);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateViewSchedulePerson(new SamePersonPredicate(personToEdit));

        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Schedule} with the details of {@code scheduleToEdit}
     * edited with {@code editEventDescriptor} at {@code targetEventIndex}.
     */
    private Schedule createEditedSchedule(Schedule scheduleToEdit, Index targetEventIndex,
            EditEventDescriptor editEventDescriptor) throws CommandException {
        assert scheduleToEdit != null;

        List<Event> scheduleEvents = scheduleToEdit.getEvents();
        ArrayList<Event> updatedEvents = new ArrayList<>(scheduleEvents);

        Event toEditEvent = updatedEvents.remove(targetEventIndex.getZeroBased());
        Event updatedEvent = createEditedEvent(toEditEvent, editEventDescriptor);
        if (updatedEvents.contains(updatedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (!updatedEvent.isValidDurationWithRecurFrequency()) {
            throw new CommandException(DURATION_RECUR_FREQ_MESSAGE_CONSTRAINTS);
        }

        editedEvent = updatedEvent;
        updatedEvents.add(targetEventIndex.getZeroBased(), updatedEvent);
        Collections.sort(updatedEvents);
        return new Schedule(updatedEvents);
    }

    /**
     * Creates and returns an {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        EventDescription updatedEventDescription =
                editEventDescriptor.getEventDescription().orElse(eventToEdit.getEventDescription());
        LocalDate updatedDate = editEventDescriptor.getDate().orElse(eventToEdit.getDate());
        LocalTime updatedTime = editEventDescriptor.getTime().orElse(eventToEdit.getTime());
        Duration updatedDuration = editEventDescriptor.getDuration().orElse(eventToEdit.getDuration());
        RecurFrequency updatedRecurFrequency = editEventDescriptor.getRecurFrequency()
                .orElse(eventToEdit.getRecurFrequency());
        return new Event(updatedEventDescription, updatedDate, updatedTime, updatedDuration, updatedRecurFrequency);
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
