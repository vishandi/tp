package seedu.address.model.recurfrequency;

import java.util.Arrays;
import java.util.HashMap;

import seedu.address.logic.parser.exceptions.ParseException;

public enum RecurFrequency {
    WEEKLY("W"), BIWEEKLY("BW"), DAILY("D"), MONTHLY("M");

    public static final String DAILY_LABEL = "(Daily)";
    public static final String WEEKLY_LABEL = "(Weekly)";
    public static final String BIWEEKLY_LABEL = "(Biweekly)";
    public static final String MONTHLY_LABEL = "(Monthly)";
    public static final String UNKNOWN_LABEL = "??";
    private static HashMap<String, RecurFrequency> frequencyMap = new HashMap<>();

    static {
        Arrays.stream(RecurFrequency.values()).forEach(option -> frequencyMap.put(option.frequency, option));
    }
    public static final String INVALID_RECUR_FREQUENCY_MESSAGE = String.format("Recurrence "
            + "frequency must be one of the following: %s", frequencyMap.keySet());
    private String frequency;

    RecurFrequency(String frequency) {
        this.frequency = frequency;
    }

    /**
     * Gets the RecursiveFrequency corresponding to the given String frequency, if it exists. Otherwise, returns null.
     *
     * @param frequency The String format of the frequency
     * @return The RecursiveFrequency corresponding to the given String frequency, if it exists.
     * Otherwise, throws ParseException.
     */
    public static RecurFrequency of(String frequency) throws ParseException {
        if (frequencyMap.containsKey(frequency)) {
            return frequencyMap.get(frequency);
        } else {
            throw new ParseException(INVALID_RECUR_FREQUENCY_MESSAGE);
        }
    }

    /**
     * Returns the string label of the RecurFrequency.
     *
     * @return the string label of the RecurFrequency
     */
    @Override
    public String toString() {
        switch (this) {
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
            return UNKNOWN_LABEL;
        }
    }
}
