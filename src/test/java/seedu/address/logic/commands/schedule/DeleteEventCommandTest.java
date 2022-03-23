package seedu.address.logic.commands.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_HENDRI;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model)
 * and unit tests for DeleteEventCommand.
 */
public class DeleteEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndices_success() {
        Index indexHendri = INDEX_HENDRI;
        Index indexFirstEvent = INDEX_FIRST_EVENT;
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(
                indexHendri,
                indexFirstEvent);
        Person hendri = getTypicalPersons().get(
                INDEX_HENDRI.getZeroBased());

        PersonBuilder editPerson = new PersonBuilder(hendri);
        Person editedPerson = editPerson
                .withSchedule(new Schedule(new ArrayList<>())).build();

        String expectedMessage = String.format(
                DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                hendri.getName(),
                indexFirstEvent.getOneBased()
        );

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_HENDRI.getZeroBased()), editedPerson);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(
                outOfBoundIndex,
                outOfBoundIndex
        );

        assertCommandFailure(deleteEventCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidEventIndex_failure() {
        Person hendri = getTypicalPersons().get(
                INDEX_HENDRI.getZeroBased());
        Index outOfBoundIndex =
                Index.fromOneBased(hendri.getSchedule().getEvents().size() + 1);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(
                INDEX_HENDRI,
                outOfBoundIndex
        );

        assertCommandFailure(deleteEventCommand, model, MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }
}
