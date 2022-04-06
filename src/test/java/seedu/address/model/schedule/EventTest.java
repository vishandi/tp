package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventTest {
    private static String pastDate = LocalDate.now().minusDays(365).toString();
    private static String futureDate = LocalDate.now().plusDays(365).toString();
    private static String oneDayAfterFutureDate = LocalDate.now().plusDays(366).toString();
    private static String oneWeekAfterFutureDate = LocalDate.now().plusDays(372).toString();
    private static String nineDaysAfterFutureDate = LocalDate.now().plusDays(374).toString();
    private static String twoWeeksAfterFutureDate = LocalDate.now().plusDays(379).toString();

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
        Event dailyEvent = new EventBuilder().withDate(futureDate).withDuration("2H").withTime("23:00")
                .withRecurFrequency("DAILY").build();

        // not colliding with the date
        assertEquals(dailyEvent.getEventsAtDate(LocalDate.parse(pastDate)), new ArrayList<>());

        // colliding
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(futureDate).withDuration("1H").withTime("23:00")
                .withRecurFrequency("DAILY").build());
        assertEquals(dailyEvent.getEventsAtDate(LocalDate.parse(futureDate)), expectedEventList);

        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(oneDayAfterFutureDate).withDuration("1H").withTime("00:00")
                .withRecurFrequency("DAILY").build());
        expectedEventList.add(new EventBuilder().withDate(oneDayAfterFutureDate).withDuration("1H").withTime("23:00")
                .withRecurFrequency("DAILY").build());
        assertEquals(dailyEvent.getEventsAtDate(LocalDate.parse(oneDayAfterFutureDate)), expectedEventList);

        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(oneWeekAfterFutureDate).withDuration("1H").withTime("00:00")
                .withRecurFrequency("DAILY").build());
        expectedEventList.add(new EventBuilder().withDate(oneWeekAfterFutureDate).withDuration("1H").withTime("23:00")
                .withRecurFrequency("DAILY").build());
        assertEquals(dailyEvent.getEventsAtDate(LocalDate.parse(oneWeekAfterFutureDate)), expectedEventList);

        // weekly event
        Event weeklyEvent = new EventBuilder().withDate(futureDate).withDuration("166H").withTime("10:00")
                .withRecurFrequency("WEEKLY").build();

        // not colliding with the date
        assertEquals(weeklyEvent.getEventsAtDate(LocalDate.parse(pastDate)), new ArrayList<>());

        // colliding on the day itself
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(futureDate).withDuration("14H").withTime("10:00")
                .withRecurFrequency("WEEKLY").build());
        assertEquals(weeklyEvent.getEventsAtDate(LocalDate.parse(futureDate)), expectedEventList);

        // colliding not on the day
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(nineDaysAfterFutureDate).withDuration("24H").withTime("00:00")
                .withRecurFrequency("WEEKLY").build());
        assertEquals(weeklyEvent.getEventsAtDate(LocalDate.parse(nineDaysAfterFutureDate)), expectedEventList);

        // colliding on day next week
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(oneWeekAfterFutureDate).withDuration("8H").withTime("00:00")
                .withRecurFrequency("WEEKLY").build());
        expectedEventList.add(new EventBuilder().withDate(oneWeekAfterFutureDate).withDuration("14H").withTime("10:00")
                .withRecurFrequency("WEEKLY").build());
        assertEquals(weeklyEvent.getEventsAtDate(LocalDate.parse(oneWeekAfterFutureDate)), expectedEventList);

        // biweekly event
        Event biweeklyEvent = new EventBuilder().withDate(futureDate).withDuration("334H").withTime("10:00")
                .withRecurFrequency("BIWEEKLY").build();

        // not colliding with the date
        assertEquals(biweeklyEvent.getEventsAtDate(LocalDate.parse(pastDate)), new ArrayList<>());

        // colliding on the day itself
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(futureDate).withDuration("14H").withTime("10:00")
                .withRecurFrequency("BIWEEKLY").build());
        assertEquals(biweeklyEvent.getEventsAtDate(LocalDate.parse(futureDate)), expectedEventList);

        // colliding not on the day
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(nineDaysAfterFutureDate).withDuration("24H").withTime("00:00")
                .withRecurFrequency("BIWEEKLY").build());
        assertEquals(biweeklyEvent.getEventsAtDate(LocalDate.parse(nineDaysAfterFutureDate)), expectedEventList);

        // colliding on the same day the following two weeks
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(twoWeeksAfterFutureDate).withDuration("8H").withTime("00:00")
                .withRecurFrequency("BIWEEKLY").build());
        expectedEventList.add(new EventBuilder().withDate(twoWeeksAfterFutureDate).withDuration("14H").withTime("10:00")
                .withRecurFrequency("BIWEEKLY").build());
        assertEquals(biweeklyEvent.getEventsAtDate(LocalDate.parse(twoWeeksAfterFutureDate)), expectedEventList);

        // biweekly event
        biweeklyEvent = new EventBuilder().withDate(futureDate).withDuration("166H").withTime("10:00")
                .withRecurFrequency("BIWEEKLY").build();

        // not colliding with the date
        assertEquals(biweeklyEvent.getEventsAtDate(LocalDate.parse(nineDaysAfterFutureDate)), new ArrayList<>());

        // the following week
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(oneWeekAfterFutureDate).withDuration("8H").withTime("00:00")
                .withRecurFrequency("BIWEEKLY").build());
        assertEquals(biweeklyEvent.getEventsAtDate(LocalDate.parse(oneWeekAfterFutureDate)), expectedEventList);
    }
}
