package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.TypicalSchedule.getTypicalEvents;
import static seedu.address.testutil.TypicalSchedule.getTypicalSchedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class IsPersonFreePredicateTest {
    @Test
    public void equals() {
        LocalTime piTime = LocalTime.parse("03:14");
        LocalDate piDate = LocalDate.parse("2022-03-14");
        LocalTime todayTime = LocalTime.parse("20:30");
        LocalDate todayDate = LocalDate.now();

        IsPersonFreePredicate piDayPredicate = new IsPersonFreePredicate(piTime, piDate); // PI-day
        IsPersonFreePredicate timeAndTodayPredicate = new IsPersonFreePredicate(todayTime, todayDate);

        // same object -> returns true
        assertTrue(piDayPredicate.equals(piDayPredicate));

        // same values -> returns true
        IsPersonFreePredicate timeAndDateCopy = new IsPersonFreePredicate(piTime, piDate);
        assertTrue(piDayPredicate.equals(timeAndDateCopy));

        // different types -> returns false
        assertFalse(piDayPredicate.equals(1));

        // null -> returns false
        assertFalse(piDayPredicate.equals(null));

        // different time and date -> returns false
        assertFalse(piDayPredicate.equals(timeAndTodayPredicate));
    }

    @Test
    public void test_isPersonFree_returnsTrue() {
        Person typicalStudent = new PersonBuilder().withSchedule(getTypicalSchedule()).build();

        // Check no clashes
        IsPersonFreePredicate predicate =
                new IsPersonFreePredicate(LocalTime.parse("18:00"), LocalDate.parse("2022-03-13")); // this is a Sunday
        assertTrue(predicate.test(typicalStudent));

        // Check clash with date but not time
        predicate = new IsPersonFreePredicate(LocalTime.parse("18:00"), LocalDate.parse("2022-03-16"));
        assertTrue(predicate.test(typicalStudent));

        // Check clash with time but not date
        predicate = new IsPersonFreePredicate(LocalTime.parse("10:00"), LocalDate.parse("2022-03-15"));
        assertTrue(predicate.test(typicalStudent));
    }

    @Test
    public void test_isPersonFree_returnsFalse() {
        Schedule schedule = new Schedule(getTypicalEvents());
        Person studentWithSchedule = new PersonBuilder().withSchedule(schedule).build();
        Person studentWithoutSchedule = new PersonBuilder().build();
        IsPersonFreePredicate predicate =
                new IsPersonFreePredicate(LocalTime.parse("10:00"), LocalDate.parse("2022-03-14"));

        // Clash time and date
        assertFalse(predicate.test(studentWithSchedule));

        // No schedule
        assertFalse(predicate.test(studentWithoutSchedule));
    }
}