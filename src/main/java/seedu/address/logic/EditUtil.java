package seedu.address.logic;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;
import seedu.address.model.person.Telegram;
import seedu.address.model.schedule.EventDescription;
import seedu.address.model.schedule.RecurFrequency;
import seedu.address.model.schedule.Schedule;

public class EditUtil {
    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Telegram telegram;
        private GitHub github;
        private Email email;
        private Address address;
        private Schedule schedule;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setTelegram(toCopy.telegram);
            setGithub(toCopy.github);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setSchedule(toCopy.schedule);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, telegram, github, email, address, schedule, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setTelegram(Telegram telegram) {
            this.telegram = telegram;
        }

        public Optional<Telegram> getTelegram() {
            return Optional.ofNullable(telegram);
        }

        public void setGithub(GitHub github) {
            this.github = github;
        }

        public Optional<GitHub> getGithub() {
            return Optional.ofNullable(github);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setSchedule(Schedule schedule) {
            this.schedule = schedule;
        }

        public Optional<Schedule> getSchedule() {
            return Optional.ofNullable(schedule);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getTelegram().equals(e.getTelegram())
                    && getGithub().equals(e.getGithub())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getSchedule().equals(e.getSchedule())
                    && getTags().equals(e.getTags());
        }
    }

    public static class EditEventDescriptor {
        private EventDescription eventDescription;
        private LocalDate date;
        private LocalTime time;
        private Duration duration;
        private RecurFrequency recurFrequency;

        public EditEventDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setEventDescription(toCopy.eventDescription);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setDuration(toCopy.duration);
            setRecurFrequency(toCopy.recurFrequency);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(eventDescription, date, time, duration, recurFrequency);
        }

        public void setEventDescription(EventDescription eventDescription) {
            this.eventDescription = eventDescription;
        }

        public Optional<EventDescription> getEventDescription() {
            return Optional.ofNullable(eventDescription);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setTime(LocalTime time) {
            this.time = time;
        }

        public Optional<LocalTime> getTime() {
            return Optional.ofNullable(time);
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public Optional<Duration> getDuration() {
            return Optional.ofNullable(duration);
        }

        public void setRecurFrequency(RecurFrequency recurFrequency) {
            this.recurFrequency = recurFrequency;
        }

        public Optional<RecurFrequency> getRecurFrequency() {
            return Optional.ofNullable(recurFrequency);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getEventDescription().equals(e.getEventDescription())
                    && getDate().equals(e.getDate())
                    && getTime().equals(e.getTime())
                    && getDuration().equals(e.getDuration())
                    && getRecurFrequency().equals(e.getRecurFrequency());
        }
    }
}
