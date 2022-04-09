package seedu.address.logic.commands.schedule;


import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.IsTagInPersonPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.Schedule;


/**
 * Command that lists all contacts who share the same tag.
 */
public class FindCommonTimingCommand extends Command {

    public static final String COMMAND_WORD = "findCommonTiming";
    public static final String COMMAND_WORD_LOWER = "findcommontiming";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Retrieves common free timings of contacts "
            + "who share the same tag\n"
            + "Parameters: "
            + PREFIX_TAG + "TAG "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "friends "
            + PREFIX_DATE + "2022-02-14";
    public static final String TAG_NOT_PRESENT_ERROR_MESSAGE =
            "Please ensure there is at least one contact with this tag!";
    private final IsTagInPersonPredicate predicate;
    private final LocalDate date;

    /**
     * Constructor for findCommonTiming object.
     * @param predicate predicate indicating whether tag is present in list of people
     * @param date date of findCommonTiming
     */
    public FindCommonTimingCommand(IsTagInPersonPredicate predicate, LocalDate date) {
        this.predicate = predicate;
        this.date = date;
    }

    /**
     * Helper function that blocks time slots according to an event that a person has(if it falls on the desired day).
     * @param timeSlots array representing 30-minute blocks
     * @param event event that needs to be accounted for
     */
    public void blockTimeSlots(int[] timeSlots, Event event) {
        List<Event> listOfSameDayEvents = event.getEventsAtDate(date);
        for (Event eventAtCurrentDate : listOfSameDayEvents) {
            System.out.println(eventAtCurrentDate);
            LocalTime startTime = eventAtCurrentDate.getTime();

            int hours = startTime.getHour();
            int minutes = startTime.getMinute();
            int startSlot = hours * 2;
            if (minutes >= 30) {
                startSlot += 1;
            }

            LocalTime endTime = eventAtCurrentDate.getEndTime();

            int endHours = endTime.getHour();
            int endMinutes = endTime.getMinute();
            int endSlot = endHours * 2;
            if (endMinutes > 30) {
                endSlot += 2;
            } else if (endMinutes > 0) {
                endSlot += 1;
            }

            if (endTime.equals(LocalTime.of(0, 0))) {
                endSlot = 48;
            }

            for (int j = startSlot; j < endSlot; j++) {
                //set as busy
                timeSlots[j] = 1;
            }
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        int[] timeSlots = new int[48];
        model.updateFilteredPersonList(predicate);
        List<Person> listOfPersons = model.getFilteredPersonList();
        for (Person person : listOfPersons) {
            Schedule scheduleToCheck = person.getSchedule();
            for (Event event : scheduleToCheck.getEvents()) {
                if (event.willDateCollide(date)) {
                    blockTimeSlots(timeSlots, event);
                }
            }
        }

        int toggle = timeSlots[0];
        LocalTime startTime = LocalTime.of(0, 0);
        Duration duration = Duration.ofMinutes(0);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < timeSlots.length; i++) {
            // Check if current slot is free
            if (timeSlots[i] == toggle) {
                duration = duration.plusMinutes(30);
            } else {
                if (toggle == 0) { // startTime - endTime(startTime + duration)
                    toggle = 1;
                    sb.append(startTime);
                    startTime = startTime.plus(duration);
                    sb.append(String.format("-%s\n", startTime));
                } else {
                    toggle = 0;
                    startTime = startTime.plus(duration);
                }
                duration = duration.ofMinutes(30);
            }
            if (i == timeSlots.length - 1 && toggle == 0) {
                sb.append(startTime);
                sb.append(String.format("-%s\n", LocalTime.of(23, 59)));
            }
        }
        Set<Integer> distinct = Arrays.stream(timeSlots).boxed().collect(Collectors.toSet());
        boolean allEqual = distinct.size() == 1;
        if (timeSlots[0] == 0 && allEqual) {
            sb.replace(0, 25, "The whole day is free for these contacts!");
        } else if (allEqual) {
            sb.replace(0, 25, "There are no free timings available!");
        }
        if (listOfPersons.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(TAG_NOT_PRESENT_ERROR_MESSAGE);
        }
        return new CommandResult(sb.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommonTimingCommand // instanceof handles nulls
                && predicate.equals(((FindCommonTimingCommand) other).predicate)
                && date.equals(((FindCommonTimingCommand) other).date)); // state check
    }
}
