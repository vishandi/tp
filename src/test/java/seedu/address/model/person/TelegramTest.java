package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TelegramTest {

    @Test
    public void equals() {
        Telegram telegram = new Telegram("telegram");

        // same object -> returns true
        assertTrue(telegram.equals(telegram));

        // same values -> returns true
        assertTrue(telegram.equals(new Telegram(telegram.value)));

        // different types -> return false
        assertFalse(telegram.equals(1));

        // null -> returns false
        assertFalse(telegram.equals(null));

        // different username -> returns false
        assertFalse(telegram.equals(new Telegram("google")));
    }

    @Test
    public void isValidUsername() {
        // null username
        assertFalse(Telegram.isValidUsername(null));

        // blank email
        assertFalse(Telegram.isValidUsername(""));

        // invalid username
        assertFalse(Telegram.isValidUsername("abc")); // less than 5 characters
        assertFalse(Telegram.isValidUsername("abcde ")); // spaces in username
        assertFalse(Telegram.isValidUsername("abcde*")); // asterisk in username

        //valid username
        assertTrue(Telegram.isValidUsername("abcde_")); // contains underscore (_)
        assertTrue(Telegram.isValidUsername("abcde12345_")); // contains alphanumeric + underscore
    }
}
