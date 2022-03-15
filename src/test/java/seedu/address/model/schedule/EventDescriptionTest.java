package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.*;
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
        assertFalse(EventDescription.isValidEventDescription("^")); // only non-alphanumeric characters
        assertFalse(EventDescription.isValidEventDescription("Jurong*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(EventDescription.isValidEventDescription("corporation drive")); // alphabets only
        assertTrue(EventDescription.isValidEventDescription("12345")); // numbers only
        assertTrue(EventDescription.isValidEventDescription("hougang 1st Ave")); // alphanumeric characters
        assertTrue(EventDescription.isValidEventDescription("Hougang 1st Ave")); // with capital letters
        assertTrue(EventDescription.isValidEventDescription("Blk 64 Lorong 5 Toa Payoh")); //long names
    }
}
