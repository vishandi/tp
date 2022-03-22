package seedu.address.testutil;

import seedu.address.logic.EditUtil.EditEventDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.schedule.RecurFrequency;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code person}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setEventDescription(event.getEventDescription());
        descriptor.setDate(event.getDate());
        descriptor.setTime(event.getTime());
        descriptor.setDuration(event.getDuration());
        descriptor.setRecurFrequency(event.getRecurFrequency()); // default no recurrence
    }

    /**
     * Sets the {@code EventDescription} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventDescription(String eventDescription) {
        descriptor.setEventDescription(new EventDescription(eventDescription));
        return this;
    }

    /**
     * Sets the {@code LocalDate} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDate(String date) {
        try {
            descriptor.setDate(ParserUtil.parseDate(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code LocalTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withTime(String time) {
        try {
            descriptor.setTime(ParserUtil.parseTime(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code Duration} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDuration(String duration) {
        try {
            descriptor.setDuration(ParserUtil.parseDuration(duration));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code RecurFrequency} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withRecurFrequency(String recurFrequency) {
        try {
            descriptor.setRecurFrequency(RecurFrequency.of(recurFrequency));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
