package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in UniGenda.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {
    //Default Address if address is not specified on add.
    public static final Address EMPTY_ADDRESS = new Address("");

    public static final String MESSAGE_CONSTRAINTS = "Addresses should not exceed 80 characters and only takes"
            + " alphanumeric characters, spaces, and the following punctuations: !\"#$&'()*+,-.:;<=>?@";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX =
            "[a-zA-Z0-9!\"#$&'()*+,-.:;<=>?@][a-zA-Z0-9!\"#$&'()*+,-.:;<=>?@ ]{0,79}";

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
        return test.equals("") || test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if this Address is the default address.
     */
    public boolean isEmpty() {
        return this.equals(EMPTY_ADDRESS);
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
