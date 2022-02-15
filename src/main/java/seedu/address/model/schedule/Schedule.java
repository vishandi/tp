package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Schedule {

    private List<Event> events;

    /**
     * Every field must be present and not null.
     */
    public Schedule(List<Event> events) {
        requireAllNonNull(events);
        this.events = events;
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void setEvents(List<Event> events) {
        this.events = events;
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
