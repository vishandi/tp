package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Schedule {

    public static final String MESSAGE_CONSTRAINTS =
            "A Schedule's Events must have alphanumeric event descriptions, date formats YYYY-MM-DD, "
                    + "time formats HH:MM and duration format in hours";
    private List<Event> events;

    private Schedule() {
        this.events = new ArrayList<>();
    }

    /**
     * Every field must be present and not null.
     */
    public Schedule(List<Event> events) {
        requireAllNonNull(events);
        this.events = events;
    }

    /**
     * Returns a List of Event objects in the Schedule.
     *
     * @return a List of Event objects in the Schedule
     */
    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    /**
     * Adds an Event to the Schedule.
     *
     * @param event the Event to add to the Schedule
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Sets the List of Event objects in the Schedule to the corresponding List.
     *
     * @param events the List of Event objects to be set in the Schedule
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * Returns true if the given event is valid.
     */
    public static boolean isValidSchedule(Schedule schedule) {
        for (Event event : schedule.getEvents()) {
            if (!Event.isValidEvent(event)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if both schedules have the same list of events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return otherSchedule.getEvents().equals(getEvents());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(events);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        int counter = 1;
        for (Event event : events) {
            builder.append(String.format("%s. %s\n", counter, event));
            counter += 1;
        }

        return builder.toString();
    }

}
