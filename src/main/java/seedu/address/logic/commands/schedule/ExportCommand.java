package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.Schedule;
import seedu.address.storage.JsonAdaptedSchedule;

/**
 * Exports the {@code Schedule} of a {@code Person}.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SUCCESS = "%1$s's schedule exported!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the schedule of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    /**
     * @param index of the person's schedule to export
     */
    public ExportCommand(Index index) {
        targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(targetIndex.getZeroBased());
        Schedule toStoreSchedule = targetPerson.getSchedule();

        if (Schedule.isEmptySchedule(toStoreSchedule)) {
            throw new CommandException(String.format(Schedule.EMPTY_SCHEDULE_MESSAGE, targetPerson.getName()));
        }

        Path exportFile = Paths.get("data", String.format("%1$s.json", targetPerson.getName()));
        try {
            JsonUtil.saveJsonFile(new JsonAdaptedSchedule(toStoreSchedule), exportFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetPerson.getName()));
    }
}
