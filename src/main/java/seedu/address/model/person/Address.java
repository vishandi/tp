package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in UniGenda.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {
    //Default Address if address is not specified on add.
    public static final String EMPTY_ADDRESS = "";
    public static final Address DEFAULT_ADDRESS = new Address(EMPTY_ADDRESS);
    public static final String DEFAULT_ADDRESS_MESSAGE = "Address not yet specified.";

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return isEmptyAddress(test) || test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string is the default empty address.
     */
    public static boolean isEmptyAddress(String test) {
        return test.equals(EMPTY_ADDRESS);
    }

    /**
     * Returns true if the given Address is the default address.
     */
    public static boolean isDefaultAddress(Address test) {
        return test.equals(DEFAULT_ADDRESS);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
