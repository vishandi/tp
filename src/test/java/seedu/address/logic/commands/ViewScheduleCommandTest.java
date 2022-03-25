package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.person.ViewScheduleCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.SamePersonPredicate;

public class ViewScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewScheduleCommand viewScheduleCommand = new ViewScheduleCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ViewScheduleCommand.MESSAGE_VIEW_PERSON_SUCCESS,
                personToView.getName().value);

        SamePersonPredicate predicate = new SamePersonPredicate(personToView);
        expectedModel.updateViewSchedulePerson(predicate);

        assertCommandSuccess(viewScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewScheduleCommand viewScheduleCommand = new ViewScheduleCommand(outOfBoundIndex);

        assertCommandFailure(viewScheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewScheduleCommand viewScheduleCommand = new ViewScheduleCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ViewScheduleCommand.MESSAGE_VIEW_PERSON_SUCCESS,
                personToView.getName().value);

        SamePersonPredicate predicate = new SamePersonPredicate(personToView);
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updateViewSchedulePerson(predicate);

        assertCommandSuccess(viewScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ViewScheduleCommand viewScheduleCommand = new ViewScheduleCommand(outOfBoundIndex);

        assertCommandFailure(viewScheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewScheduleCommand viewFirstCommand = new ViewScheduleCommand(INDEX_FIRST_PERSON);
        ViewScheduleCommand viewSecondCommand = new ViewScheduleCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewScheduleCommand viewFirstCommandCopy = new ViewScheduleCommand(INDEX_FIRST_PERSON);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }
}
