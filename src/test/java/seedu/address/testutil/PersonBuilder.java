package seedu.address.testutil;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;
import seedu.address.model.person.Telegram;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.schedule.RecurFrequency;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_TELEGRAM = "Amy_Bee";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Telegram telegram;
    private GitHub github;
    private Email email;
    private Address address;
    private Schedule schedule;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        telegram = Telegram.EMPTY_TELEGRAM;
        github = GitHub.EMPTY_GITHUB;
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        schedule = new Schedule(new ArrayList<>());
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        telegram = personToCopy.getTelegram();
        github = personToCopy.getGithub();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        schedule = new Schedule(personToCopy.getSchedule().getEvents());
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Telegram} of the {@code Person} that we are building.
     */
    public PersonBuilder withTelegram(String telegram) {
        this.telegram = new Telegram(telegram);
        return this;
    }

    /**
     * Sets the {@code GitHub} of the {@code Person} that we are building.
     */
    public PersonBuilder withGithub(String github) {
        this.github = new GitHub(github);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Parses the {@code schedule} into a {@code Schedule} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    /**
     * Adds the {@code Event} into the {@code Schedule} of the {@code Person} that we are building.
     */
    public PersonBuilder withEvent(Event event) {
        ArrayList<Event> newEvents = new ArrayList<>(schedule.getEvents());
        newEvents.add(event);
        Collections.sort(newEvents);
        schedule = new Schedule(newEvents);
        return this;
    }

    /**
     * Parses the arguments to create an {@code Event} and add it to the {@code Schedule}
     * of the {@code Person} that we are building.
     */
    public PersonBuilder withEvent(String description, String date, String time, String duration,
                                   String recurFrequency) {
        EventDescription eventDescription = new EventDescription(description);
        try {
            LocalDate eventDate = ParserUtil.parseDate(date);
            LocalTime eventTime = ParserUtil.parseTime(time);
            Duration eventDuration = ParserUtil.parseDuration(duration);
            RecurFrequency eventRecurFrequency = ParserUtil.parseRecurFrequency(recurFrequency);
            Event event = new Event(eventDescription, eventDate, eventTime, eventDuration, eventRecurFrequency);
            ArrayList<Event> newEvents = new ArrayList<>(this.schedule.getEvents());
            newEvents.add(event);
            Collections.sort(newEvents);
            this.schedule = new Schedule(newEvents);
            return this;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Parses the arguments to create an {@code Event} and add its next recurrence to the {@code Schedule} of the
     * {@code Person} that we are building.
     */
    public PersonBuilder withClosestEvent(String description, String date, String time, String duration,
                                   String recurFrequency) {
        EventDescription eventDescription = new EventDescription(description);
        try {
            LocalDate eventDate = ParserUtil.parseDate(date);
            LocalTime eventTime = ParserUtil.parseTime(time);
            Duration eventDuration = ParserUtil.parseDuration(duration);
            RecurFrequency eventRecurFrequency = ParserUtil.parseRecurFrequency(recurFrequency);
            Event event = new Event(eventDescription, eventDate, eventTime, eventDuration, eventRecurFrequency);
            Event nextRecurringEvent = event.getNextRecurringEvent();
            ArrayList<Event> newEvents = new ArrayList<>(this.schedule.getEvents());
            newEvents.add(nextRecurringEvent);
            Collections.sort(newEvents);
            this.schedule = new Schedule(newEvents);
            return this;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Person build() {
        return new Person(name, phone, telegram, github, email, address, schedule, tags);
    }

}
