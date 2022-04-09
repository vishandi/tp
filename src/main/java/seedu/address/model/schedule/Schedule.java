package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Schedule {

    public static final String MESSAGE_CONSTRAINTS =
            "A Schedule's Events must have alphanumeric event descriptions, date formats YYYY-MM-DD, "
                    + "time formats HH:MM and duration format in hours";
    public static final String MESSAGE_DUPLICATE_EVENT = "The event already exists in the schedule";
    public static final Schedule EMPTY_SCHEDULE = new Schedule(new ArrayList<>());
    private final List<Event> events = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Schedule(List<Event> events) {
        requireAllNonNull(events);
        this.events.addAll(events);
    }

    /**
     * Returns a List of Event objects in the Schedule.
     *
     * @return a List of Event objects in the Schedule
     */
    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    /**
     * Returns whether the Schedule contains the given event.
     */
    public boolean hasEvent(Event event) {
        return events.contains(event);
    }

    /**
     * Returns a Schedule object containing events that are happening in the next {@code daysForward} days.
     * The events in the schedule has been updated with the respective next recurring date.
     */
    public Schedule getUpcomingSchedule(int daysForward) {
        LocalDate today = LocalDate.now();
        LocalDate nextDaysForward = today.plusDays(daysForward);
        List<Event> upcomingEvents = new ArrayList<>();

        for (Event event : getEvents()) {
            if (event.willDateCollide(nextDaysForward)) {
                List<Event> eventsAtDate = event.getEventsAtDate(nextDaysForward);
                for (Event e : eventsAtDate) {
                    if (!event.getDuration().isZero()) {
                        upcomingEvents.add(e);
                    }
                }
            }
        }

        Collections.sort(upcomingEvents);

        return new Schedule(upcomingEvents);
    }

    /**
     * Returns an Event object at specified index
     *
     * @param index of the Event object to retrieve
     * @return an Event object
     */
    public Event getEvent(int index) {
        return events.get(index);
    }

    /**
     * Returns true if the schedule is empty.
     */
    public boolean isEmpty() {
        return getEvents().isEmpty();
    }

    public String getDailyScheduleFormat() {
        final StringBuilder builder = new StringBuilder();

        for (Event event : events) {
            builder.append(String.format("%s\n", event.getDailyScheduleFormat()));
        }

        return builder.toString();
    }

    /**
     * Returns true if both schedules have the same list of events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return otherSchedule.getEvents().equals(getEvents());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(events);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        int counter = 1;
        for (Event event : events) {
            builder.append(String.format("%s. %s\n", counter, event));
            counter += 1;
        }

        return builder.toString();
    }

}
