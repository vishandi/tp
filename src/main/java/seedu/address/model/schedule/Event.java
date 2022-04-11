package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents a scheduled Event.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements Comparable<Event> {

    public static final String DEFAULT_TIME = "00:00";
    public static final String DEFAULT_DURATION = "2H";
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
     * Returns the date that is closest to the given date that is either
     * a. still ongoing, or
     * b. going to happen.
     * (a) will be prioritized over (b) if there is an event to happen twice at {@code relativeDate}.
     * The event will happen sometime in {@code relativeDate} in either cases.
     *
     * @param relativeDate is the relative date that we are comparing to
     * @return the closest start date of event that is still ongoing or has already occurred
     */
    public LocalDate getClosestStartDate(LocalDate relativeDate) {
        long dateDiff = ChronoUnit.DAYS.between(this.date, relativeDate);
        // event has not started compared to date given.
        if (dateDiff <= 0) {
            return date;
        }
        // event that has past
        LocalDate startDate;
        LocalDateTime endDateTime;
        switch (recurFrequency) {
        case NONE:
            startDate = date;
            break;
        case DAILY:
            startDate = relativeDate.minusDays(1);
            endDateTime = LocalDateTime.of(startDate, time).plus(duration);
            if (!endDateTime.isAfter(LocalDateTime.of(relativeDate, LocalTime.MIDNIGHT))) {
                startDate = startDate.plusDays(1);
            }
            break;
        case WEEKLY:
            startDate = date.plusDays(dateDiff - dateDiff % 7);
            if (dateDiff % 7 == 0) {
                startDate = relativeDate.minusDays(7);
            }
            endDateTime = LocalDateTime.of(startDate, time).plus(duration);
            if (!endDateTime.isAfter(LocalDateTime.of(relativeDate, LocalTime.MIDNIGHT))) {
                startDate = startDate.plusDays(7);
            }
            break;
        case BIWEEKLY:
            startDate = date.plusDays(dateDiff - dateDiff % 14);
            if (dateDiff % 14 == 0) {
                startDate = relativeDate.minusDays(14);
            }
            endDateTime = LocalDateTime.of(startDate, time).plus(duration);
            if (!endDateTime.isAfter(LocalDateTime.of(relativeDate, LocalTime.MIDNIGHT))) {
                startDate = startDate.plusDays(14);
            }
            break;
        default:
            // case NONE and INVALID falls through to reach here
            startDate = date;
            logger.warning(String.format(MISSING_RECUR_FREQUENCY_CASE, recurFrequency));
        }
        return startDate;
    }

    /**
     * Returns the end date of the event closest to the given date.
     *
     * @param relativeDate is the relative date that we are comparing to
     * @return the closest end date of event that is either still ongoing or has already occurred
     */
    public LocalDate getClosestEndDate(LocalDate relativeDate) {
        long dateDiff = ChronoUnit.DAYS.between(date, relativeDate);
        // event has not started compared to date given.
        if (dateDiff < 0) {
            return date;
        }

        // event that has past
        LocalDate closestStartDate = getClosestStartDate(relativeDate);
        LocalDate endDate = LocalDateTime.of(closestStartDate, time).plus(duration).toLocalDate();

        return endDate;
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
     * Returns the date this event will occur next, if it is a recurring event.
     * Otherwise, returns the date the event will happen.
     */
    public LocalDate getNextDate() {
        switch (recurFrequency) {
        case NONE:
            return date;
        case DAILY:
            return date.plusDays(1);
        case WEEKLY:
            return date.plusDays(7);
        case BIWEEKLY:
            return date.plusDays(14);
        default:
            logger.warning(String.format(MISSING_RECUR_FREQUENCY_CASE, recurFrequency));
        }
        return date;
    }

    /**
     * Returns the next Event of the next time this event will occur, if it is a recurring event.
     */
    public Event getNextEvent() {
        return new Event(getEventDescription(), getNextDate(), getTime(), getDuration(), getRecurFrequency());
    }

    /**
     * Returns an {@code Event} with the same event description, time, duration and recur frequency,
     * but with the next recurring date if the {@code Event} has passed its end date and time.
     */
    public Event getNextRecurringEvent(LocalDate relativeDate) {
        return new Event(getEventDescription(), getClosestStartDate(relativeDate), getTime(), getDuration(),
                getRecurFrequency());
    }

    /**
     * Returns true if date clashes with event.
            *
            * @param date used to check against {@code Event}'s date
            * @return true if date clashes with {@code Event}
     */
    public boolean willDateCollide(LocalDate date) {
        LocalDate closestStartDate = getClosestStartDate(date);
        LocalDate closestEndDate = getClosestEndDate(date);
        return (closestStartDate.isBefore(date) || closestStartDate.isEqual(date))
                && (closestEndDate.isAfter(date) || closestEndDate.isEqual(date));
    }

    /**
     * Returns true if the event is happening sometime in {@param date}.
     */
    public boolean isCollidingAtDate(LocalDate date) {
        return (getDate().isBefore(date) && getEndDate().isAfter(date))
                || getDate().isEqual(date) || getEndDate().isEqual(date);
    }

    /**
     * Returns true if date and time clashes with event.
     *
     * @param date used with time to check against {@code Event}'s date and time
     * @param time used with date to check against {@code Event}'s date and time
     * @return true if date and time clashes with {@code Event}
     */
    public boolean willDateTimeCollideEvent(LocalDate date, LocalTime time) {
        List<Event> events = getEventsAtDate(date);
        boolean willCollideEvent = false;
        for (Event event : events) {
            LocalDate closestStartDate = event.getClosestStartDate(date);
            LocalDate closestEndDate = event.getClosestEndDate(date);

            if (ChronoUnit.DAYS.between(date, closestEndDate) >= 0) {
                LocalDateTime startDateTime = LocalDateTime.of(closestStartDate, event.getTime());
                LocalDateTime endDateTime = LocalDateTime.of(closestEndDate, event.getEndTime());
                LocalDateTime toCheckDateTime = LocalDateTime.of(date, time);

                willCollideEvent = willCollideEvent
                        || ((startDateTime.isEqual(toCheckDateTime)
                        || startDateTime.isBefore(toCheckDateTime)
                        && endDateTime.isAfter(toCheckDateTime)));
            }
        }
        return willCollideEvent;
    }

    /**
     * Splits the Event into multiple Events by date of occurrence and returns a list of the split Events
     * that occur during the specified {@code date}
     *
     * @param date used to check.
     * @return a list of Events for that particular date.
     */
    public List<Event> getEventsAtDate(LocalDate date) {
        ArrayList<Event> eventsAtDate = new ArrayList<>();

        // nextEvent will occur sometime at date.
        Event nextEvent = getNextRecurringEvent(date);

        if (date.isBefore(getDate()) || !willDateCollide(date)) {
            return eventsAtDate;
        }

        /*
        Cases:
        1. If the event starts before date and ends after date: full day event at date.
        2. If the event starts before date and ends at date: starts from 00:00 until the end time at date.
        3. If the event starts at date and ends after date: starts from start time until 00:00 (the next day) at date.
        4. If none of the above is true, it means the event starts and ends at date: the whole event is at date.
         */
        if (nextEvent.getDate().isBefore(date) && nextEvent.getEndDate().isAfter(date)) {
            eventsAtDate.add(new Event(nextEvent.eventDescription, date, LocalTime.MIDNIGHT,
                    Duration.ofHours(24), recurFrequency));
        } else if (nextEvent.getDate().isBefore(date) && nextEvent.getEndDate().isEqual(date)) {
            Duration duration = Duration.between(LocalTime.MIDNIGHT, nextEvent.getEndTime());
            if (!duration.isZero()) {
                eventsAtDate.add(new Event(nextEvent.eventDescription, date,
                        LocalTime.MIDNIGHT, duration, recurFrequency));
            }
        } else if (nextEvent.getDate().isEqual(date) && nextEvent.getEndDate().isAfter(date)) {
            Duration duration = Duration.between(nextEvent.getTime(), LocalTime.MIDNIGHT).plusDays(1);
            eventsAtDate.add(new Event(nextEvent.eventDescription, date,
                    nextEvent.getTime(), duration, recurFrequency));
        } else {
            eventsAtDate.add(nextEvent);
        }

        Event nextNextEvent = nextEvent.getNextEvent();

        if (nextNextEvent.isCollidingAtDate(date) && !getRecurFrequency().equals(RecurFrequency.NONE)) {
            Duration duration = Duration.between(nextNextEvent.getTime(), LocalTime.MIDNIGHT).plusDays(1);
            eventsAtDate.add(new Event(nextNextEvent.eventDescription, date,
                    nextNextEvent.getTime(), duration, recurFrequency));
        }

        return eventsAtDate;
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
        return String.format("%s-%s%s%s %s", time, getEndTime(),
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
        return String.format("%s %s-%s%s%s %s", date.format(
                        DateTimeFormatter.ofPattern("dd-MMM-yyyy")), time, getEndTime(),
                plusDays, getRecurFrequency().getLabel(), eventDescription);
    }

}
