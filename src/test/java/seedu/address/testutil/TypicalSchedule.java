package seedu.address.testutil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.Schedule;

/**
 * A utility class containing a {@code Schedule} object to be used in tests.
 */
public class TypicalSchedule {

    public static final Event SE_TUTORIAL = new EventBuilder().withEventDescription("CS2103T Tutorial")
            .withDate("2022-03-16").withTime("09:00").withDuration("1H")
            .withRecurFrequency("WEEKLY").build().getNextRecurringEvent();
    public static final Event SE_LECTURE = new EventBuilder().withEventDescription("CS2103T Lecture")
            .withDate("2022-03-18").withTime("14:00").withDuration("2H")
            .withRecurFrequency("WEEKLY").build().getNextRecurringEvent();
    public static final Event OS_TUTORIAL = new EventBuilder().withEventDescription("CS2106 Tutorial")
            .withDate("2022-03-14").withTime("10:00").withDuration("1H")
            .withRecurFrequency("WEEKLY").build().getNextRecurringEvent();
    public static final Event OS_LECTURE = new EventBuilder().withEventDescription("CS2106 Lecture")
            .withDate("2022-03-16").withTime("10:00").withDuration("2H")
            .withRecurFrequency("WEEKLY").build().getNextRecurringEvent();
    public static final Event OS_LAB = new EventBuilder().withEventDescription("CS2106 Lab")
            .withDate("2022-03-17").withTime("13:00").withDuration("1H")
            .withRecurFrequency("WEEKLY").build().getNextRecurringEvent();
    public static final Event NATIONAL_DAY_PARTY = new EventBuilder().withEventDescription("National Day Party at BFF")
            .withDate("2022-08-09").withTime("20:00").withDuration("3H").build().getNextRecurringEvent();
    public static final Event ALICE_BIRTHDAY = new EventBuilder().withEventDescription("Alice Birthday Surprise")
            .withDate("2022-03-14").withTime("17:00").withDuration("4H").build().getNextRecurringEvent();

    private TypicalSchedule() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static Schedule getTypicalSchedule() {
        return new Schedule(getTypicalEvents());
    }

    public static List<Event> getTypicalEvents() {
        List<Event> events = Arrays.asList(OS_TUTORIAL, ALICE_BIRTHDAY, SE_TUTORIAL, OS_LECTURE, OS_LAB,
                SE_LECTURE, NATIONAL_DAY_PARTY);
        Collections.sort(events);
        return events;
    }
}
