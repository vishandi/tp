package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.SamePersonPredicate;
import seedu.address.model.schedule.Schedule;
import seedu.address.storage.JsonAdaptedSchedule;


/**
 * Imports a {@code Schedule} from the specified json file and replaces the indexed {@code Person}'s schedule with
 * the imported {@code Schedule}
 */
public class ImportScheduleCommand extends Command {

    public static final String COMMAND_WORD = "importSchedule";
    public static final String COMMAND_WORD_LOWER = "importschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports a schedule from a json file and "
            + "replaces the indexed person's schedule with the imported schedule.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) "
            + PREFIX_FILEPATH + "FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_FILEPATH + "typicalSchedule.json";

    public static final String MESSAGE_SUCCESS = "%1$s's schedule has been replaced with the imported schedule!";
    public static final String FILE_DOES_NOT_EXIST_MESSAGE = "\"%1$s\" does not exist in \"%2$s\"";
    public static final String FILE_PATH_IS_DIRECTORY_MESSAGE = "File path should not be a directory!";
    public static final String FILE_NOT_READABLE_MESSAGE = "You do not have access to the specified file path!";
    public static final String UNKNOWN_ERROR_MESSAGE = "Unknown error when reading the file!";
    public static final String VALID_DATA_FORMAT_EXAMPLE = "{\n"
            + "  \"events\" : [ {\n"
            + "    \"eventDescription\" : \"CS2106 Tutorial\",\n"
            + "    \"date\" : \"2022-03-14\",\n"
            + "    \"time\" : \"10:00:00\",\n"
            + "    \"duration\" : \"PT1H\",\n"
            + "    \"recurFrequency\" : \"WEEKLY\"\n"
            + "  }, {\n"
            + "    \"eventDescription\" : \"Alice Birthday Surprise\",\n"
            + "    \"date\" : \"2022-03-14\",\n"
            + "    \"time\" : \"17:00:00\",\n"
            + "    \"duration\" : \"PT4H\",\n"
            + "    \"recurFrequency\" : \"WEEKLY\"\n"
            + "  } ]\n"
            + "}";
    public static final Path TEMPLATE_FILE_PATH = Paths.get("data", "template", "template.json");
    public static final String REFER_TEMPLATE_MESSAGE =
            String.format("Refer to %s for a valid Json template.", TEMPLATE_FILE_PATH);

    public static final String NOT_JSON_FORMAT_MESSAGE = "\"%s\" is empty or not in valid json format!";
    public static final String NOT_JSON_FORMAT_REFER_TEMPLATE =
            NOT_JSON_FORMAT_MESSAGE + "\n" + REFER_TEMPLATE_MESSAGE;
    public static final String NOT_JSON_FORMAT_WITH_EXAMPLE =
            NOT_JSON_FORMAT_MESSAGE + "\nExample of valid data:\n" + VALID_DATA_FORMAT_EXAMPLE;

    public static final String INVALID_DATA_MESSAGE = "Data contains invalid headers and/or values!";
    public static final String INVALID_DATA_REFER_TEMPLATE = INVALID_DATA_MESSAGE + "\n" + REFER_TEMPLATE_MESSAGE;
    public static final String INVALID_DATA_WITH_EXAMPLE =
            INVALID_DATA_MESSAGE + "\nExample of valid data:\n" + VALID_DATA_FORMAT_EXAMPLE;

    private final Index targetIndex;
    private final Path filePath;

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param filePath of the file to be read
     */
    public ImportScheduleCommand(Index targetIndex, Path filePath) {
        this.targetIndex = targetIndex;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        checkValidFilePath(filePath);
        Schedule importedSchedule = convertFileDataToSchedule(filePath);

        model.setSchedule(personToEdit, importedSchedule);
        model.updateViewSchedulePerson(new SamePersonPredicate(personToEdit));
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit.getName()));
    }

    /**
     * Converts the data in the specified file path into a {@code Schedule}.
     *
     * @throws CommandException if the data is in invalid format or contains illegal values and/or headers
     */
    private Schedule convertFileDataToSchedule(Path filePath) throws CommandException {
        Optional<JsonAdaptedSchedule> importedJsonAdaptedSchedule;
        try {
            importedJsonAdaptedSchedule = JsonUtil.readJsonFile(filePath, JsonAdaptedSchedule.class);
        } catch (DataConversionException ive) {
            createTemplate(String.format(NOT_JSON_FORMAT_WITH_EXAMPLE, filePath.getFileName()));
            throw new CommandException(String.format(NOT_JSON_FORMAT_REFER_TEMPLATE, filePath.getFileName()));
        }

        if (!importedJsonAdaptedSchedule.isPresent()) {
            throw new CommandException(String.format(UNKNOWN_ERROR_MESSAGE));
        }

        Schedule importedSchedule;
        try {
            importedSchedule = importedJsonAdaptedSchedule.get().toModelType();
        } catch (IllegalValueException ive) {
            createTemplate(String.format(INVALID_DATA_WITH_EXAMPLE, filePath.getFileName()));
            throw new CommandException(String.format(INVALID_DATA_REFER_TEMPLATE));
        }

        if (importedSchedule.isEmpty()) {
            createTemplate(String.format(INVALID_DATA_WITH_EXAMPLE, filePath.getFileName()));
            throw new CommandException(String.format(INVALID_DATA_REFER_TEMPLATE));
        }
        return importedSchedule;
    }

    /**
     * Checks if the provided filePath is a readable file.
     *
     * @throws CommandException if the file path is empty, a directory or is not readable
     */
    private void checkValidFilePath(Path filePath) throws CommandException {
        if (Files.notExists(filePath)) {
            throw new CommandException(String.format(
                    FILE_DOES_NOT_EXIST_MESSAGE, filePath.getFileName(), filePath.toAbsolutePath().getParent()));
        }
        if (Files.isDirectory(filePath)) {
            throw new CommandException(String.format(FILE_PATH_IS_DIRECTORY_MESSAGE, filePath));
        }
        if (!Files.isReadable(filePath)) {
            throw new CommandException(String.format(FILE_NOT_READABLE_MESSAGE, filePath));
        }
    }

    /**
     * Creates a template file with valid schedule data.
     *
     * @param errorMessage the error message to throw if template file fails to create
     * @throws CommandException if template file fails to create
     */
    private void createTemplate(String errorMessage) throws CommandException {
        try {
            FileUtil.createParentDirsOfFile(TEMPLATE_FILE_PATH);
            FileUtil.writeToFile(TEMPLATE_FILE_PATH, VALID_DATA_FORMAT_EXAMPLE);
        } catch (IOException ioe) {
            throw new CommandException(errorMessage);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ImportScheduleCommand)) {
            return false;
        }

        // state check
        ImportScheduleCommand e = (ImportScheduleCommand) other;
        return targetIndex.equals(e.targetIndex)
                && filePath.equals(e.filePath);
    }
}
