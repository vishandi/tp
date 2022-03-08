package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    /*
    //Email can be empty.
    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }
     */

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank email
        //assertFalse(Email.isValidEmail("")); // empty string Email can be empty.
        assertFalse(Email.isValidEmailAddress(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidEmailAddress("@example.com")); // missing local part
        assertFalse(Email.isValidEmailAddress("peterjackexample.com")); // missing '@' symbol
        assertFalse(Email.isValidEmailAddress("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmailAddress("peterjack@-")); // invalid domain name
        assertFalse(Email.isValidEmailAddress("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Email.isValidEmailAddress("peter jack@example.com")); // spaces in local part
        assertFalse(Email.isValidEmailAddress("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Email.isValidEmailAddress(" peterjack@example.com")); // leading space
        assertFalse(Email.isValidEmailAddress("peterjack@example.com ")); // trailing space
        assertFalse(Email.isValidEmailAddress("peterjack@@example.com")); // double '@' symbol
        assertFalse(Email.isValidEmailAddress("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmailAddress("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(Email.isValidEmailAddress("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(Email.isValidEmailAddress("peter..jack@example.com")); // local part has two consecutive periods
        assertFalse(Email.isValidEmailAddress("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Email.isValidEmailAddress("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Email.isValidEmailAddress("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Email.isValidEmailAddress("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Email.isValidEmailAddress("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(Email.isValidEmailAddress("peterjack@example.c")); // top level domain has less than two chars

        // valid email
        assertTrue(Email.isValidEmailAddress("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(Email.isValidEmailAddress("PeterJack.1190@example.com")); // period in local part
        assertTrue(Email.isValidEmailAddress("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(Email.isValidEmailAddress("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(Email.isValidEmailAddress("a@bc")); // minimal
        assertTrue(Email.isValidEmailAddress("test@localhost")); // alphabets only
        assertTrue(Email.isValidEmailAddress("123@145")); // numeric local part and domain name
        assertTrue(Email.isValidEmailAddress("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Email.isValidEmailAddress("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Email.isValidEmailAddress("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Email.isValidEmailAddress("e1234567@u.nus.edu")); // more than one period in domain
    }
}
