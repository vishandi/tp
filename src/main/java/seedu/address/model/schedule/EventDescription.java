package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.Name;

public class EventDescription {
    public static final String MESSAGE_CONSTRAINTS =
            "Event descriptions should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String eventDescription;

    private EventDescription() {
        this.eventDescription = "";
    }

    /**
     * Constructs a {@code Name}.
     *
     * @param eventDescription A valid eventDescription.
     */
    public EventDescription(String eventDescription) {
        requireNonNull(eventDescription);
        checkArgument(isValidEventDescription(eventDescription), MESSAGE_CONSTRAINTS);
        this.eventDescription = eventDescription;
    }

    /**
     * Returns true if a given string is a valid eventDescription.
     */
    public static boolean isValidEventDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return eventDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDescription // instanceof handles nulls
                && eventDescription.equals(((EventDescription) other).eventDescription)); // state check
    }

    @Override
    public int hashCode() {
        return eventDescription.hashCode();
    }

}
