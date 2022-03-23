package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;
import seedu.address.model.person.Telegram;
import seedu.address.model.schedule.Schedule;


/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String telegram;
    private final String github;
    private final String email;
    private final String address;
    private final JsonAdaptedSchedule schedule;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("telegram") String telegram, @JsonProperty("github") String github,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("schedule") JsonAdaptedSchedule schedule,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.telegram = telegram;
        this.github = github;
        this.email = email;
        this.address = address;
        this.schedule = schedule;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Constructs a {@code JsonAdaptedPerson} using the attributes of the given {@code Person} for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().value;
        phone = source.getPhone().value;
        telegram = source.getTelegram().value;
        github = source.getGithub().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        schedule = new JsonAdaptedSchedule(source.getSchedule().getEvents()
                .stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (telegram == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Telegram.class.getSimpleName()));
        }
        if (!Telegram.isValidTelegram(telegram)) {
            throw new IllegalValueException(Telegram.MESSAGE_CONSTRAINTS);
        }
        final Telegram modelTelegram = new Telegram(telegram);

        if (github == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    GitHub.class.getSimpleName()));
        }
        if (!GitHub.isValidGitHub(github)) {
            throw new IllegalValueException(GitHub.MESSAGE_CONSTRAINTS);
        }
        final GitHub modelGithub = new GitHub(github);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (schedule == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Schedule.class.getSimpleName()));
        }
        final Schedule modelSchedule = schedule.toModelType();

        final Set<Tag> modelTags = new HashSet<>(personTags);

        return new Person(modelName, modelPhone, modelTelegram,
                modelGithub, modelEmail, modelAddress, modelSchedule, modelTags);
    }

}
