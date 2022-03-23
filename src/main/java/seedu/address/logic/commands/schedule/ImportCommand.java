package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.EditUtil.EditPersonDescriptor;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditTypeCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Schedule;
import seedu.address.storage.JsonAdaptedSchedule;


/**
 * Imports a {@code Schedule} from the specified json file and replaces the indexed {@code Person}'s schedule with
 * the imported {@code Schedule}
 */
public class ImportCommand extends EditTypeCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports a schedule from a json file and "
            + "replaces the indexed person's schedule with the imported schedule.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) "
            + PREFIX_FILEPATH + "FILEPATH\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_FILEPATH + "typicalSchedule.json";

    public static final String MESSAGE_SUCCESS = "%1$s's schedule has been replaced with the imported schedule!";
    public static final String MESSAGE_FAILURE =
            "Data in the specified file path is in invalid format, %1$s's schedule is unchanged.";
    public static final String EMPTY_FILE_MESSAGE = "File is empty! %1$s's schedule is unchanged.";

    private final Index targetIndex;
    private final Path filePath;

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param filePath of the file to be read
     */
    public ImportCommand(Index targetIndex, Path filePath) {
        this.targetIndex = targetIndex;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, DataConversionException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());

        Optional<JsonAdaptedSchedule> importedJsonAdaptedSchedule = JsonUtil.readJsonFile(filePath, JsonAdaptedSchedule.class);
        if (!importedJsonAdaptedSchedule.isPresent()) {
            return new CommandResult(String.format(EMPTY_FILE_MESSAGE, personToEdit.getName()));
        }
        
        Schedule importedSchedule;
        try {
            importedSchedule = importedJsonAdaptedSchedule.get().toModelType();
        } catch (IllegalValueException e) {
            return new CommandResult(String.format(MESSAGE_FAILURE, personToEdit.getName()));
        }
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        editPersonDescriptor.setSchedule(importedSchedule);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                personToEdit.getName()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ImportCommand)) {
            return false;
        }

        // state check
        ImportCommand e = (ImportCommand) other;
        return targetIndex.equals(e.targetIndex)
                && filePath.equals(e.filePath);
    }
}
