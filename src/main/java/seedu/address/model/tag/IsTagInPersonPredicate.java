package seedu.address.model.tag;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests whether a {@code Person}'s {@code tag}
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
        if (tagSet.isEmpty()) {
            return false;
        }
        Iterator iter = tagSet.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(this.tag)) {
                return true;
            }
        }
        return false;
    }
}
