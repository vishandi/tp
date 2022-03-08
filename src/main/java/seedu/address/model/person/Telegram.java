package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Optional;

/**
 * Represents a Person's Telegram handle in UniGenda.
 * Guarantees: immutable; is valid as declared in
 */
public class Telegram {
    public static final String MESSAGE_CONSTRAINTS = "Telegram username should "
            + "adhere to the following constraints:\n"
            + "1. The username should only contain alphanumeric characters and underscore (_).\n"
            + "2. The username must contain at least 5 characters.";
    public static final String VALIDATION_REGEX = "^[a-zA-z0-9-_]{5,}$";
    public static final Telegram EMPTY_TELEGRAM = new Telegram();

    public final String value;

    /**
     * Constructs on {@code Telegram}.
     *
     * @param username A valid Telegram username.
     */
    public Telegram(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_CONSTRAINTS);
        value = username;
    }

    private Telegram() {
        value = null;
    }

    /**
     * Returns true if a given string is a valid Telegram username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given Telegram is a valid Telegram object.
     */
    public static boolean isValidTelegram(Telegram telegram) {
        return telegram.isEmpty() || isValidUsername(telegram.value);
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
