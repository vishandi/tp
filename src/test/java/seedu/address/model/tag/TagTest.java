package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Tag;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names
        assertFalse(Tag.isValidTagName("/")); // punctuation
        assertFalse(Tag.isValidTagName("O".repeat(31))); // exceeds maximum length
        assertFalse(Tag.isValidTagName("best friend")); // contains space

        // valid tag names
        assertTrue(Tag.isValidTagName("a")); // one character only
        assertTrue(Tag.isValidTagName("1")); // one numeric character
        assertTrue(Tag.isValidTagName("O".repeat(30))); //maximum length
    }

    @Test
    public void equals() {
        assertFalse(new Tag("abc").equals(null)); // null
        assertFalse(new Tag("aBC").equals(new Tag("Def"))); // different tag

        assertTrue(new Tag("abc").equals(new Tag("abc"))); // same tag
        assertTrue(new Tag("abC").equals(new Tag("ABc"))); // same lower case form
    }

}
