package seedu.address.model.schedule;

import static java.time.temporal.TemporalAdjusters.next;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * Represents a scheduled Event.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements Comparable<Event> {

    public static final String DEFAULT_DATE = "2022-12-20";
    public static final String DEFAULT_TIME = "00:00";
    public static final String DEFAULT_DURATION = "2H";
    public static final String DEFAULT_EVENT_DESCRIPTION = "CS2103T Tutorial";
    public static final String FULL_DAY_EVENT_DURATION = "24H";
    public static final String DATE_MESSAGE_CONSTRAINTS = "Event date should be in YYYY-MM-DD format";
    public static final String DURATION_MESSAGE_CONSTRAINTS = "Event duration should be in"
            + " XHYM, XH, YM or X format,"
            + " where X is an integer representing the number of hours"
            + " and Y is an integer representing the number of minutes.";
    public static final String TIME_MESSAGE_CONSTRAINTS = "Event time should be in HH:MM format";
    public static final String MISSING_TIME_MESSAGE = "The event start time must be specified "
            + "if the duration is specified!";
    public static final String MISSING_RECUR_FREQUENCY_CASE =
            "%s switch case is missing in Event::getNextRecurrenceDate! Returning initial date...";

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private final EventDescription eventDescription;
    private final LocalDate date;
    private final LocalTime time;
    private final Duration duration;
    private final RecurFrequency recurFrequency;

    /**
     * Every field must be present, and only recurFrequency can be null.
     */
    public Event(EventDescription eventDescription, LocalDate date, LocalTime time, Duration duration,
                 RecurFrequency recurFrequency) {
        requireAllNonNull(eventDescription, date, time, duration, recurFrequency);
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

    public RecurFrequency getRecurFrequency() {
        return recurFrequency;
    }

    public Event getNextRecurringEvent() {
        return new Event(getEventDescription(), getNextRecurrenceDate(), getTime(), getDuration(), getRecurFrequency());
    }

    /**
     * Returns true if the given event is valid.
     */
    public static boolean isValidEvent(Event event) {
        return EventDescription.isValidEventDescription(event.getEventDescription().toString());
    }

    /**
     * Returns the next recurring {@code LocalDate} of the {@code Event}, which can be today,
     * if the {@code Event} is recurring. Otherwise, returns the {@code Event} date.
     */
    public LocalDate getNextRecurrenceDate() {
        LocalDate newDate = date;
        LocalDate today = LocalDate.now();
        switch (recurFrequency) {
        case NONE:
            return date;
        case DAILY:
            if (today.isAfter(date)) {
                newDate = today;
            }
            break;
        case WEEKLY:
            if (today.isAfter(date)) {
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                newDate = today.with(next(dayOfWeek));
            }
            break;
        case BIWEEKLY:
            if (today.isAfter(date)) {
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                newDate = today.with(next(dayOfWeek));
                if (ChronoUnit.DAYS.between(date, newDate) % 14 != 0) {
                    newDate = newDate.with(next(dayOfWeek));
                }
            }
            break;
        default:
            logger.warning(String.format(MISSING_RECUR_FREQUENCY_CASE, recurFrequency));
        }
        return newDate;
    }

    /**
     * Returns true if date collides with {@code Event}'s date and recurrence
     *
     * @param date used to check against {@code Event}'s date
     * @return true if date collides with {@code Event}'s date and recurrence
     */
    public boolean willDateCollide(LocalDate date) {
        if (this.date.isAfter(date)) {
            // if start date of event is after the date we are checking, then they will never collide
            return false;
        }

        switch (recurFrequency) {
        case DAILY:
            return true;
        case WEEKLY:
            return this.date.getDayOfWeek().equals(date.getDayOfWeek());
        case BIWEEKLY:
            return (ChronoUnit.DAYS.between(this.date, date) % 14 == 0);
        default:
            // case NONE and INVALID falls through to reach here
            return this.date.equals(date);
        }
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
        }

        Duration eventDuration = event.getDuration();
        return duration.compareTo(eventDuration);
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
        return String.format("%s %s %s-%s%s %s", eventDescription, date.format(
                        DateTimeFormatter.ofPattern("dd-MMM-yyyy")), time, getEndTime(),
                plusDays, getRecurFrequency().getLabel());
    }

}
