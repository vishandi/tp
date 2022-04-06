package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventTest {
    @Test
    public void getNextEvent() {
        Event noneEvent = new EventBuilder().withDate("2021-01-01").withRecurFrequency("NONE").build();
        Event dailyEvent = new EventBuilder().withDate("2021-01-01").withRecurFrequency("DAILY").build();
        Event weeklyEvent = new EventBuilder().withDate("2021-01-01").withRecurFrequency("WEEKLY").build();
        Event biweeklyEvent = new EventBuilder().withDate("2021-01-01").withRecurFrequency("BIWEEKLY").build();

        assertEquals(new EventBuilder().withDate("2021-01-01").withRecurFrequency("NONE").build(),
                noneEvent.getNextEvent());
        assertEquals(new EventBuilder().withDate("2021-01-02").withRecurFrequency("DAILY").build(),
                dailyEvent.getNextEvent());
        assertEquals(new EventBuilder().withDate("2021-01-08").withRecurFrequency("WEEKLY").build(),
                weeklyEvent.getNextEvent());
        assertEquals(new EventBuilder().withDate("2021-01-15").withRecurFrequency("BIWEEKLY").build(),
                biweeklyEvent.getNextEvent());
    }

    @Test
    public void isCollidingAtDate() {
        Event event = new EventBuilder().withDate("2021-01-01").withRecurFrequency("NONE").withTime("00:00")
                .withDuration("72H").build();

        assertFalse(event.isCollidingAtDate(LocalDate.parse("2020-12-31")));
        assertFalse(event.isCollidingAtDate(LocalDate.parse("2021-01-05")));
        assertTrue(event.isCollidingAtDate(LocalDate.parse("2021-01-04")));
        assertTrue(event.isCollidingAtDate(LocalDate.parse("2021-01-03")));
    }

    @Test
    public void getEventsAtDate() {
        ArrayList<Event> expectedEventList;

        // none recurring event
        Event noneEvent = new EventBuilder().withDate("2021-01-01").withDuration("2H").withTime("23:00")
                .withRecurFrequency("NONE").build();

        // not colliding with the date
        assertEquals(noneEvent.getEventsAtDate(LocalDate.parse("2020-12-31")), new ArrayList<>());
        assertEquals(noneEvent.getEventsAtDate(LocalDate.parse("2021-01-03")), new ArrayList<>());
        // colliding
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2021-01-01").withDuration("1H").withTime("23:00")
                .withRecurFrequency("NONE").build());
        assertEquals(noneEvent.getEventsAtDate(LocalDate.parse("2021-01-01")), expectedEventList);

        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2021-01-02").withDuration("1H").withTime("00:00")
                .withRecurFrequency("NONE").build());
        assertEquals(noneEvent.getEventsAtDate(LocalDate.parse("2021-01-02")), expectedEventList);

        // daily event
        Event dailyEvent = new EventBuilder().withDate("2023-01-01").withDuration("2H").withTime("23:00")
                .withRecurFrequency("DAILY").build();

        // not colliding with the date
        assertEquals(dailyEvent.getEventsAtDate(LocalDate.parse("2020-12-31")), new ArrayList<>());

        // colliding
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2023-01-01").withDuration("1H").withTime("23:00")
                .withRecurFrequency("DAILY").build());
        assertEquals(dailyEvent.getEventsAtDate(LocalDate.parse("2023-01-01")), expectedEventList);

        expectedEventList = new ArrayList<>(); // doesn't work after 2023
        expectedEventList.add(new EventBuilder().withDate("2023-01-02").withDuration("1H").withTime("00:00")
                .withRecurFrequency("DAILY").build());
        expectedEventList.add(new EventBuilder().withDate("2023-01-02").withDuration("1H").withTime("23:00")
                .withRecurFrequency("DAILY").build());
        assertEquals(dailyEvent.getEventsAtDate(LocalDate.parse("2023-01-02")), expectedEventList);

        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2023-01-10").withDuration("1H").withTime("00:00")
                .withRecurFrequency("DAILY").build());
        expectedEventList.add(new EventBuilder().withDate("2023-01-10").withDuration("1H").withTime("23:00")
                .withRecurFrequency("DAILY").build());
        assertEquals(dailyEvent.getEventsAtDate(LocalDate.parse("2023-01-10")), expectedEventList);

        // weekly event
        Event weeklyEvent = new EventBuilder().withDate("2023-01-01").withDuration("166H").withTime("10:00")
                .withRecurFrequency("WEEKLY").build();

        // not colliding with the date
        assertEquals(weeklyEvent.getEventsAtDate(LocalDate.parse("2020-12-31")), new ArrayList<>());

        // colliding on the day itself
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2023-01-01").withDuration("14H").withTime("10:00")
                .withRecurFrequency("WEEKLY").build());
        assertEquals(weeklyEvent.getEventsAtDate(LocalDate.parse("2023-01-01")), expectedEventList);

        // colliding not on Monday
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2023-01-10").withDuration("24H").withTime("00:00")
                .withRecurFrequency("WEEKLY").build());
        assertEquals(weeklyEvent.getEventsAtDate(LocalDate.parse("2023-01-10")), expectedEventList);

        // colliding on Monday next week
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2023-01-08").withDuration("8H").withTime("00:00")
                .withRecurFrequency("WEEKLY").build());
        expectedEventList.add(new EventBuilder().withDate("2023-01-08").withDuration("14H").withTime("10:00")
                .withRecurFrequency("WEEKLY").build());
        assertEquals(weeklyEvent.getEventsAtDate(LocalDate.parse("2023-01-08")), expectedEventList);

        // biweekly event
        Event biweeklyEvent = new EventBuilder().withDate("2023-01-01").withDuration("334H").withTime("10:00")
                .withRecurFrequency("BIWEEKLY").build();

        // not colliding with the date
        assertEquals(biweeklyEvent.getEventsAtDate(LocalDate.parse("2020-12-31")), new ArrayList<>());

        // colliding on the day itself
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2023-01-01").withDuration("14H").withTime("10:00")
                .withRecurFrequency("BIWEEKLY").build());
        assertEquals(biweeklyEvent.getEventsAtDate(LocalDate.parse("2023-01-01")), expectedEventList);

        // colliding not on Monday
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2023-01-10").withDuration("24H").withTime("00:00")
                .withRecurFrequency("BIWEEKLY").build());
        assertEquals(biweeklyEvent.getEventsAtDate(LocalDate.parse("2023-01-10")), expectedEventList);

        // colliding on Monday next two weeks
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2023-01-15").withDuration("8H").withTime("00:00")
                .withRecurFrequency("BIWEEKLY").build());
        expectedEventList.add(new EventBuilder().withDate("2023-01-15").withDuration("14H").withTime("10:00")
                .withRecurFrequency("BIWEEKLY").build());
        assertEquals(biweeklyEvent.getEventsAtDate(LocalDate.parse("2023-01-15")), expectedEventList);

        // biweekly event
        biweeklyEvent = new EventBuilder().withDate("2023-01-01").withDuration("166H").withTime("10:00")
                .withRecurFrequency("BIWEEKLY").build();

        // not colliding with the date
        assertEquals(biweeklyEvent.getEventsAtDate(LocalDate.parse("2023-01-10")), new ArrayList<>());

        // the following week
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2023-01-08").withDuration("8H").withTime("00:00")
                .withRecurFrequency("BIWEEKLY").build());
        assertEquals(biweeklyEvent.getEventsAtDate(LocalDate.parse("2023-01-08")), expectedEventList);
    }
}
