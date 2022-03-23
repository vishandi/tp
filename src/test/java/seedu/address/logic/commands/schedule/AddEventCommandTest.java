package seedu.address.logic.commands.schedule;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddEventCommand}.
 */
class AddEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    private Person firstPerson = expectedModel.getFilteredPersonList().get(0);
    private EventBuilder eventBuilder;
    private List<Event> toEditEvents;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        eventBuilder = new EventBuilder();
        toEditEvents = new ArrayList<>(firstPerson.getSchedule().getEvents());
    }

    @Test
    public void execute_allFieldsSpecified_success() {
        Event toAddEvent = eventBuilder.build();
        AddEventCommand addEventCommand =
                new AddEventCommand(INDEX_FIRST_PERSON, toAddEvent);
        toEditEvents.add(toAddEvent);
        Collections.sort(toEditEvents);
        Schedule schedule = new Schedule(toEditEvents);
        Person editedPerson = new PersonBuilder(firstPerson).withSchedule(schedule).build();

        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        String expectedMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, toAddEvent, firstPerson.getName());

        assertCommandSuccess(addEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Event toAddEvent = eventBuilder.build();
        AddEventCommand addEventCommand = new AddEventCommand(outOfBoundIndex, toAddEvent);
        assertCommandFailure(addEventCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

}
