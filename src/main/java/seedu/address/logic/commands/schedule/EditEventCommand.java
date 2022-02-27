package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;

/**
 * Edits an event of an existing person in the schedule of address book.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "editEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a event of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDICES (must be a positive integer) "
            + "[" + PREFIX_EVENT_DESCRIPTION + "EVENT DESCRIPTION] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_DURATION + "DURATION] "
            + "Example: " + COMMAND_WORD + " 1 2 "
            + PREFIX_TIME + "10:00";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "%1$s's event edited successfully";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
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
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Schedule scheduleToEdit = personToEdit.getSchedule();

        if (targetEventIndex.getZeroBased() >= scheduleToEdit.getEvents().size()) {
            throw new CommandException(MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Schedule updatedSchedule = createEditedSchedule(scheduleToEdit, targetEventIndex, editEventDescriptor);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        editPersonDescriptor.setSchedule(updatedSchedule);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, personToEdit.getName()));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Schedule updatedSchedule = editPersonDescriptor.getSchedule().orElse(personToEdit.getSchedule());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedSchedule, updatedTags);
    }

    /**
     * Creates and returns an {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        EventDescription updatedEventDescription =
                editEventDescriptor.getEventDescription().orElse(eventToEdit.getEventDescription());
        LocalDate updatedDate = editEventDescriptor.getDate().orElse(eventToEdit.getDate());
        LocalTime updatedTime = editEventDescriptor.getTime().orElse(eventToEdit.getTime());
        Duration updatedDuration = editEventDescriptor.getDuration().orElse(eventToEdit.getDuration());

        return new Event(updatedEventDescription, updatedDate, updatedTime, updatedDuration);
    }

    /**
     * Creates and returns a {@code Schedule} with the details of {@code scheduleToEdit}
     * edited with {@code editPersonDescriptor} at {@code targetEventIndex}.
     */
    private static Schedule createEditedSchedule(
            Schedule scheduleToEdit, Index targetEventIndex, EditEventDescriptor editEventDescriptor) {
        assert scheduleToEdit != null;

        List<Event> scheduleEvents = scheduleToEdit.getEvents();
        ArrayList<Event> updatedEvents = new ArrayList<>();

        int counter = 0;
        for (Event toEditEvent : scheduleEvents) {
            Event updatedEvent;
            if (counter == targetEventIndex.getZeroBased()) {
                updatedEvent = createEditedEvent(toEditEvent, editEventDescriptor);
            } else {
                updatedEvent = toEditEvent;
            }
            updatedEvents.add(updatedEvent);
            counter++;
        }
        scheduleToEdit.setEvents(updatedEvents);

        return scheduleToEdit;
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

    public static class EditEventDescriptor {
        private EventDescription eventDescription;
        private LocalDate date;
        private LocalTime time;
        private Duration duration;

        public EditEventDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setEventDescription(toCopy.eventDescription);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setDuration(toCopy.duration);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(eventDescription, date, time, duration);
        }

        public void setEventDescription(EventDescription eventDescription) {
            this.eventDescription = eventDescription;
        }

        public Optional<EventDescription> getEventDescription() {
            return Optional.ofNullable(eventDescription);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setTime(LocalTime time) {
            this.time = time;
        }

        public Optional<LocalTime> getTime() {
            return Optional.ofNullable(time);
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public Optional<Duration> getDuration() {
            return Optional.ofNullable(duration);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getEventDescription().equals(e.getEventDescription())
                    && getDate().equals(e.getDate())
                    && getTime().equals(e.getTime())
                    && getDuration().equals(e.getDuration());
        }
    }
}
