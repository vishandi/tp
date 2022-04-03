package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
        assertThrows(NullPointerException.class, () -> Telegram.isValidTelegram(null));

        // invalid username
        assertFalse(Telegram.isValidTelegram("abc")); // less than 5 characters
        assertFalse(Telegram.isValidTelegram("abcde ")); // spaces in username
        assertFalse(Telegram.isValidTelegram("abcde*")); // asterisk in username
        assertFalse(Telegram.isValidTelegram("_abcde")); // starts with underscore
        assertFalse(Telegram.isValidTelegram("abcde_")); // ends with underscore
        assertFalse(Telegram.isValidTelegram("abc__de")); // contains consecutive underscores
        assertFalse(Telegram.isValidTelegram("a".repeat(41))); // exceeding 40 characters limit

        //valid username
        assertTrue(Telegram.isValidTelegram("ab_cde")); // contains underscore (_)
        assertTrue(Telegram.isValidTelegram("ab_cd_e")); // contains multiple underscores
        assertTrue(Telegram.isValidTelegram("abcde_12345")); // contains alphanumeric + underscore
        assertTrue(Telegram.isValidTelegram("a".repeat(40))); // maximum length
    }
}
