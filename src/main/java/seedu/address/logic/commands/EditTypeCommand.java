package seedu.address.logic.commands;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditUtil.EditEventDescriptor;
import seedu.address.logic.EditUtil.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;
import seedu.address.model.person.Telegram;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.schedule.RecurFrequency;
import seedu.address.model.schedule.Schedule;

public abstract class EditTypeCommand extends Command {
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    protected static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Telegram updatedTelegram = editPersonDescriptor.getTelegram().orElse(personToEdit.getTelegram());
        GitHub updatedGithub = editPersonDescriptor.getGithub().orElse(personToEdit.getGithub());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Schedule updatedSchedule = editPersonDescriptor.getSchedule().orElse(personToEdit.getSchedule());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedTelegram, updatedGithub,
                updatedEmail, updatedAddress, updatedSchedule, updatedTags);
    }

    /**
     * Creates and returns a {@code Schedule} with the details of {@code scheduleToEdit}
     * edited with {@code editEventDescriptor} at {@code targetEventIndex}.
     */
    protected static Schedule createEditedSchedule(
            Schedule scheduleToEdit, Index targetEventIndex, EditEventDescriptor editEventDescriptor) {
        assert scheduleToEdit != null;

        List<Event> scheduleEvents = scheduleToEdit.getEvents();
        ArrayList<Event> updatedEvents = new ArrayList<>(scheduleEvents);

        Event toEditEvent = updatedEvents.remove(targetEventIndex.getZeroBased());
        Event updatedEvent = createEditedEvent(toEditEvent, editEventDescriptor);
        updatedEvents.add(targetEventIndex.getZeroBased(), updatedEvent);
        Collections.sort(updatedEvents);
        return new Schedule(updatedEvents);
    }

    /**
     * Creates and returns a {@code Schedule} with the details of {@code eventToEdit}
     * with an added {@code eventToAdd}.
     */
    protected static Schedule createEditedSchedule(Schedule scheduleToEdit, Event eventToAdd) {
        assert scheduleToEdit != null;

        List<Event> scheduleEvents = scheduleToEdit.getEvents();
        ArrayList<Event> updatedEvents = new ArrayList<>(scheduleEvents);
        updatedEvents.add(eventToAdd);
        Collections.sort(updatedEvents);
        return new Schedule(updatedEvents);
    }

    protected static Schedule createDeletedSchedule(Schedule scheduleToEdit, Index targetIndex) {
        assert scheduleToEdit != null;

        List<Event> scheduleEvents = scheduleToEdit.getEvents();
        ArrayList<Event> updatedEvents = new ArrayList<>(scheduleEvents);
        updatedEvents.remove(targetIndex.getZeroBased());

        return new Schedule(updatedEvents);
    }

    /**
     * Creates and returns an {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    protected static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
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
}
