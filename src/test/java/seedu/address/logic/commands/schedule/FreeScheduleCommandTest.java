package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.HENDRI;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.schedule.IsPersonFreePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FreeScheduleCommand}.
 */
class FreeScheduleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        LocalTime piTime = LocalTime.parse("03:14");
        LocalDate piDate = LocalDate.parse("2022-03-14");
        LocalTime todayTime = LocalTime.parse("20:30");
        LocalDate todayDate = LocalDate.now();

        IsPersonFreePredicate piDayPredicate = new IsPersonFreePredicate(piTime, piDate); // PI-day
        IsPersonFreePredicate timeAndTodayPredicate = new IsPersonFreePredicate(todayTime, todayDate);

        FreeScheduleCommand freeSchedulePiCommand = new FreeScheduleCommand(piDayPredicate);
        FreeScheduleCommand freeScheduleTodayCommand = new FreeScheduleCommand(timeAndTodayPredicate);

        // same object -> returns true
        assertTrue(freeSchedulePiCommand.equals(freeSchedulePiCommand));

        // same values -> returns true
        FreeScheduleCommand freeSchedulePiCommandCopy = new FreeScheduleCommand(piDayPredicate);
        assertTrue(freeSchedulePiCommand.equals(freeSchedulePiCommandCopy));

        // different types -> returns false
        assertFalse(freeSchedulePiCommandCopy.equals(1));

        // null -> returns false
        assertFalse(freeSchedulePiCommandCopy.equals(null));

        // different person -> returns false
        assertFalse(freeSchedulePiCommandCopy.equals(freeScheduleTodayCommand));
    }

    // To do manual testing
    //    @Test
    //    public void execute_clashInTimeAndDate_noPersonFound() {
    //        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
    //        IsPersonFreePredicate predicate =
    //                new IsPersonFreePredicate(LocalTime.parse("09:00"), LocalDate.parse("2022-03-16"));
    //        FreeScheduleCommand command = new FreeScheduleCommand(predicate);
    //        expectedModel.updateFilteredPersonList(predicate);
    //        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    //    }

    @Test
    public void execute_noClashInTimeAndDate_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        IsPersonFreePredicate predicate =
                new IsPersonFreePredicate(LocalTime.parse("09:00"), LocalDate.parse("2022-03-13"));
        FreeScheduleCommand command = new FreeScheduleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, HENDRI), model.getFilteredPersonList());
    }
}
