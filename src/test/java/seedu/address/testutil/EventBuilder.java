package seedu.address.testutil;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.schedule.RecurFrequency;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    public static final String DEFAULT_EVENT_DESCRIPTION = "CS2101 Tutorial";
    public static final String DEFAULT_DATE = "2022-03-12";
    public static final String DEFAULT_TIME = "14:00";
    public static final String DEFAULT_DURATION = "2H";
    public static final String DEFAULT_RECURRENCE = "WEEKLY";

    private EventDescription eventDescription;
    private LocalDate date;
    private LocalTime time;
    private Duration duration;
    private RecurFrequency recurFrequency;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        try {
            eventDescription = ParserUtil.parseEventDescription(DEFAULT_EVENT_DESCRIPTION);
            date = ParserUtil.parseDate(DEFAULT_DATE);
            time = ParserUtil.parseTime(DEFAULT_TIME);
            duration = ParserUtil.parseDuration(DEFAULT_DURATION);
            recurFrequency = ParserUtil.parseRecurFrequency(DEFAULT_RECURRENCE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        eventDescription = eventToCopy.getEventDescription();
        date = eventToCopy.getDate();
        time = eventToCopy.getTime();
        duration = eventToCopy.getDuration();
        recurFrequency = eventToCopy.getRecurFrequency();
    }

    /**
     * Sets the {@code EventDescription} of the {@code Event} that we are building.
     */
    public EventBuilder withEventDescription(String eventDescription) {
        try {
            this.eventDescription = ParserUtil.parseEventDescription(eventDescription);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Parses and sets the {@code LocalDate} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        try {
            this.date = ParserUtil.parseDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Parses and sets the {@code LocalTime} of the {@code Event} that we are building.
     */
    public EventBuilder withTime(String time) {
        try {
            this.time = ParserUtil.parseTime(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Parses and sets the {@code Duration} of the {@code Event} that we are building.
     */
    public EventBuilder withDuration(String duration) {
        try {
            this.duration = ParserUtil.parseDuration(duration);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Parses and sets the {@code RecurFrequency} of the {@code Event} that we are building.
     */
    public EventBuilder withRecurFrequency(String recurFrequency) {
        try {
            this.recurFrequency = ParserUtil.parseRecurFrequency(recurFrequency);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Event build() {
        return new Event(eventDescription, date, time, duration, recurFrequency);
    }

}

