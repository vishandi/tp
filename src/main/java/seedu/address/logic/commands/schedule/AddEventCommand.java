package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditUtil.EditPersonDescriptor;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditTypeCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.Schedule;


public class AddEventCommand extends EditTypeCommand {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an event to the indexed person's schedule in the address book. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_EVENT_DESCRIPTION + "EVENT_DESCRIPTION "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_DURATION + "DURATION] "
            + "[" + PREFIX_RECUR_FREQUENCY + "RECUR_FREQUENCY]\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_EVENT_DESCRIPTION + "CS2103T Tutorial "
            + PREFIX_DATE + "2022-12-28 "
            + PREFIX_TIME + "10:00 "
            + PREFIX_DURATION + "3H30M "
            + PREFIX_RECUR_FREQUENCY + "W";

    public static final String MESSAGE_SUCCESS = "Added %1$s to %2$s's schedule";

    private final Event eventToAdd;
    private final Index targetIndex;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddEventCommand(Index targetIndex, Event event) {
        requireAllNonNull(targetIndex, event);
        this.targetIndex = targetIndex;
        this.eventToAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Schedule scheduleToEdit = personToEdit.getSchedule();
        Schedule updatedSchedule = createEditedSchedule(scheduleToEdit, eventToAdd);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        editPersonDescriptor.setSchedule(updatedSchedule);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, eventToAdd, personToEdit.getName()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddEventCommand)) {
            return false;
        }

        // state check
        AddEventCommand e = (AddEventCommand) other;
        return targetIndex.equals(e.targetIndex)
                && eventToAdd.equals(e.eventToAdd);
    }
}
