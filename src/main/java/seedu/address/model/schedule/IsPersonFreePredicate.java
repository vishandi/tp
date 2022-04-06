package seedu.address.model.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;

/**
 * Tests that a {@code Person}'s {@code Schedule} does not collide to given time and date.
 */
public class IsPersonFreePredicate implements Predicate<Person> {

    private final LocalTime time;
    private final LocalDate date;
    private final Set<Tag> tags;

    /**
     * Constructor method of IsPersonFreePredicate
     * @param time to check if there exist Events that collide with it
     * @param date to check if there exist Events that collide with it
     */
    public IsPersonFreePredicate(LocalTime time, LocalDate date, Set<Tag> tags) {
        this.time = time;
        this.date = date;
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        if (!tags.isEmpty()) {
            boolean hasTag = person.getTags().stream().anyMatch(tag -> tags.contains(tag));
            if (!hasTag) {
                return false;
            }
        }
        Schedule schedule = person.getSchedule();
        List<Event> events = schedule.getEvents();
        if (events.size() == 0) {
            return true;
        }
        Stream<Event> eventsStream = events.stream();
        return eventsStream.allMatch(event -> !event.willDateTimeCollideEvent(date, time));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsPersonFreePredicate // instanceof handles nulls
                && time.equals(((IsPersonFreePredicate) other).time)
                && date.equals(((IsPersonFreePredicate) other).date)); // state check
    }
}
