package seedu.address.storage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.schedule.RecurFrequency;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

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
     */
    public Event toModelType() throws IllegalValueException {
        if (eventDescription == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EventDescription.class.getSimpleName()));
        }
        if (!EventDescription.isValidEventDescription(eventDescription)) {
            throw new IllegalValueException(EventDescription.MESSAGE_CONSTRAINTS);
        }
        final EventDescription modelEventDescription = new EventDescription(eventDescription);

        if (date == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, LocalDate.class.getSimpleName()));
        }
        final LocalDate modelDate;
        try {
            modelDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Event.DATE_MESSAGE_CONSTRAINTS);
        }

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

        Event event = new Event(modelEventDescription, modelDate, modelTime, modelDuration, modelRecurFrequency);
        return event.getNextRecurringEvent();
    }
}
