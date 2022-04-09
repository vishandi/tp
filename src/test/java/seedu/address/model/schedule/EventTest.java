package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventTest {
    private static final int ONE_YEAR = 365;
    private static String pastDate = LocalDate.now().minusDays(ONE_YEAR).toString();
    private static String futureDate = LocalDate.now().plusDays(ONE_YEAR).toString();
    private static String oneDayAfterFutureDate = LocalDate.now().plusDays(ONE_YEAR + 1).toString();
    private static String oneWeekAfterFutureDate = LocalDate.now().plusDays(ONE_YEAR + 7).toString();
    private static String nineDaysAfterFutureDate = LocalDate.now().plusDays(ONE_YEAR + 9).toString();
    private static String twoWeeksAfterFutureDate = LocalDate.now().plusDays(ONE_YEAR + 14).toString();
    private static String threeDaysBeforeToday = LocalDate.now().minusDays(3).toString();
    private static String oneWeekBeforeToday = LocalDate.now().minusDays(7).toString();
    private static String twoWeeksBeforeToday = LocalDate.now().minusDays(14).toString();

    @Test
    public void getClosestStartDate() {
        // past events
        Event noneEvent = new EventBuilder().withDate(pastDate).withRecurFrequency("NONE").withTime("23:00")
                .withDuration("2H").build();

        assertEquals(LocalDate.parse(pastDate), noneEvent.getClosestStartDate(LocalDate.now()));

        Event dailyEvent = new EventBuilder().withDate(pastDate).withRecurFrequency("DAILY").withTime("23:00")
                .withDuration("2H").build();

        assertEquals(LocalDate.now().minusDays(1), dailyEvent.getClosestStartDate(LocalDate.now()));

        Event weeklyEvent = new EventBuilder().withDate(oneWeekBeforeToday).withRecurFrequency("WEEKLY")
                .withTime("10:00").withDuration("166H").build();

        assertEquals(LocalDate.parse(oneWeekBeforeToday), weeklyEvent.getClosestStartDate(LocalDate.now()));

        weeklyEvent = new EventBuilder().withDate(oneWeekBeforeToday).withRecurFrequency("WEEKLY")
                .withTime("00:00").withDuration("168H").build();

        assertEquals(LocalDate.now(), weeklyEvent.getClosestStartDate(LocalDate.now()));

        Event biweeklyEvent = new EventBuilder().withDate(twoWeeksBeforeToday).withRecurFrequency("BIWEEKLY")
                .withTime("10:00").withDuration("334H").build();

        assertEquals(LocalDate.parse(twoWeeksBeforeToday), biweeklyEvent.getClosestStartDate(LocalDate.now()));

        biweeklyEvent = new EventBuilder().withDate(twoWeeksBeforeToday).withRecurFrequency("BIWEEKLY")
                .withTime("00:00").withDuration("336H").build();

        assertEquals(LocalDate.now(), biweeklyEvent.getClosestStartDate(LocalDate.now()));

        // future events
        Event noneFutureEvent = new EventBuilder().withDate(futureDate).withRecurFrequency("NONE").build();
        Event dailyFutureEvent = new EventBuilder().withDate(futureDate).withRecurFrequency("DAILY").build();
        Event weeklyFutureEvent = new EventBuilder().withDate(futureDate).withRecurFrequency("WEEKLY").build();
        Event biweeklyFutureEvent = new EventBuilder().withDate(futureDate).withRecurFrequency("BIWEEKLY").build();

        assertEquals(LocalDate.parse(futureDate), noneFutureEvent.getClosestStartDate(LocalDate.now()));
        assertEquals(LocalDate.parse(futureDate), dailyFutureEvent.getClosestStartDate(LocalDate.now()));
        assertEquals(LocalDate.parse(futureDate), weeklyFutureEvent.getClosestStartDate(LocalDate.now()));
        assertEquals(LocalDate.parse(futureDate), biweeklyFutureEvent.getClosestStartDate(LocalDate.now()));
    }

    @Test
    public void getClosestEndDate() {
        // past events
        Event noneEvent = new EventBuilder().withDate(pastDate).withRecurFrequency("NONE").withTime("23:00")
                .withDuration("2H").build();

        assertEquals(LocalDate.parse(pastDate).plusDays(1), noneEvent.getClosestEndDate(LocalDate.now()));

        noneEvent = new EventBuilder().withDate(pastDate).withRecurFrequency("NONE").withTime("23:00")
                .withDuration("1H").build();

        assertEquals(LocalDate.parse(pastDate).plusDays(1), noneEvent.getClosestEndDate(LocalDate.now()));

        Event dailyEvent = new EventBuilder().withDate(pastDate).withRecurFrequency("DAILY").withTime("23:00")
                .withDuration("2H").build();

        assertEquals(LocalDate.now(), dailyEvent.getClosestEndDate(LocalDate.now()));

        Event weeklyEvent = new EventBuilder().withDate(oneWeekBeforeToday).withRecurFrequency("WEEKLY")
                .withTime("10:00").withDuration("3H").build();

        assertEquals(LocalDate.now(), weeklyEvent.getClosestEndDate(LocalDate.now()));

        weeklyEvent = new EventBuilder().withDate(oneWeekBeforeToday).withRecurFrequency("WEEKLY")
                .withTime("00:00").withDuration("168H").build();

        assertEquals(LocalDate.now().plusDays(7), weeklyEvent.getClosestEndDate(LocalDate.now()));

        Event biweeklyEvent = new EventBuilder().withDate(twoWeeksBeforeToday).withRecurFrequency("BIWEEKLY")
                .withTime("10:00").withDuration("3H").build();

        assertEquals(LocalDate.now(), biweeklyEvent.getClosestEndDate(LocalDate.now()));

        biweeklyEvent = new EventBuilder().withDate(twoWeeksBeforeToday).withRecurFrequency("BIWEEKLY")
                .withTime("00:00").withDuration("336H").build();

        assertEquals(LocalDate.now().plusDays(14), biweeklyEvent.getClosestEndDate(LocalDate.now()));
    }

    @Test
    public void willDateCollide() {
        // none Event
        Event noneEvent = new EventBuilder().withDate(pastDate).withRecurFrequency("NONE").build();

        assertTrue(noneEvent.willDateCollide(LocalDate.parse(pastDate)));
        assertFalse(noneEvent.willDateCollide(LocalDate.parse(pastDate).minusDays(1)));
        assertFalse(noneEvent.willDateCollide(LocalDate.now()));

        // daily Event
        Event dailyEvent = new EventBuilder().withDate(pastDate).withRecurFrequency("DAILY").withTime("23:00")
                .withDuration("2H").build();

        assertTrue(dailyEvent.willDateCollide(LocalDate.parse(pastDate)));
        assertTrue(dailyEvent.willDateCollide(LocalDate.now()));
        assertFalse(dailyEvent.willDateCollide(LocalDate.parse(pastDate).minusDays(1)));

        // weekly Event
        Event weeklyEvent = new EventBuilder().withDate(twoWeeksBeforeToday).withRecurFrequency("WEEKLY")
                .withTime("10:00").withDuration("2H").build();

        assertTrue(weeklyEvent.willDateCollide(LocalDate.parse(twoWeeksBeforeToday)));
        assertFalse(weeklyEvent.willDateCollide(LocalDate.parse(twoWeeksBeforeToday).minusDays(1)));
        assertFalse(weeklyEvent.willDateCollide(LocalDate.parse(twoWeeksBeforeToday).plusDays(1)));

        // biweekly Event
        Event biweeklyEvent = new EventBuilder().withDate(twoWeeksBeforeToday).withRecurFrequency("BIWEEKLY")
                .withTime("10:00").withDuration("2H").build();

        assertTrue(biweeklyEvent.willDateCollide(LocalDate.parse(twoWeeksBeforeToday)));
        assertFalse(biweeklyEvent.willDateCollide(LocalDate.parse(twoWeeksBeforeToday).minusDays(1)));
        assertFalse(biweeklyEvent.willDateCollide(LocalDate.parse(twoWeeksBeforeToday).plusDays(1)));
    }

    @Test
    public void getNextEvent() {
        Event noneEvent = new EventBuilder().withDate("2021-01-01").withRecurFrequency("NONE").build();
        Event dailyEvent = new EventBuilder().withDate("2021-01-01").withRecurFrequency("DAILY").build();
        Event weeklyEvent = new EventBuilder().withDate("2021-01-01").withRecurFrequency("WEEKLY").build();
        Event biweeklyEvent = new EventBuilder().withDate("2021-01-01").withRecurFrequency("BIWEEKLY").build();
        Event todayEvent = new EventBuilder().withDate(LocalDate.now().toString()).withRecurFrequency("DAILY")
                .withDuration("24H").withTime("00:00").build();

        assertEquals(new EventBuilder().withDate("2021-01-01").withRecurFrequency("NONE").build(),
                noneEvent.getNextEvent());
        assertEquals(new EventBuilder().withDate("2021-01-02").withRecurFrequency("DAILY").build(),
                dailyEvent.getNextEvent());
        assertEquals(new EventBuilder().withDate("2021-01-08").withRecurFrequency("WEEKLY").build(),
                weeklyEvent.getNextEvent());
        assertEquals(new EventBuilder().withDate("2021-01-15").withRecurFrequency("BIWEEKLY").build(),
                biweeklyEvent.getNextEvent());
        assertEquals(new EventBuilder().withDate(LocalDate.now().plusDays(1).toString()).withRecurFrequency("DAILY")
                .withDuration("24H").withTime("00:00").build(), todayEvent.getNextEvent());
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
        assertEquals(new ArrayList<>(), noneEvent.getEventsAtDate(LocalDate.parse("2020-12-31")));
        assertEquals(new ArrayList<>(), noneEvent.getEventsAtDate(LocalDate.parse("2021-01-03")));

        // colliding
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2021-01-01").withDuration("1H").withTime("23:00")
                .withRecurFrequency("NONE").build());
        assertEquals(expectedEventList, noneEvent.getEventsAtDate(LocalDate.parse("2021-01-01")));

        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate("2021-01-02").withDuration("1H").withTime("00:00")
                .withRecurFrequency("NONE").build());
        assertEquals(expectedEventList, noneEvent.getEventsAtDate(LocalDate.parse("2021-01-02")));

        // daily event
        Event dailyEvent = new EventBuilder().withDate(futureDate).withDuration("2H").withTime("23:00")
                .withRecurFrequency("DAILY").build();

        // not colliding with the date
        assertEquals(new ArrayList<>(), dailyEvent.getEventsAtDate(LocalDate.parse(pastDate)));

        // colliding
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(futureDate).withDuration("1H").withTime("23:00")
                .withRecurFrequency("DAILY").build());
        assertEquals(expectedEventList, dailyEvent.getEventsAtDate(LocalDate.parse(futureDate)));

        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(oneDayAfterFutureDate).withDuration("1H").withTime("00:00")
                .withRecurFrequency("DAILY").build());
        expectedEventList.add(new EventBuilder().withDate(oneDayAfterFutureDate).withDuration("1H").withTime("23:00")
                .withRecurFrequency("DAILY").build());
        assertEquals(expectedEventList, dailyEvent.getEventsAtDate(LocalDate.parse(oneDayAfterFutureDate)));

        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(oneWeekAfterFutureDate).withDuration("1H").withTime("00:00")
                .withRecurFrequency("DAILY").build());
        expectedEventList.add(new EventBuilder().withDate(oneWeekAfterFutureDate).withDuration("1H").withTime("23:00")
                .withRecurFrequency("DAILY").build());
        assertEquals(expectedEventList, dailyEvent.getEventsAtDate(LocalDate.parse(oneWeekAfterFutureDate)));

        // weekly event
        Event weeklyEvent = new EventBuilder().withDate(futureDate).withDuration("166H").withTime("10:00")
                .withRecurFrequency("WEEKLY").build();

        // not colliding with the date
        assertEquals(new ArrayList<>(), weeklyEvent.getEventsAtDate(LocalDate.parse(pastDate)));

        // colliding on the day itself
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(futureDate).withDuration("14H").withTime("10:00")
                .withRecurFrequency("WEEKLY").build());
        assertEquals(expectedEventList, weeklyEvent.getEventsAtDate(LocalDate.parse(futureDate)));

        // colliding not on the day
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(nineDaysAfterFutureDate).withDuration("24H").withTime("00:00")
                .withRecurFrequency("WEEKLY").build());
        assertEquals(expectedEventList, weeklyEvent.getEventsAtDate(LocalDate.parse(nineDaysAfterFutureDate)));

        // colliding on day next week
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(oneWeekAfterFutureDate).withDuration("8H").withTime("00:00")
                .withRecurFrequency("WEEKLY").build());
        expectedEventList.add(new EventBuilder().withDate(oneWeekAfterFutureDate).withDuration("14H").withTime("10:00")
                .withRecurFrequency("WEEKLY").build());
        assertEquals(expectedEventList, weeklyEvent.getEventsAtDate(LocalDate.parse(oneWeekAfterFutureDate)));

        weeklyEvent = new EventBuilder().withDate(oneWeekBeforeToday).withRecurFrequency("WEEKLY")
                .withTime("10:00").withDuration("27H").build();

        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(LocalDate.now().toString()).withDuration("14H")
                .withTime("10:00").withRecurFrequency("WEEKLY").build());

        assertEquals(expectedEventList, weeklyEvent.getEventsAtDate(LocalDate.now()));

        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(LocalDate.now().plusDays(1).toString()).withDuration("13H")
                .withTime("00:00").withRecurFrequency("WEEKLY").build());

        assertEquals(expectedEventList, weeklyEvent.getEventsAtDate(LocalDate.now().plusDays(1)));

        // biweekly event
        Event biweeklyEvent = new EventBuilder().withDate(futureDate).withDuration("334H").withTime("10:00")
                .withRecurFrequency("BIWEEKLY").build();

        // not colliding with the date
        assertEquals(new ArrayList<>(), biweeklyEvent.getEventsAtDate(LocalDate.parse(pastDate)));

        // colliding on the day itself
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(futureDate).withDuration("14H").withTime("10:00")
                .withRecurFrequency("BIWEEKLY").build());
        assertEquals(expectedEventList, biweeklyEvent.getEventsAtDate(LocalDate.parse(futureDate)));

        // colliding not on the day
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(nineDaysAfterFutureDate).withDuration("24H").withTime("00:00")
                .withRecurFrequency("BIWEEKLY").build());
        assertEquals(expectedEventList, biweeklyEvent.getEventsAtDate(LocalDate.parse(nineDaysAfterFutureDate)));

        // colliding on the same day the following two weeks
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(twoWeeksAfterFutureDate).withDuration("8H").withTime("00:00")
                .withRecurFrequency("BIWEEKLY").build());
        expectedEventList.add(new EventBuilder().withDate(twoWeeksAfterFutureDate).withDuration("14H").withTime("10:00")
                .withRecurFrequency("BIWEEKLY").build());
        assertEquals(expectedEventList, biweeklyEvent.getEventsAtDate(LocalDate.parse(twoWeeksAfterFutureDate)));

        // biweekly event
        biweeklyEvent = new EventBuilder().withDate(futureDate).withDuration("166H").withTime("10:00")
                .withRecurFrequency("BIWEEKLY").build();

        // not colliding with the date
        assertEquals(new ArrayList<>(), biweeklyEvent.getEventsAtDate(LocalDate.parse(nineDaysAfterFutureDate)));

        // the following week
        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(oneWeekAfterFutureDate).withDuration("8H").withTime("00:00")
                .withRecurFrequency("BIWEEKLY").build());
        assertEquals(expectedEventList, biweeklyEvent.getEventsAtDate(LocalDate.parse(oneWeekAfterFutureDate)));

        biweeklyEvent = new EventBuilder().withDate(twoWeeksBeforeToday).withRecurFrequency("BIWEEKLY")
                .withTime("10:00").withDuration("27H").build();

        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(LocalDate.now().toString()).withDuration("14H")
                .withTime("10:00").withRecurFrequency("BIWEEKLY").build());

        assertEquals(expectedEventList, biweeklyEvent.getEventsAtDate(LocalDate.now()));

        expectedEventList = new ArrayList<>();
        expectedEventList.add(new EventBuilder().withDate(LocalDate.now().plusDays(1).toString()).withDuration("13H")
                .withTime("00:00").withRecurFrequency("BIWEEKLY").build());

        assertEquals(expectedEventList, biweeklyEvent.getEventsAtDate(LocalDate.now().plusDays(1)));
    }
}
