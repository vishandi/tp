package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Telegram handle in UniGenda.
 * Guarantees: immutable;
 */
public class Telegram {
    public static final String MESSAGE_CONSTRAINTS = "Telegram username should "
            + "adhere to the following constraints:\n"
            + "1. The username should only contain alphanumeric characters and underscore (_).\n"
            + "2. The username should not start or end with underscore, and it cannot contain "
            + "any consecutive underscores.\n"
            + "3. The username must contain at least 5 characters and at most 40 characters.";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9](?:[a-zA-Z0-9]|_(?=[a-zA-Z0-9])){4,39}$";
    public static final Telegram EMPTY_TELEGRAM = new Telegram("");

    public final String value;

    /**
     * Constructs on {@code Telegram}.
     *
     * @param username A valid Telegram username.
     */
    public Telegram(String username) {
        requireNonNull(username);
        checkArgument(isValidTelegram(username), MESSAGE_CONSTRAINTS);
        value = username;
    }

    /**
     * Returns true if a given string is a valid Telegram username.
     */
    public static boolean isValidTelegram(String test) {
        return test.equals("") || test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the Telegram object is the default Telegram.
     */
    public boolean isEmpty() {
        return this.equals(EMPTY_TELEGRAM);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Telegram) {
            Telegram otherTelegram = (Telegram) other;
            if (this.value == null) {
                return otherTelegram.value == null;
            } else {
                return this.value.equals(otherTelegram.value);
            }
        }
        return false;
    }
}
