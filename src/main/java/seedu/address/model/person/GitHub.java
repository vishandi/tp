package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's GitHub username in UniGenda.
 * Guarantees: immutable;
 */
public class GitHub {
    public static final String MESSAGE_CONSTRAINTS = "GitHub username should "
            + "adhere to the following constraints:\n"
            + "1. The username should only contain alphanumeric characters and hyphen (-).\n"
            + "2. The username cannot have multiple consecutive hyphens or begin or end with a hyphen.\n"
            + "4. The username must not exceed 39 characters.";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9](?:[a-zA-Z0-9]|-(?=[a-zA-Z0-9])){0,38}$";
    public static final GitHub EMPTY_GITHUB = new GitHub("");

    public final String value;

    /**
     * Constructs an {@code GitHub}
     *
     * @param username A Valid GitHub username.
     */
    public GitHub(String username) {
        requireNonNull(username);
        checkArgument(isValidGitHub(username), MESSAGE_CONSTRAINTS);
        value = username;
    }

    /**
     * Returns true if a given string is a valid GitHub username.
     */
    public static boolean isValidGitHub(String test) {
        return test.equals("") || test.matches(VALIDATION_REGEX);
    }

    /***
     * Returns true if the given GitHub object is the default empty GitHub.
     */
    public boolean isEmpty() {
        return this.equals(EMPTY_GITHUB);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof GitHub
                && value.equals(((GitHub) other).value));
    }
}
