package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RECUR_FREQUENCY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditUtil.EditEventDescriptor;
import seedu.address.logic.commands.person.ClearCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditEventCommand.
 */
class EditEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        Event editedEvent = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person toEditPerson = expectedModel.getFilteredPersonList().get(0);
        List<Event> toEditEvents = new ArrayList<>(toEditPerson.getSchedule().getEvents());
        toEditEvents.remove(0);
        toEditEvents.add(0, editedEvent);
        Schedule schedule = new Schedule(toEditEvents);
        Person editedPerson = new PersonBuilder(toEditPerson).withSchedule(schedule).build();

        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    // Not sure why this test method pass on computer but not with gradlew. Need more time to look into the problem.
    @Test
    public void execute_someFieldsSpecified_success() {
        Person firstPerson = model.getFilteredPersonList().get(0);
        List<Event> toEditEvents = new ArrayList<>(firstPerson.getSchedule().getEvents());
        Index lastEventIndex = Index.fromOneBased(toEditEvents.size());

        Event toEditEvent = toEditEvents.remove(lastEventIndex.getZeroBased());
        Event editedEvent = new EventBuilder(toEditEvent)
                .withEventDescription(VALID_EVENT_DESCRIPTION).withRecurFrequency(VALID_RECUR_FREQUENCY).build();
        toEditEvents.add(lastEventIndex.getZeroBased(), editedEvent);
        Schedule schedule = new Schedule(toEditEvents);
        Person editedPerson = new PersonBuilder(firstPerson).withSchedule(schedule).build();

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventDescription(VALID_EVENT_DESCRIPTION).withRecurFrequency(VALID_RECUR_FREQUENCY).build();
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_PERSON, lastEventIndex, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDate(VALID_EVENT_DATE).build();
        EditEventCommand editCommand = new EditEventCommand(outOfBoundIndex, INDEX_FIRST_EVENT, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validPersonIndexInvalidEventIndex_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(firstPerson.getSchedule().getEvents().size() + 1);
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDate(VALID_EVENT_DATE).build();
        EditEventCommand editCommand = new EditEventCommand(INDEX_FIRST_PERSON, outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    // Only unfiltered list is tested. FilteredList will be test after v1.2

    @Test
    public void equals() {
        final EditEventCommand standardCommand =
                new EditEventCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT, DESC_CS2101);
        EditEventDescriptor differentDescriptor = new EditEventDescriptorBuilder().withDate("2022-12-31").build();

        // same values -> returns true
        EditEventDescriptor copyDescriptor = new EditEventDescriptor(DESC_CS2101);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_SECOND_PERSON, INDEX_FIRST_EVENT, DESC_CS2101)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
                new EditEventCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT, differentDescriptor)));
    }
}
