package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECUR_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.Duration;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditUtil.EditEventDescriptor;
import seedu.address.logic.EditUtil.EditPersonDescriptor;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.commands.schedule.AddEventCommand;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.schedule.Event;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    public static String getAddEventCommand(Index index, Event event) {
        return AddEventCommand.COMMAND_WORD + " " + index.getOneBased() + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().value + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.value).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event) {
        Duration duration = event.getDuration();
        String hours = String.valueOf(duration.toHours());
        String minutes = String.valueOf(duration.toMinutes() % 60);
        String durationString = String.format("%1$sH%2$sM", hours, minutes);

        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_EVENT_DESCRIPTION + event.getEventDescription().value + " ");
        sb.append(PREFIX_DATE + event.getDate().toString() + " ");
        sb.append(PREFIX_TIME + event.getTime().toString() + " ");
        sb.append(PREFIX_DURATION + durationString + " ");
        sb.append(PREFIX_RECUR_FREQUENCY + event.getRecurFrequency().getShortName() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditEventDescriptor}'s details.
     */
    public static String getEditEventDescriptorDetails(EditEventDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getEventDescription().ifPresent(ed -> sb.append(PREFIX_EVENT_DESCRIPTION)
                .append(ed.value).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date).append(" "));
        descriptor.getTime().ifPresent(time -> sb.append(PREFIX_TIME).append(time).append(" "));
        if (descriptor.getDuration().isPresent()) {
            Duration duration = descriptor.getDuration().get();
            String hours = String.valueOf(duration.toHours());
            String minutes = String.valueOf(duration.toMinutes() % 60);
            String durationString = String.format("%1$sH%2$sM", hours, minutes);
            sb.append(PREFIX_DURATION).append(durationString).append(" ");
        }
        descriptor.getRecurFrequency().ifPresent(rf -> sb.append(PREFIX_RECUR_FREQUENCY)
                .append(rf.getShortName()).append(" "));
        return sb.toString();
    }
}
