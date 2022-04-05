package seedu.address.model.schedule;

import static java.time.temporal.TemporalAdjusters.next;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

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
    public static final String DURATION_MESSAGE_CONSTRAINTS = "Event duration should be in HhMm, Hh, Mm or H format\n"
            + "eg. 3h30m, 2h, 30m, 3";
    public static final String TIME_MESSAGE_CONSTRAINTS = "Event time should be in HH:MM format";
    public static final String MISSING_TIME_MESSAGE = "The event start time must be specified "
            + "if the duration is specified!";
    public static final String MISSING_RECUR_FREQUENCY_CASE =
            "%s switch case is missing in Event::getNextRecurrenceDate! Returning initial date...";
    public static final String DURATION_RECUR_FREQ_MESSAGE_CONSTRAINTS =
            "Duration should not be longer than frequency of event!";

    private static final Logger logger = LogsCenter.getLogger(Event.class);
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

    /**
     * Returns true if the given event is valid.
     */
    public static boolean isValidEvent(Event event) {
        return EventDescription.isValidEventDescription(event.getEventDescription().toString());
    }

    /**
     * Returns true if {@code Duration} in {@code Event} is less than its {@code RecurFrequency}.
     *
     * @return true if duration in event is less than its recur frequency
     */
    public boolean isValidDurationWithRecurFrequency() {
        switch (recurFrequency) {
        case DAILY:
            if (duration.compareTo(Duration.ofDays(1)) > 0) {
                return false;
            }
            break;
        case WEEKLY:
            if (duration.compareTo(Duration.ofDays(7)) > 0) {
                return false;
            }
            break;
        case BIWEEKLY:
            if (duration.compareTo(Duration.ofDays(14)) > 0) {
                return false;
            }
            break;
        default:
            return true;
        }
        return true;
    }

    /**
     * Returns an {@code Event} with the same event description, time, duration and recur frequency,
     * but with the next recurring date if the {@code Event} has passed its end date.
     */
    public Event getNextRecurringEvent() {
        return new Event(getEventDescription(), getNextRecurrenceDate(), getTime(), getDuration(), getRecurFrequency());
    }

    /**
     * Returns the next recurring {@code LocalDate} of the {@code Event} (which can be today),
     * if the {@code Event} is recurring and the {@code Event} has passed its end date.
     * Otherwise, returns the {@code Event}'s current start date.
     */
    public LocalDate getNextRecurrenceDate() {
        LocalDate newDate = date;
        LocalDate today = LocalDate.now();
        switch (recurFrequency) {
        case NONE:
            return date;
        case DAILY:
            if (today.isAfter(getEndDate())) {
                newDate = today;
            }
            break;
        case WEEKLY:
            if (today.isAfter(getEndDate())) {
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                newDate = today.with(next(dayOfWeek));
            }
            break;
        case BIWEEKLY:
            if (today.isAfter(getEndDate())) {
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
     * Returns true if date clashes with event.
     *
     * @param date used to check against {@code Event}'s date
     * @return true if date clashes with {@code Event}
     */
    public boolean willDateCollide(LocalDate date) {
        long dateDiff = ChronoUnit.DAYS.between(this.date, date);
        // event has not started compared to date given.
        if (dateDiff < 0) {
            return false;
        }

        // event is what we are looking for
        if (dateDiff == 0) {
            return true;
        }

        // event that has past
        LocalDate endDate;
        LocalDate closestDate;
        switch (recurFrequency) {
        case DAILY:
            endDate = date;
            break;
        case WEEKLY:
            closestDate = this.date.plusDays(dateDiff - dateDiff % 7);
            endDate = LocalDateTime.of(closestDate, time).plus(duration).toLocalDate();
            break;
        case BIWEEKLY:
            closestDate = this.date.plusDays(dateDiff - dateDiff % 14);
            endDate = LocalDateTime.of(closestDate, time).plus(duration).toLocalDate();
            break;
        default:
            // case NONE and INVALID falls through to reach here
            endDate = getEndDate();
        }
        if (ChronoUnit.DAYS.between(date, endDate) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if date and time clashes with event.
     *
     * @param date used with time to check against {@code Event}'s date and time
     * @param time used with date to check against {@code Event}'s date and time
     * @return true if date and time clashes with {@code Event}
     */
    public boolean willDateTimeCollideEvent(LocalDate date, LocalTime time) {
        long dateDiff = ChronoUnit.DAYS.between(this.date, date);
        // event has not started compared to date given.
        if (dateDiff < 0) {
            return false;
        }

        // event that has past
        LocalDate endDate;
        LocalDate closestDate;
        switch (recurFrequency) {
        case WEEKLY:
            closestDate = this.date.plusDays(dateDiff - dateDiff % 7);
            endDate = LocalDateTime.of(closestDate, time).plus(duration).toLocalDate();
            break;
        case BIWEEKLY:
            closestDate = this.date.plusDays(dateDiff - dateDiff % 14);
            endDate = LocalDateTime.of(closestDate, time).plus(duration).toLocalDate();
            break;
        default:
            // case NONE and INVALID falls through to reach here
            endDate = getEndDate();
        }
        if (ChronoUnit.DAYS.between(date, endDate) >= 0) {
            LocalDateTime startDateTime = LocalDateTime.of(this.date, this.time);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, getEndTime());
            LocalDateTime toCheckDateTime = LocalDateTime.of(date, time);
            return (startDateTime.isEqual(toCheckDateTime)
                    || startDateTime.isBefore(toCheckDateTime)
                    && endDateTime.isAfter(toCheckDateTime));
        }
        return false;
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

    public String getDailyScheduleFormat() {
        String plusDays = "";
        long numDays = ChronoUnit.DAYS.between(getDate(), getEndDate());
        if (numDays > 0) {
            plusDays = String.format(" (+%s)", numDays);
        }
        return String.format("%s-%s%s %s %s", time, getEndTime(),
                plusDays, getRecurFrequency().getLabel(), eventDescription);
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
                && (otherEvent.willDateCollide(getDate()) || willDateCollide(otherEvent.getDate()))
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
        return String.format("%s %s-%s%s %s %s", date.format(
                        DateTimeFormatter.ofPattern("dd-MMM-yyyy")), time, getEndTime(),
                plusDays, getRecurFrequency().getLabel(), eventDescription);
    }

}
