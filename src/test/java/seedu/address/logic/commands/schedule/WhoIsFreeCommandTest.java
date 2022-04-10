package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalSchedule.SE_TUTORIAL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.schedule.IsPersonFreePredicate;
import seedu.address.testutil.PersonBuilder;

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
    public void execute_clashInTimeAndDate_noPersonFound() {
        Person personWithConflictingEvent = new PersonBuilder(AMY).withEvent(SE_TUTORIAL).build();
        AddressBook ab = new AddressBook();
        ab.addPerson(personWithConflictingEvent);

        Model uniqueModel = new ModelManager(ab, new UserPrefs());
        Model expectedUniqueModel = new ModelManager(ab, new UserPrefs());

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        // clash with the starting time
        LocalTime clashTime = SE_TUTORIAL.getTime();
        LocalDate clashDate = SE_TUTORIAL.getClosestStartDate(LocalDate.now());
        IsPersonFreePredicate predicate = new IsPersonFreePredicate(clashTime, clashDate, emptyTags);
        expectedUniqueModel.updateFilteredPersonList(predicate);

        WhoIsFreeCommand command = new WhoIsFreeCommand(predicate);

        assertCommandSuccess(command, uniqueModel, expectedMessage, expectedUniqueModel);
        assertEquals(expectedUniqueModel.getFilteredPersonList(), uniqueModel.getFilteredPersonList());

        // reset models
        uniqueModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedUniqueModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        // clash during occurrence of the event
        clashTime = clashTime.plus(SE_TUTORIAL.getDuration().dividedBy(2));
        predicate = new IsPersonFreePredicate(clashTime, clashDate, emptyTags);
        expectedUniqueModel.updateFilteredPersonList(predicate);

        command = new WhoIsFreeCommand(predicate);

        assertCommandSuccess(command, uniqueModel, expectedMessage, expectedUniqueModel);
        assertEquals(expectedUniqueModel.getFilteredPersonList(), uniqueModel.getFilteredPersonList());
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
