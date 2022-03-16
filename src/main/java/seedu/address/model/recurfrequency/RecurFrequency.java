package seedu.address.model.recurfrequency;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.parser.exceptions.ParseException;

public enum RecurFrequency {
    WEEKLY("W", "WEEKLY"), BIWEEKLY("BW", "BIWEEKLY"),
    DAILY("D", "DAILY"), MONTHLY("M", "MONTHLY"),
    NONE("NONE", "NONE");

    private static final String DAILY_LABEL = "(Daily)";
    private static final String WEEKLY_LABEL = "(Weekly)";
    private static final String BIWEEKLY_LABEL = "(Biweekly)";
    private static final String MONTHLY_LABEL = "(Monthly)";
    private static final String INVALID_LABEL = "(Invalid)";
    private static final String NO_LABEL = "";
    private static Set<RecurFrequency> validFrequencies = Arrays.stream(RecurFrequency.values()).collect(Collectors.toSet());
    private final String shortName;
    private final String fullName;

    public static final String DEFAULT_RECURRENCE = NONE.fullName;
    public static final String INVALID_RECUR_FREQUENCY_MESSAGE = String.format("Recurrence "
            + "frequency must be one of the following: %s", validFrequencies);

    RecurFrequency(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    /**
     * Gets the RecursiveFrequency corresponding to the given String frequency, if it exists. Otherwise, throws ParseException.
     *
     * @param frequency The String format of the frequency
     * @return The RecursiveFrequency corresponding to the given String frequency, if it exists.
     * Otherwise, throws ParseException.
     */
    public static RecurFrequency of(String frequency) throws ParseException {
        Optional<RecurFrequency> recurFrequency = validFrequencies.stream()
                .filter(r -> r.shortName.equals(frequency) ||
                        r.fullName.equals(frequency)).findFirst();
        if (!recurFrequency.isPresent()) {
            throw new ParseException(INVALID_RECUR_FREQUENCY_MESSAGE);
        }
        return recurFrequency.get();
    }

    public static boolean isValidRecurFrequency(String frequency) {
        return validFrequencies.stream()
                .anyMatch(recurFrequency -> recurFrequency.shortName.equals(frequency) ||
                        recurFrequency.fullName.equals(frequency));
    }

    /**
     * Returns the string label of the RecurFrequency.
     *
     * @return the string label of the RecurFrequency
     */
    public String getLabel() {
        switch (this) {
        case NONE:
            return NO_LABEL;
        case DAILY:
            return DAILY_LABEL;
        case WEEKLY:
            return WEEKLY_LABEL;
        case BIWEEKLY:
            return BIWEEKLY_LABEL;
        case MONTHLY:
            return MONTHLY_LABEL;
        default:
            //Should not happen
            return INVALID_LABEL;
        }
    }
}
