package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import seedu.address.model.recurfrequency.RecurFrequency;

/**
 * Represents a scheduled Event.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements Comparable<Event> {

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
    private final RecurFrequency recurFrequency;

    private Event() {
        this.eventDescription = new EventDescription(PLACEHOLDER_EVENT_DESCRIPTION);
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.duration = Duration.ZERO;
        this.recurFrequency = null;
    }

    /**
     * Every field must be present, and only recurFrequency can be null.
     */
    public Event(EventDescription eventDescription, LocalDate date, LocalTime time, Duration duration,
                 RecurFrequency recurFrequency) {
        requireAllNonNull(eventDescription, date, time, duration);
        this.eventDescription = eventDescription;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.recurFrequency = recurFrequency;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getEndDate() {
        return date.atTime(time).plus(duration).toLocalDate();
    }

    public LocalTime getEndTime() {
        return time.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public EventDescription getEventDescription() {
        return eventDescription;
    }

    public Optional<RecurFrequency> getRecurFrequency() {
        return Optional.ofNullable(recurFrequency);
    }

    /**
     * Returns true if the Event is recurring.
     * @return
     */
    public boolean isRecurring() {
        return getRecurFrequency().isPresent();
    }

    /**
     * Returns true if the given event is valid.
     */
    public static boolean isValidEvent(Event event) {
        return EventDescription.isValidEventDescription(event.getEventDescription().toString());
    }

    /**
     * Compares 2 {@code Event} based on date and time. Returns a positive integer if {@code event}
     * occurs after the caller, a negative integer if {@code event} occurs before the caller, and 0
     * if both {@code Event} have occurs on the same date and time.
     */
    public int compareTo(Event event) {
        LocalDate eventDate = event.getDate();
        if (date.isBefore(eventDate)) {
            return -1;
        } else if (date.isAfter(eventDate)) {
            return 1;
        }

        LocalTime eventTime = event.getTime();
        if (time.isBefore(eventTime)) {
            return -1;
        } else if (time.isAfter(eventTime)) {
            return 1;
        } else {
            return 0;
        }
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
                && otherEvent.getDuration().equals(getDuration())
                && otherEvent.getRecurFrequency().equals(getRecurFrequency());
    }

    @Override
    public String toString() {
        String plusDays = "";
        long numDays = ChronoUnit.DAYS.between(getDate(), getEndDate());
        if (numDays > 0) {
            plusDays = String.format(" (+%s)", numDays);
        }

        return String.format("%s %s %s-%s%s%s", eventDescription, date.format(
                        DateTimeFormatter.ofPattern("dd-MMM-YYYY")), time, getEndTime(),
                plusDays, getRecurFrequency().map(x -> " " + x).orElse(""));
    }

}
