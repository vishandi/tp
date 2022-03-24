package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Schedule {

    public static final String EMPTY_SCHEDULE_MESSAGE = "No schedule recorded yet.";
    public static final String MESSAGE_CONSTRAINTS =
            "A Schedule's Events must have alphanumeric event descriptions, date formats YYYY-MM-DD, "
                    + "time formats HH:MM and duration format in hours";
    public static final Schedule EMPTY_SCHEDULE = new Schedule(new ArrayList<>());
    private final List<Event> events = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Schedule(List<Event> events) {
        requireAllNonNull(events);
        this.events.addAll(events);
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
     * Returns an Event object at specified index
     *
     * @param index of the Event object to retrieve
     * @return an Event object
     */
    public Event getEvent(int index) {
        return events.get(index);
    }

    /**
     * Returns true if the given schedule is valid.
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
     * Returns true if the schedule is empty.
     */
    public static boolean isEmptySchedule(Schedule test) {
        return test.getEvents().isEmpty();
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
