package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class EventDescription {
    public static final String MESSAGE_CONSTRAINTS =
            "Event descriptions should only contain alphanumeric characters and spaces, and it should not be blank";

    public final String value;

    /**
     * An empty constructor for Jackson to initialise an {@code EventDescription}.
     */
    private EventDescription() {
        this.value = "";
    }

    /**
     * Constructs a {@code Name}.
     *
     * @param eventDescription A valid eventDescription.
     */
    public EventDescription(String eventDescription) {
        requireNonNull(eventDescription);
        checkArgument(isValidEventDescription(eventDescription), MESSAGE_CONSTRAINTS);
        this.value = eventDescription;
    }

    /**
     * Returns true if a given string is a valid eventDescription.
     */
    public static boolean isValidEventDescription(String test) {
        String trimmedTest = test.trim();
        return !trimmedTest.equals("");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDescription // instanceof handles nulls
                && value.equals(((EventDescription) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
