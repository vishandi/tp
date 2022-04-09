package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.schedule.Event.DATE_MESSAGE_CONSTRAINTS;
import static seedu.address.model.schedule.Event.DURATION_MESSAGE_CONSTRAINTS;
import static seedu.address.model.schedule.Event.TIME_MESSAGE_CONSTRAINTS;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;
import seedu.address.model.person.Telegram;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.schedule.RecurFrequency;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private static final String TIME_REGEX = "\\d{2}:\\d{2}";
    private static final String DURATION_HOURS_REGEX = "^[0-9]*H?";
    private static final String DURATION_HOURS_MINUTES_REGEX = "^[0-9]*H[0-9]*M";
    private static final String DURATION_MINUTES_REGEX = "[0-9]*M";


    private static final String FILE_PATH_MESSAGE_CONSTRAINTS = "File path cannot be empty!";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code oneBasedIndices} into a {@code List<Index>} and returns it. Leading and trailing whitespaces will
     * be trimmed.
     * @throws ParseException if any specified index is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndices (String oneBasedIndices) throws ParseException {
        String[] indices = oneBasedIndices.split("\\s+");
        ArrayList<Index> trimmedIndices = new ArrayList<>();
        for (String index : indices) {
            if (!StringUtil.isNonZeroUnsignedInteger(index.trim())) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
            trimmedIndices.add(Index.fromOneBased(Integer.parseInt(index)));
        }
        return trimmedIndices;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String username} into a {@code Telegram}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code username} is invalid.
     */
    public static Telegram parseTelegram(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Telegram.isValidTelegram(trimmedUsername) || trimmedUsername.equals("")) {
            throw new ParseException(Telegram.MESSAGE_CONSTRAINTS);
        }
        return new Telegram(trimmedUsername);
    }

    /**
     * Parses a {@code String username} into a {@code GitHub}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String username} is invalid.
     */
    public static GitHub parseGithub(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!GitHub.isValidGitHub(trimmedUsername) || trimmedUsername.equals("")) {
            throw new ParseException(GitHub.MESSAGE_CONSTRAINTS);
        }
        return new GitHub(trimmedUsername);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress) || trimmedAddress.equals("")) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail) || trimmedEmail.equals("")) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String eventDescription} into an {@code EventDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventDescription} is invalid.
     */
    public static EventDescription parseEventDescription(String eventDescription) throws ParseException {
        requireNonNull(eventDescription);
        String trimmedEventDescription = eventDescription.trim();
        if (!EventDescription.isValidEventDescription(trimmedEventDescription)) {
            throw new ParseException(EventDescription.MESSAGE_CONSTRAINTS);
        }
        return new EventDescription(trimmedEventDescription);
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!trimmedDate.matches(DATE_REGEX)) {
            throw new ParseException(DATE_MESSAGE_CONSTRAINTS);
        }
        try {
            LocalDate localDate = LocalDate.parse(trimmedDate);
            checkValidDate(localDate);
            return localDate;
        } catch (DateTimeParseException e) {
            throw new ParseException("The provided date does not exist!");
        }
    }

    /**
     * A helper function to check if a date satisfies the year constraints.
     *
     * @throws ParseException if the year of the given {@code date} is less than 2000 or more than 2100
     */
    private static void checkValidDate(LocalDate date) throws ParseException {
        int year = date.getYear();
        if (year < 2000 || year > 2100) {
            throw new ParseException("The date of the event must be between year 2000 to 2100 inclusive!");
        }
    }

    /**
     * Parses a {@code String time} into an {@code LocalTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static LocalTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!trimmedTime.matches(TIME_REGEX)) {
            throw new ParseException(TIME_MESSAGE_CONSTRAINTS);
        }
        try {
            return LocalTime.parse(trimmedTime);
        } catch (DateTimeParseException e) {
            throw new ParseException("The provided time is invalid!");
        }
    }

    /**
     * Parses a {@code String duration} into a {@code Duration}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code duration} is invalid.
     */
    public static Duration parseDuration(String duration) throws ParseException {
        requireNonNull(duration);
        String trimmedDuration = duration.trim().toUpperCase();
        int hours = 0;
        int minutes = 0;
        try {
            if (trimmedDuration.matches(DURATION_HOURS_MINUTES_REGEX)) {
                String[] splitDuration = trimmedDuration.split("H");
                hours = Integer.parseInt(splitDuration[0]);
                minutes = Integer.parseInt(splitDuration[1].split("M")[0]);
                checkValidHoursAndMinutes(hours, minutes);
            } else if (trimmedDuration.matches(DURATION_HOURS_REGEX)) {
                hours = Integer.parseInt(trimmedDuration.split("H")[0]);
                checkValidHours(hours);
            } else if (trimmedDuration.matches(DURATION_MINUTES_REGEX)) {
                minutes = Integer.parseInt(trimmedDuration.split("M")[0]);
                checkValidMinutes(minutes);
            } else {
                throw new ParseException(DURATION_MESSAGE_CONSTRAINTS);
            }
            Duration newDuration = Duration.ofHours(hours).plusMinutes(minutes);
            if (newDuration.isZero()) {
                throw new ParseException("Duration cannot be 0!");
            }
            return newDuration;
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new ParseException(DURATION_MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * A helper function to check if the hours and minutes satisfies duration constraints.
     *
     * @throws ParseException if the {@code hours} and {@code minutes} combined exceeds 336 hours (2 weeks)
     */
    private static void checkValidHoursAndMinutes(int hours, int minutes) throws ParseException {
        checkValidMinutes(minutes);
        checkValidHours(hours);
        if (hours == 336 && minutes > 0) {
            throw new ParseException("The event's duration cannot exceed 2 weeks! (336 hours)");
        }
    }

    /**
     * A helper function to check if the hours satisfy duration constraints.
     *
     * @throws ParseException if the given {@code hours} is less than 0 or more than 336
     */
    private static void checkValidHours(int hours) throws ParseException {
        if (hours > 336) {
            throw new ParseException("The event's duration cannot exceed 2 weeks! (336 hours)");
        } else if (hours < 0) {
            throw new ParseException("Hours cannot be negative!");
        }
    }

    /**
     * A helper function to check if the minutes satisfy minute constraints.
     *
     * @throws ParseException if the given {@code minutes} is less than 0 or larger than 59
     */
    private static void checkValidMinutes(int minutes) throws ParseException {
        if (minutes < 0 || minutes > 59) {
            throw new ParseException("Minutes should be an integer between 0 to 59 inclusive!");
        }
    }

    /**
     * Parses a {@code String recurFrequency} into an {@code RecurFrequency}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code recurFrequency} is invalid.
     */
    public static RecurFrequency parseRecurFrequency(String recurFrequency) throws ParseException {
        requireNonNull(recurFrequency);
        String trimmedRecurFrequency = recurFrequency.trim();
        return RecurFrequency.of(trimmedRecurFrequency.toUpperCase());
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String filePath} into a {@code Path}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code filePath} is invalid.
     */
    public static Path parseFilePath(String filePath) throws ParseException {
        requireNonNull(filePath);
        if (filePath.trim().equals("")) {
            throw new ParseException(FILE_PATH_MESSAGE_CONSTRAINTS);
        }
        return Paths.get(filePath);
    }
}
