package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.model.recurfrequency.RecurFrequency;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.Schedule;

/**
 * A utility class containing a {@code Schedule} object to be used in tests.
 */
public class TypicalSchedule {

    public static final Event SE_TUTORIAL = new EventBuilder().withEventDescription("CS2103T Tutorial")
            .withDate("2022-03-16").withTime("09:00").withDuration("1H")
            .withRecurFrequency(RecurFrequency.WEEKLY).build();
    public static final Event SE_LECTURE = new EventBuilder().withEventDescription("CS2103T Lecture")
            .withDate("2022-03-18").withTime("14:00").withDuration("2H")
            .withRecurFrequency(RecurFrequency.WEEKLY).build();
    public static final Event OS_TUTORIAL = new EventBuilder().withEventDescription("CS2106 Tutorial")
            .withDate("2022-03-14").withTime("10:00").withDuration("1H")
            .withRecurFrequency(RecurFrequency.WEEKLY).build();
    public static final Event OS_LECTURE = new EventBuilder().withEventDescription("CS2106 Lecture")
            .withDate("2022-03-16").withTime("10:00").withDuration("2H")
            .withRecurFrequency(RecurFrequency.WEEKLY).build();
    public static final Event OS_LAB = new EventBuilder().withEventDescription("CS2106 Lab")
            .withDate("2022-03-17").withTime("13:00").withDuration("1H")
            .withRecurFrequency(RecurFrequency.WEEKLY).build();
    public static final Event NATIONAL_DAY_PARTY = new EventBuilder().withEventDescription("National Day Party at BFF")
            .withDate("2022-08-09").withTime("20:00").withDuration("3H").build();
    public static final Event ALICE_BIRTHDAY = new EventBuilder().withEventDescription("Alice Birthday Surprise")
            .withDate("2022-03-14").withTime("17:00").withDuration("4H").build();

    private TypicalSchedule() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static Schedule getTypicalSchedule() {
        return new Schedule(getTypicalEvents());
    }

    public static List<Event> getTypicalEvents() {
        ArrayList<Event> typicalEvents = new ArrayList<>();
//        typicalEvents.add(SE_TUTORIAL);
//        typicalEvents.add(SE_LECTURE);
//        typicalEvents.add(OS_LECTURE);
//        typicalEvents.add(OS_TUTORIAL);
//        typicalEvents.add(OS_LAB);
//        typicalEvents.add(NATIONAL_DAY_PARTY);
//        typicalEvents.add(ALICE_BIRTHDAY);

        Collections.addAll(typicalEvents, SE_TUTORIAL, SE_LECTURE, OS_LECTURE, OS_TUTORIAL, OS_LAB,
                NATIONAL_DAY_PARTY, ALICE_BIRTHDAY);
        return typicalEvents;
    }
}
