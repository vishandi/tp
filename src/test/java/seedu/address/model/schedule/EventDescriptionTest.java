package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class EventDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventDescription(null));
    }

    @Test
    public void isValidEventDescription() {
        // null name
        assertThrows(NullPointerException.class, () -> EventDescription.isValidEventDescription(null));

        // invalid name
        assertFalse(EventDescription.isValidEventDescription("")); // empty string
        assertFalse(EventDescription.isValidEventDescription(" ")); // spaces only
        assertFalse(EventDescription.isValidEventDescription("Meet John /at NUS")); //contains /
        assertFalse(EventDescription.isValidEventDescription("/go school")); //starts with /

        // valid name
        assertTrue(EventDescription.isValidEventDescription("corporation drive")); // alphabets only
        assertTrue(EventDescription.isValidEventDescription("12345")); // numbers only
        assertTrue(EventDescription.isValidEventDescription("hougang 1st Ave")); // alphanumeric characters
        assertTrue(EventDescription.isValidEventDescription("Hougang 1st Ave")); // with capital letters
        assertTrue(EventDescription.isValidEventDescription("Blk 64 Lorong 5 Toa Payoh")); //long names
        assertTrue(EventDescription.isValidEventDescription("^")); // non-alphanumeric characters
        assertTrue(EventDescription.isValidEventDescription("Jurong*")); // contains non-alphanumeric characters
    }

    @Test
    public void equals() {
        EventDescription eventDescription = new EventDescription("CS2101");
        EventDescription eventDescriptionCopy = new EventDescription("CS2101");
        EventDescription eventDescriptionDifferent = new EventDescription("CS2103T");

        // same object -> returns true
        assertTrue(eventDescription.equals(eventDescription));

        // same values -> returns true
        assertTrue(eventDescription.equals(eventDescriptionCopy));

        // null -> returns false
        assertFalse(eventDescription.equals(null));

        // different type -> returns false
        assertFalse(eventDescription.equals(5));

        // different person -> returns false
        assertFalse(eventDescription.equals(eventDescriptionDifferent));
    }
}
