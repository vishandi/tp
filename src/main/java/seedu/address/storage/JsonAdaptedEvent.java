package seedu.address.storage;

import static java.time.temporal.TemporalAdjusters.next;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.InvalidEnumArgumentException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recurfrequency.RecurFrequency;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    public static final String MISSING_RECUR_FREQUENCY_CASE = "%s switch case is missing in JsonAdaptedEvent!";

    private final String eventDescription;
    private final String date;
    private final String time;
    private final String duration;
    private final String recurFrequency;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventDescription") String eventDescription,
            @JsonProperty("date") String date, @JsonProperty("time") String time,
            @JsonProperty("duration") String duration,
            @JsonProperty("recurFrequency") String recurFrequency) {
        this.eventDescription = eventDescription;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.recurFrequency = recurFrequency;
    }

    /**
     * Constructs a {@code JsonAdaptedEvent} using the attributes of the given {@code Event} for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        eventDescription = source.getEventDescription().value;
        date = source.getDate().toString();
        time = source.getTime().toString();
        duration = source.getDuration().toString();
        recurFrequency = source.getRecurFrequency().toString();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     * @throws InvalidEnumArgumentException if there are any unhandled RecurFrequency cases.
     */
    public Event toModelType() throws IllegalValueException, InvalidEnumArgumentException {
        if (eventDescription == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EventDescription.class.getSimpleName()));
        }
        if (!EventDescription.isValidEventDescription(eventDescription)) {
            throw new IllegalValueException(EventDescription.MESSAGE_CONSTRAINTS);
        }
        final EventDescription modelEventDescription = new EventDescription(eventDescription);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalTime.class.getSimpleName()));
        }
        final LocalTime modelTime;
        try {
            modelTime = LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Event.TIME_MESSAGE_CONSTRAINTS);
        }

        if (duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Duration.class.getSimpleName()));
        }
        final Duration modelDuration;
        try {
            modelDuration = Duration.parse(duration);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Event.DURATION_MESSAGE_CONSTRAINTS);
        }

        if (recurFrequency == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RecurFrequency.class.getSimpleName()));
        }
        final RecurFrequency modelRecurFrequency;
        try {
            modelRecurFrequency = RecurFrequency.of(recurFrequency);
        } catch (ParseException e) {
            throw new IllegalValueException(RecurFrequency.INVALID_RECUR_FREQUENCY_MESSAGE);
        }

        if (date == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, LocalDate.class.getSimpleName()));
        }
        final LocalDate oldDate;
        try {
            oldDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Event.DATE_MESSAGE_CONSTRAINTS);
        }
        final LocalDate modelDate = updateDate(oldDate, modelRecurFrequency);;
        return new Event(modelEventDescription, modelDate, modelTime, modelDuration, modelRecurFrequency);
    }

    private LocalDate updateDate(LocalDate date, RecurFrequency recurFrequency) throws InvalidEnumArgumentException {
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
            LocalDate resetDate = date.plusDays(7);
            if (today.isAfter(resetDate)) {
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                newDate = today.with(next(dayOfWeek));
            }
            break;
        case BIWEEKLY:
            resetDate = date.plusDays(14);
            if (today.isAfter(resetDate)) {
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                newDate = today.with(next(dayOfWeek));
                if (ChronoUnit.DAYS.between(date, newDate) % 14 != 0) {
                    newDate = newDate.with(next(dayOfWeek));
                }
            }
            break;
        case MONTHLY:
            resetDate = date.plusMonths(1);
            if (today.isAfter(resetDate)) {
                int dayOfMonth = date.getDayOfMonth();
                try {
                    newDate = today.withDayOfMonth(dayOfMonth);
                } catch (DateTimeException e) {
                    newDate = today.with(TemporalAdjusters.lastDayOfMonth());
                }
            }
            break;
        default:
            throw new InvalidEnumArgumentException(String.format(MISSING_RECUR_FREQUENCY_CASE, recurFrequency));
        }
        return newDate;
    }
}
