package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public record Address(String value) {

    public static final String MESSAGE_CONSTRAINTS = "Addresses should be maximum 150 characters long.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].{0,149}";

    /**
     * Constructs an {@code Address}.
     *
     * @param value A valid address.
     */
    public Address {
        requireNonNull(value);
        checkArgument(isValidAddress(value), MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
