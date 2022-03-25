package seedu.address.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.Schedule;

public class JsonAdaptedSchedule {
    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedSchedule} with the given schedule details.
     */
    @JsonCreator
    public JsonAdaptedSchedule(@JsonProperty("schedule") List<JsonAdaptedEvent> events) {
        if (events != null) {
            this.events.addAll(events);
        }
    }

    /**
     * Constructs a {@code JsonAdaptedSchedule} using the events of the given {@code Schedule},
     * for Jackson use.
     */
    public JsonAdaptedSchedule(Schedule source) {
        events.addAll(source.getEvents().stream()
                .map(JsonAdaptedEvent::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted schedule object into the model's {@code Schedule} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted schedule.
     */
    public Schedule toModelType() throws IllegalValueException {
        final List<Event> modelEvents = new ArrayList<>();
        for (JsonAdaptedEvent event : events) {
            modelEvents.add(event.toModelType());
        }
        Collections.sort(modelEvents);

        return new Schedule(modelEvents);
    }
}
