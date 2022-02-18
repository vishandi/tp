package seedu.address.model.schedule;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a scheduled Event.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    private final EventDescription eventDescription;
    private final LocalDate date;
    private final LocalTime time;
    private final Duration duration;

    private static final String PLACEHOLDER_EVENT_DESCRIPTION = "Event Description";
    public static final String MESSAGE_CONSTRAINTS = "Events should be of format EVENT_DESCRIPTION YYYY-MM-DD HH:MM";

    private Event() {
        this.eventDescription = new EventDescription(PLACEHOLDER_EVENT_DESCRIPTION);
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.duration = Duration.ZERO;
    }

    /**
     * Every field must be present and not null.
     */
    public Event(EventDescription eventDescription, LocalDate date, LocalTime time, Duration duration) {
        this.eventDescription = eventDescription;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Duration getDuration() {
        return duration;
    }

    public EventDescription getEventDescription() {
        return eventDescription;
    }

    /**
     * Returns true if the given event is valid.
     */
    public static boolean isValidEvent(Event event) {
        return EventDescription.isValidEventDescription(event.getEventDescription().toString());
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
                && otherEvent.getTime().equals(getTime())
                && otherEvent.getDuration().equals(getDuration());
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", eventDescription, date, time, duration);
    }

}
