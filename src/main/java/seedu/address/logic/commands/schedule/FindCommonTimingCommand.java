package seedu.address.logic.commands.schedule;


import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    public static final String COMMAND_WORD_LOWER = "findcommontiming";
    public static final String MESSAGE_USAGE = COMMAND_WORD_LOWER
            + ": Retrieves common free timings of contacts "
            + "who share the same tag\n"
            + "Parameters: "
            + PREFIX_TAG + "TAG"
            + " [" + PREFIX_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD_LOWER
            + PREFIX_TAG + "friends "
            + PREFIX_DATE + "2022-02-14";

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
     * Helper function that blocks time slots according to an event that a person has.
     * @param timeSlots array representing 30-minute blocks
     * @param event event that needs to be accounted for
     */
    public void blockTimeSlots(int[] timeSlots, Event event) {
        LocalTime startTime = event.getTime();
        int hours = startTime.getHour();
        int minutes = startTime.getMinute();
        int startSlot = hours * 2;
        if (minutes >= 30) {
            startSlot += 1;
        }

        LocalTime endTime = event.getEndTime();
        int endHours = endTime.getHour();
        int endMinutes = endTime.getMinute();
        int endSlot = endHours * 2;
        if (endMinutes > 30) {
            endSlot += 1;
        }

        for (int i = startSlot; i <= endSlot; i++) {
            //set as busy
            timeSlots[i] = 1;
        }
    }

    /**
     * Helper method to convert integer to time
     * @param n integer that represent timeslot that is blocked
     * @return time to be outputted
     */
    public LocalTime convertIntToTime(int n) {
        return n % 2 == 0
                ? LocalTime.of(n / 2, 0)
                : LocalTime.of(n / 2, 30);
    }

    /**
     * Helper method to convert array to strings that represent common timings shared.
     * @param list of LocalTime objects to be outputted
     * @return a string representing common free timings
     */
    public String convertLocalTimeArrayToString(List<LocalTime> list) {
        String formattedTime = "%s - %s\n";
        String freeTimeSlots = "";
        for (int i = 0; i < list.size(); i = i + 2) {
            freeTimeSlots += String.format(formattedTime, list.get(i), list.get(i + 1));
        }
        return freeTimeSlots;
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

        // This list will contain an even number of LocalTime objects
        // The even indexes (i.e. 0, 2, 4, ...) will be starting times
        // The odd indexes will be ending times
        List<LocalTime> listOfAvailableTime = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < timeSlots.length; i++) {
            // Check if current slot is free
            if (timeSlots[i] == 0) {

                if (i != 0 && timeSlots[i - 1] == 0) {
                    // Shor-circuits if the current timeslot is the first timeslot
                    // If the previous timeslot was also free, i.e. the current timeslot is part of an ongoing block
                    // Get the current LocalTime and add 30 minutes to it
                    LocalTime currentTime = listOfAvailableTime.get(counter).plus(Duration.ofMinutes(30));
                    listOfAvailableTime.set(counter, currentTime);
                    if (i != timeSlots.length - 1 && timeSlots[i + 1] == 1) {
                        // Short-circuits if the current slot is the last slot
                        // If the next timeslot is NOT free, i.e. the block stops at the current timeslot
                        // We will move onto the next set of LocalTime
                        LocalTime newTime = listOfAvailableTime.get(counter).plus(Duration.ofMinutes(30));
                        listOfAvailableTime.set(counter, newTime);
                        counter++;
                    }
                    if (i == timeSlots.length - 1) {
                        LocalTime newTime = listOfAvailableTime.get(counter).plus(Duration.ofMinutes(30));
                        listOfAvailableTime.set(counter, newTime);
                    }
                } else {
                    // If the previous timeslot was not free, i.e. it is the start of a new block
                    // OR if the current timeslot is the first timeslot
                    // Add a new LocalTime into the list
                    if (i == 0) {
                        listOfAvailableTime.add(counter, convertIntToTime(i));
                    } else {
                        listOfAvailableTime.add(counter, convertIntToTime(i - 1));
                    }
                    counter++;
                    listOfAvailableTime.add(counter, convertIntToTime(i));
                }
            }
        }
        return new CommandResult(convertLocalTimeArrayToString(listOfAvailableTime));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommonTimingCommand // instanceof handles nulls
                && predicate.equals(((FindCommonTimingCommand) other).predicate)
                && date.equals(((FindCommonTimingCommand) other).date)); // state check
    }
}
