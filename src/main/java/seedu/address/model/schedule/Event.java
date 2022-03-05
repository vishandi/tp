package seedu.address.model.schedule;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a scheduled Event.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {
    public static final String DEFAULT_TIME = "00:00";
    public static final String DEFAULT_DURATION = "2H";
    public static final String FULL_DAY_EVENT_DURATION = "24H";
    public static final String DATE_MESSAGE_CONSTRAINTS = "Event date should be in YYYY-MM-DD format";
    public static final String DURATION_MESSAGE_CONSTRAINTS = "Event duration should be in XHYM, XHY, XH or X format,"
            + " where X is an integer representing the number of hours"
            + " and Y is an integer representing the number of minutes.";
    public static final String TIME_MESSAGE_CONSTRAINTS = "Event time should be in HH:MM format";
    public static final String MISSING_TIME_MESSAGE = "The event start time must be specified "
            + "if the duration is specified!";

    private static final String PLACEHOLDER_EVENT_DESCRIPTION = "Event Description";
    private final EventDescription eventDescription;
    private final LocalDate date;
    private final LocalTime time;
    private final Duration duration;

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

    public LocalTime getEndTime() {
        return time.plus(duration);
    }

    public LocalDate getEndDate() {
        LocalDateTime endDateTime = date.atTime(time).plus(duration);
        return endDateTime.toLocalDate();
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
        return String.format("%s %s %s-%s", eventDescription,
                date.format(DateTimeFormatter.ofPattern("dd-MMM-YYYY")), time, getEndTime());
    }

}
