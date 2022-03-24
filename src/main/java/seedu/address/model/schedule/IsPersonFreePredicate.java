package seedu.address.model.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Schedule} does not collide to given time and date.
 */
public class IsPersonFreePredicate implements Predicate<Person> {

    private final LocalTime time;
    private final LocalDate date;

    /**
     * Constructor method of IsPersonFreePredicate
     * @param time to check if there exist Events that collide with it
     * @param date to check if there exist Events that collide with it
     */
    public IsPersonFreePredicate(LocalTime time, LocalDate date) {
        this.time = time;
        this.date = date;
    }

    @Override
    public boolean test(Person person) {
        Schedule schedule = person.getSchedule();
        List<Event> events = schedule.getEvents();
        if (events.size() == 0) {
            return false;
        }
        Stream<Event> eventsStream = events.stream();
        return eventsStream
                .filter(event -> event.willDateCollide(date))
                .allMatch(event -> {
                    LocalTime start = event.getTime();
                    LocalTime end = event.getEndTime();

                    return !(start.equals(time) || start.isBefore(time) && end.isAfter(time));
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsPersonFreePredicate // instanceof handles nulls
                && time.equals(((IsPersonFreePredicate) other).time)
                && date.equals(((IsPersonFreePredicate) other).date)); // state check
    }
}
