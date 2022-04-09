package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Schedule;
import seedu.address.storage.JsonAdaptedSchedule;

/**
 * Exports the {@code Schedule} of a {@code Person}.
 */
public class ExportScheduleCommand extends Command {

    public static final String COMMAND_WORD = "exportSchedule";
    public static final String COMMAND_WORD_LOWER = "exportschedule";
    public static final String MESSAGE_SUCCESS = "%1$s's schedule exported to %2$s!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the schedule of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SCHEDULE_EMPTY = "No existing schedule for %1$s.";

    private static final Logger logger = LogsCenter.getLogger(ExportScheduleCommand.class);
    private final Index targetIndex;

    /**
     * @param index of the person's schedule to export
     */
    public ExportScheduleCommand(Index index) {
        targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Exporting schedule...");

        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person targetPerson = lastShownList.get(targetIndex.getZeroBased());

        Schedule toExportSchedule = targetPerson.getSchedule();
        if (toExportSchedule.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_SCHEDULE_EMPTY, targetPerson.getName()));
        }

        Path exportFile = Paths.get("data", "export", String.format("%1$s.json", targetPerson.getName()));
        try {
            FileUtil.createIfMissing(exportFile);
            JsonUtil.saveJsonFile(new JsonAdaptedSchedule(toExportSchedule), exportFile);
            logger.info(targetPerson.getName() + "'s schedule saved at " + exportFile + ".");
        } catch (IOException e) {
            logger.warning("Unable to save " + targetPerson.getName() + "'s schedule in " + exportFile + ": " + e);
            throw new CommandException("An error has occurred. Schedule not exported!");
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetPerson.getName(), exportFile));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instance of handles nulls
        if (!(other instanceof ExportScheduleCommand)) {
            return false;
        }

        // state check
        ExportScheduleCommand e = (ExportScheduleCommand) other;
        return targetIndex.equals(e.targetIndex);
    }
}
