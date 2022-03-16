package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class SamePersonPredicate implements Predicate<Person> {
    private final Person person;

    public SamePersonPredicate(Person person) {
        this.person = person;
    }

    @Override
    public boolean test(Person person) {
        return person.isSamePerson(this.person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SamePersonPredicate // instanceof handles nulls
                && person.isSamePerson(((SamePersonPredicate) other).person)); // state check
    }

}
