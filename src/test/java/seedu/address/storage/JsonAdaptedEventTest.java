package seedu.address.storage;

import static java.time.temporal.TemporalAdjusters.next;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSchedule.SE_TUTORIAL;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.schedule.RecurFrequency;

public class JsonAdaptedEventTest {
    private static final String INVALID_EVENT_DESCRIPTION = "";
    private static final String INVALID_DATE = "20-12-2022";
    private static final String INVALID_TIME = "09:000";
    private static final String INVALID_DURATION = "8HdM";
    private static final String INVALID_RECURFREQUENCY = "non-recurring";

    private static final String VALID_EVENT_DESCRIPTION = SE_TUTORIAL.getEventDescription().toString();
    private static final String VALID_DATE = SE_TUTORIAL.getDate().toString();
    private static final String VALID_TIME = SE_TUTORIAL.getTime().toString();
    private static final String VALID_DURATION = SE_TUTORIAL.getDuration().toString();
    private static final String VALID_RECURFREQUENCY = SE_TUTORIAL.getRecurFrequency().toString();

    private static final String RECURFREQUENCY_DAILY = "D";
    private static final String RECURFREQUENCY_WEEKLY = "WEEKLY";
    private static final String RECURFREQUENCY_BIWEEKLY = "BIWEEKLY";

    private static final LocalDate PAST_RESET_DATE_ODD_LOCALDATE = LocalDate.of(2020, 3, 14);
    private static final DayOfWeek PAST_RESET_DATE_ODD_DAYOFWEEK = PAST_RESET_DATE_ODD_LOCALDATE.getDayOfWeek();
    private static final String PAST_RESET_DATE_ODD = PAST_RESET_DATE_ODD_LOCALDATE.toString();

    private static final LocalDate PAST_RESET_DATE_EVEN_LOCALDATE = LocalDate.of(2020, 3, 7);
    private static final DayOfWeek PAST_RESET_DATE_EVEN_DAYOFWEEK = PAST_RESET_DATE_EVEN_LOCALDATE.getDayOfWeek();
    private static final String PAST_RESET_DATE_EVEN = PAST_RESET_DATE_EVEN_LOCALDATE.toString();

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(SE_TUTORIAL);
        assertEquals(SE_TUTORIAL, event.toModelType());
    }

    @Test
    public void toModelType_invalidEventDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                INVALID_EVENT_DESCRIPTION, VALID_DATE, VALID_TIME, VALID_DURATION, VALID_RECURFREQUENCY);
        String expectedMessage = EventDescription.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                null, VALID_DATE, VALID_TIME, VALID_DURATION, VALID_RECURFREQUENCY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventDescription.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_EVENT_DESCRIPTION, INVALID_DATE, VALID_TIME, VALID_DURATION, VALID_RECURFREQUENCY);;
        String expectedMessage = Event.DATE_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_EVENT_DESCRIPTION, null, VALID_TIME, VALID_DURATION, VALID_RECURFREQUENCY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_EVENT_DESCRIPTION, VALID_DATE, INVALID_TIME, VALID_DURATION, VALID_RECURFREQUENCY);
        String expectedMessage = Event.TIME_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_EVENT_DESCRIPTION, VALID_DATE, null, VALID_DURATION, VALID_RECURFREQUENCY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDuration_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_EVENT_DESCRIPTION, VALID_DATE, VALID_TIME, INVALID_DURATION, VALID_RECURFREQUENCY);
        String expectedMessage = Event.DURATION_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_EVENT_DESCRIPTION, VALID_DATE, VALID_TIME, null, VALID_RECURFREQUENCY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Duration.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidRecurFrequency_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_EVENT_DESCRIPTION, VALID_DATE, VALID_TIME, VALID_DURATION, INVALID_RECURFREQUENCY);
        String expectedMessage = RecurFrequency.INVALID_RECUR_FREQUENCY_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullRecurFrequency_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_EVENT_DESCRIPTION, VALID_DATE, VALID_TIME, VALID_DURATION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RecurFrequency.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_updateDaily_success() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_EVENT_DESCRIPTION, PAST_RESET_DATE_ODD, VALID_TIME, VALID_DURATION, RECURFREQUENCY_DAILY);
        LocalDate expectedDate = LocalDate.now();
        assertEquals(expectedDate, event.toModelType().getDate());
    }

    @Test
    public void toModelType_updateWeekly_success() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_EVENT_DESCRIPTION, PAST_RESET_DATE_ODD, VALID_TIME, VALID_DURATION, RECURFREQUENCY_WEEKLY);
        LocalDate expectedDate = LocalDate.now().with(next(PAST_RESET_DATE_ODD_DAYOFWEEK));
        assertEquals(expectedDate, event.toModelType().getDate());
    }

    @Test
    public void toModelType_updateOddWeekBiweekly_success() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_EVENT_DESCRIPTION, PAST_RESET_DATE_ODD, VALID_TIME, VALID_DURATION, RECURFREQUENCY_BIWEEKLY);
        LocalDate today = LocalDate.now();
        LocalDate expectedDate = today.with(next(PAST_RESET_DATE_ODD_DAYOFWEEK));
        if (ChronoUnit.DAYS.between(PAST_RESET_DATE_ODD_LOCALDATE, expectedDate) % 14 != 0) {
            expectedDate = expectedDate.with(next(PAST_RESET_DATE_ODD_DAYOFWEEK));
        }
        assertEquals(expectedDate, event.toModelType().getDate());
    }

    @Test
    public void toModelType_updateEvenWeekBiweekly_success() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_EVENT_DESCRIPTION, PAST_RESET_DATE_EVEN, VALID_TIME, VALID_DURATION, RECURFREQUENCY_BIWEEKLY);
        LocalDate today = LocalDate.now();
        LocalDate expectedDate = today.with(next(PAST_RESET_DATE_EVEN_DAYOFWEEK));
        if (ChronoUnit.DAYS.between(PAST_RESET_DATE_EVEN_LOCALDATE, expectedDate) % 14 != 0) {
            expectedDate = expectedDate.with(next(PAST_RESET_DATE_EVEN_DAYOFWEEK));
        }
        assertEquals(expectedDate, event.toModelType().getDate());
    }
}
