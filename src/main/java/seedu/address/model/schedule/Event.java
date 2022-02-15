package seedu.address.model.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a scheduled Event.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    private EventDescription eventDescription;
    private LocalDate date;
    private LocalTime time;

    /**
     * Every field must be present and not null.
     */
    public Event(EventDescription eventDescription, LocalDate date, LocalTime time) {
        this.eventDescription = eventDescription;
        this.date = date;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public EventDescription getEventDescription() {
        return eventDescription;
    }

    /**
     * Returns true if both schedules have the same list of events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getEventDescription().equals(getEventDescription())
                && otherEvent.getDate().equals(getDate())
                && otherEvent.getTime().equals(getTime());
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", eventDescription, date, time);
    }

}
