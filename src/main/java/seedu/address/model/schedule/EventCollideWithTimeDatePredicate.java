package seedu.address.model.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Schedule} does not collide to given time and/or date.
 */
public class EventCollideWithTimeDatePredicate implements Predicate<Person> {

    private final LocalTime time;
    private final LocalDate date;

    /**
     * Constructor method of EventCollideWithTimeDatePredicate
     * @param time to check if there exist Events that collide with it
     * @param date to check if there exist Events that collide with it
     */
    public EventCollideWithTimeDatePredicate(LocalTime time, LocalDate date) {
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
                .filter(event -> event.getDate().equals(date))
                .allMatch(event -> {
                    LocalTime start = event.getTime();
                    LocalTime end = start.plus(event.getDuration());

                    return !(start.equals(time) || start.isBefore(time) && end.isAfter(time));
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventCollideWithTimeDatePredicate // instanceof handles nulls
                && time.equals(((EventCollideWithTimeDatePredicate) other).time)); // state check
    }
}
