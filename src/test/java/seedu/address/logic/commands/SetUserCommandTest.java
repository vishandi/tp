package seedu.address.logic.commands;

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
import seedu.address.logic.commands.person.SetUserCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code SetUserCommand}.
 */
public class SetUserCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToSetUser = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        SetUserCommand setUserCommand = new SetUserCommand(INDEX_SECOND_PERSON);

        String expectedMessage = String.format(SetUserCommand.MESSAGE_SET_USER_SUCCESS, personToSetUser.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToSetUser);
        expectedModel.insertPerson(personToSetUser, 0);

        assertCommandSuccess(setUserCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SetUserCommand setUserCommand = new SetUserCommand(outOfBoundIndex);

        assertCommandFailure(setUserCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Person personToSetUser = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        SetUserCommand setUserCommand = new SetUserCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(SetUserCommand.MESSAGE_SET_USER_SUCCESS, personToSetUser.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToSetUser);
        expectedModel.insertPerson(personToSetUser, 0);

        assertCommandSuccess(setUserCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        SetUserCommand setUserCommand = new SetUserCommand(outOfBoundIndex);

        assertCommandFailure(setUserCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

}
