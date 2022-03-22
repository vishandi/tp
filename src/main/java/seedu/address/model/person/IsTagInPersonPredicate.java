package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Tests whether a {@code Person} has specified {@code Tag}
 */
public class IsTagInPersonPredicate implements Predicate<Person> {

    private final Tag tag;

    /**
     * Constructor method of IsTagInPersonPredicate
     */
    public IsTagInPersonPredicate(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Person person) {
        Set<Tag> tagSet = person.getTags();
        return tagSet.contains(this.tag);
    }
}
