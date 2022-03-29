package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ClearScheduleCommand}.
 */
public class ClearScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToClearSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ClearScheduleCommand clearScheduleCommand = new ClearScheduleCommand(INDEX_FIRST_PERSON);

        String expectedMessage =
                String.format(ClearScheduleCommand.MESSAGE_CLEAR_SCHEDULE_SUCCESS, personToClearSchedule.getName());

        CommandResult commandResult = clearScheduleCommand.execute(model);
        assertEquals(commandResult, new CommandResult(expectedMessage));

        Person updatedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertTrue(updatedPerson.getSchedule().isEmpty());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ClearScheduleCommand clearScheduleCommand = new ClearScheduleCommand(outOfBoundIndex);

        assertCommandFailure(clearScheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToClearSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ClearScheduleCommand clearScheduleCommand = new ClearScheduleCommand(INDEX_FIRST_PERSON);

        String expectedMessage =
                String.format(ClearScheduleCommand.MESSAGE_CLEAR_SCHEDULE_SUCCESS, personToClearSchedule.getName());

        CommandResult commandResult = clearScheduleCommand.execute(model);
        assertEquals(commandResult, new CommandResult(expectedMessage));

        Person updatedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertTrue(updatedPerson.getSchedule().isEmpty());
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ClearScheduleCommand clearScheduleCommand = new ClearScheduleCommand(outOfBoundIndex);

        assertCommandFailure(clearScheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ClearScheduleCommand clearScheduleFirstCommand = new ClearScheduleCommand(INDEX_FIRST_PERSON);
        ClearScheduleCommand clearScheduleSecondCommand = new ClearScheduleCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(clearScheduleFirstCommand.equals(clearScheduleFirstCommand));

        // same values -> returns true
        ClearScheduleCommand clearScheduleFirstCommandCopy = new ClearScheduleCommand(INDEX_FIRST_PERSON);
        assertTrue(clearScheduleFirstCommand.equals(clearScheduleFirstCommandCopy));

        // different types -> returns false
        assertFalse(clearScheduleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(clearScheduleFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(clearScheduleFirstCommand.equals(clearScheduleSecondCommand));
    }
}
