package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GitHubTest {

    @Test
    public void equals() {
        GitHub github = new GitHub("example");

        // same object -> returns true
        assertTrue(github.equals(github));

        // same values -> return true
        assertTrue(github.equals(new GitHub(github.value)));

        // different type -> return false
        assertFalse(github.equals(1));

        // null -> return false
        assertFalse(github.equals(null));

        // different username -> return false
        assertFalse(github.equals(new GitHub("Google")));
    }

    @Test
    public void isValidUsername() {
        // null username
        assertThrows(NullPointerException.class, () -> GitHub.isValidGitHub(null));

        // invalid username
        assertFalse(GitHub.isValidGitHub("-a")); // starts with hyphen
        assertFalse(GitHub.isValidGitHub("a-")); // ends with hyphen
        assertFalse(GitHub.isValidGitHub("a*b")); // invalid character
        assertFalse(GitHub.isValidGitHub("a_b")); // invalid character
        assertFalse(GitHub.isValidGitHub("a".repeat(40))); // exceed 39 maximum characters
        assertFalse(GitHub.isValidGitHub("a--b")); // hyphens followed by another hyphen

        // valid username
        assertTrue(GitHub.isValidGitHub("a"));
        assertTrue(GitHub.isValidGitHub("a-b"));
        assertTrue(GitHub.isValidGitHub("a-b-c"));
        assertTrue(GitHub.isValidGitHub("a".repeat(39)));
    }
}
