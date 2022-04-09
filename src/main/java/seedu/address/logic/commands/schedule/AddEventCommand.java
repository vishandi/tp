package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.schedule.Event.DURATION_RECUR_FREQ_MESSAGE_CONSTRAINTS;
import static seedu.address.model.schedule.Schedule.MESSAGE_DUPLICATE_EVENT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.SamePersonPredicate;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.Schedule;


public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";
    public static final String COMMAND_WORD_LOWER = "addevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an event to the indexed person's schedule in the address book.\n"
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

        if (!eventToAdd.isValidDurationWithRecurFrequency()) {
            throw new CommandException(DURATION_RECUR_FREQ_MESSAGE_CONSTRAINTS);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Schedule scheduleToEdit = personToEdit.getSchedule();
        Schedule updatedSchedule = createEditedSchedule(scheduleToEdit, eventToAdd);

        model.setSchedule(personToEdit, updatedSchedule);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateViewSchedulePerson(new SamePersonPredicate(personToEdit));
        return new CommandResult(String.format(MESSAGE_SUCCESS, eventToAdd, personToEdit.getName()));
    }

    /**
     * Creates and returns a {@code Schedule} with the details of {@code eventToEdit}
     * with an added {@code eventToAdd}.
     */
    private static Schedule createEditedSchedule(Schedule scheduleToEdit, Event eventToAdd) throws CommandException {
        assert scheduleToEdit != null;

        if (scheduleToEdit.hasEvent(eventToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
        List<Event> scheduleEvents = scheduleToEdit.getEvents();
        ArrayList<Event> updatedEvents = new ArrayList<>(scheduleEvents);
        updatedEvents.add(eventToAdd);
        Collections.sort(updatedEvents);
        return new Schedule(updatedEvents);
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
