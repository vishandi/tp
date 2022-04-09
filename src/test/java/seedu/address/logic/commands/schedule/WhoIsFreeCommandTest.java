package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Tag;
import seedu.address.model.schedule.IsPersonFreePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FreeScheduleCommand}.
 */
class WhoIsFreeCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Set<Tag> emptyTags = new HashSet<>();

    @Test
    public void equals() {
        LocalTime piTime = LocalTime.parse("03:14");
        LocalDate piDate = LocalDate.parse("2022-03-14");
        LocalTime todayTime = LocalTime.parse("20:30");
        LocalDate todayDate = LocalDate.now();

        IsPersonFreePredicate piDayPredicate = new IsPersonFreePredicate(piTime, piDate, emptyTags); // PI-day
        IsPersonFreePredicate timeAndTodayPredicate = new IsPersonFreePredicate(todayTime, todayDate, emptyTags);

        WhoIsFreeCommand whoIsFreePiCommand = new WhoIsFreeCommand(piDayPredicate);
        WhoIsFreeCommand whoIsFreeTodayCommand = new WhoIsFreeCommand(timeAndTodayPredicate);

        // same object -> returns true
        assertTrue(whoIsFreePiCommand.equals(whoIsFreePiCommand));

        // same values -> returns true
        WhoIsFreeCommand whoIsFreePiCommandCopy = new WhoIsFreeCommand(piDayPredicate);
        assertTrue(whoIsFreePiCommand.equals(whoIsFreePiCommandCopy));

        // different types -> returns false
        assertFalse(whoIsFreePiCommandCopy.equals(1));

        // null -> returns false
        assertFalse(whoIsFreePiCommandCopy.equals(null));

        // different person -> returns false
        assertFalse(whoIsFreePiCommandCopy.equals(whoIsFreeTodayCommand));
    }

    @Test
    public void execute_noClashInTimeAndDate_multiplePersonsFound() {
        IsPersonFreePredicate predicate =
                new IsPersonFreePredicate(LocalTime.parse("09:00"), LocalDate.parse("2022-03-13"), emptyTags);
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage =
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        WhoIsFreeCommand command = new WhoIsFreeCommand(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }
}
