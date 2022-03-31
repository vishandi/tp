package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

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

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String TOO_LONG_NAME = "O".repeat(31);
    private static final String INVALID_PHONE = "+651234";
    private static final String TOO_LONG_PHONE = "0".repeat(16);
    private static final String INVALID_TELEGRAM = "abcde**";
    private static final String TOO_LONG_TELEGRAM = "O".repeat(41);
    private static final String INVALID_GITHUB = "ab--e";
    private static final String TOO_LONG_GITHUB = "O".repeat(40);
    private static final String INVALID_EMAIL = "example.com";
    private static final String TOO_LONG_EMAIL = "a".repeat(49) + "@example.com";
    private static final String INVALID_ADDRESS = "[]/\\";
    private static final String TOO_LONG_ADDRESS = "O".repeat(81);
    private static final String INVALID_TAG = "#friend";
    private static final String TOO_LONG_TAG = "O".repeat(31);
    private static final String INVALID_EVENT_DESCRIPTION = "abcde/fghij";
    private static final String TOO_LONG_EVENT_DESCRIPTION = "O".repeat(61);
    private static final String INVALID_DATE_YEAR_1999 = "1999-12-31";
    private static final String INVALID_DATE_YEAR_2101 = "2101-01-01";
    private static final String INVALID_DATE_NEGATIVE = "-2101-01-01";
    private static final String INVALID_DATE_FORMAT = "2022-5-11";
    private static final String INVALID_TIME = "24:00";
    private static final String INVALID_TIME_HAS_SECONDS = "23:00:03";
    private static final String INVALID_TIME_FORMAT = "001:00";
    private static final String INVALID_DURATION_NEGATIVE = "-3H";
    private static final String INVALID_DURATION_FORMAT = "3HH";
    private static final String INVALID_DURATION_EXCEEDS_2_WEEKS = "336H1M";
    private static final String INVALID_DURATION_NON_INTEGER = "3H3.5M";
    private static final String INVALID_DURATION_MINUTES_60 = "2H60M";
    private static final String INVALID_RECUR_FREQUENCY = "B";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String MAXIMUM_LENGTH_NAME = "O".repeat(30);
    private static final String VALID_PHONE = "123456";
    private static final String MAXIMUM_LENGTH_PHONE = "0".repeat(15);
    private static final String VALID_TELEGRAM = "abcde";
    private static final String MAXIMUM_LENGTH_TELEGRAM = "O".repeat(40);
    private static final String VALID_GITHUB = "abcde";
    private static final String MAXIMUM_LENGTH_GITHUB = "O".repeat(39);
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String MAXIMUM_LENGTH_EMAIL = "a".repeat(48) + "@example.com";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String MAXIMUM_LENGTH_ADDRESS = "O".repeat(80);
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String MAXIMUM_LENGTH_TAG = "O".repeat(30);
    private static final String VALID_EVENT_DESCRIPTION = "abcde";
    private static final String MAXIMUM_LENGTH_EVENT_DESCRIPTION = "O".repeat(60);
    private static final String VALID_DATE = "2022-03-30";
    private static final String VALID_TIME = "13:00";
    private static final String VALID_DURATION_HOURS_MINUTES = "3H20M";
    private static final String VALID_DURATION_MINUTES = "30M";
    private static final String VALID_DURATION_HOURS = "3H";
    private static final String VALID_DURATION_INTEGER = "3";
    private static final String VALID_RECUR_FREQUENCY = "BiweekLy";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
        assertThrows(ParseException.class, () -> ParserUtil.parseName(TOO_LONG_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));

        Name maximumLengthName = new Name(MAXIMUM_LENGTH_NAME);
        assertEquals(maximumLengthName, ParserUtil.parseName(MAXIMUM_LENGTH_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));

        String maximumLengthNameWithWhitespace = WHITESPACE + MAXIMUM_LENGTH_NAME + WHITESPACE;
        Name maximumLengthName = new Name(MAXIMUM_LENGTH_NAME);
        assertEquals(maximumLengthName, ParserUtil.parseName(maximumLengthNameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(TOO_LONG_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));

        Phone maximumLengthPhone = new Phone(MAXIMUM_LENGTH_PHONE);
        assertEquals(maximumLengthPhone, ParserUtil.parsePhone(MAXIMUM_LENGTH_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));

        String maximumLengthPhoneWithWhitespace = WHITESPACE + MAXIMUM_LENGTH_PHONE + WHITESPACE;
        Phone maximumLengthPhone = new Phone(MAXIMUM_LENGTH_PHONE);
        assertEquals(maximumLengthPhone, ParserUtil.parsePhone(maximumLengthPhoneWithWhitespace));
    }

    @Test
    public void parseTelegram_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTelegram((String) null));
    }

    @Test
    public void parseTelegram_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTelegram(INVALID_TELEGRAM));
        assertThrows(ParseException.class, () -> ParserUtil.parseTelegram(TOO_LONG_TELEGRAM));
    }

    @Test
    public void parseTelegram_validValueWithoutWhitespace_returnsTelegram() throws Exception {
        Telegram expectedTelegram = new Telegram(VALID_TELEGRAM);
        assertEquals(expectedTelegram, ParserUtil.parseTelegram(VALID_TELEGRAM));

        Telegram maximumLengthTelegram = new Telegram(MAXIMUM_LENGTH_TELEGRAM);
        assertEquals(maximumLengthTelegram, ParserUtil.parseTelegram(MAXIMUM_LENGTH_TELEGRAM));
    }

    @Test
    public void parseTelegram_validValueWithWhitespace_returnsTelegram() throws Exception {
        String telegramWithWhitespace = WHITESPACE + VALID_TELEGRAM + WHITESPACE;
        Telegram expectedTelegram = new Telegram(VALID_TELEGRAM);
        assertEquals(expectedTelegram, ParserUtil.parseTelegram(telegramWithWhitespace));

        String maximumLengthTelegramWithWhitespace = WHITESPACE + MAXIMUM_LENGTH_TELEGRAM + WHITESPACE;
        Telegram maximumLengthTelegram = new Telegram(MAXIMUM_LENGTH_TELEGRAM);
        assertEquals(maximumLengthTelegram, ParserUtil.parseTelegram(maximumLengthTelegramWithWhitespace));
    }

    @Test
    public void parseGitHub_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGithub((String) null));
    }

    @Test
    public void parseGitHub_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGithub(INVALID_GITHUB));
        assertThrows(ParseException.class, () -> ParserUtil.parseGithub(TOO_LONG_GITHUB));
    }

    @Test
    public void parseGitHub_validValueWithoutWhitespace_returnsGitHub() throws Exception {
        GitHub expectedGithub = new GitHub(VALID_GITHUB);
        assertEquals(expectedGithub, ParserUtil.parseGithub(VALID_GITHUB));

        GitHub maximumLengthGithub = new GitHub(MAXIMUM_LENGTH_GITHUB);
        assertEquals(maximumLengthGithub, ParserUtil.parseGithub(MAXIMUM_LENGTH_GITHUB));
    }

    @Test
    public void parseGitHub_validValueWithWhitespace_returnsGitHub() throws Exception {
        String githubWithWhitespace = WHITESPACE + VALID_GITHUB + WHITESPACE;
        GitHub expectedGithub = new GitHub(VALID_GITHUB);
        assertEquals(expectedGithub, ParserUtil.parseGithub(githubWithWhitespace));

        String maximumLengthGithubWithWhitespace = WHITESPACE + MAXIMUM_LENGTH_GITHUB + WHITESPACE;
        GitHub maximumLengthGithub = new GitHub(MAXIMUM_LENGTH_GITHUB);
        assertEquals(maximumLengthGithub, ParserUtil.parseGithub(maximumLengthGithubWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(TOO_LONG_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));

        Address maximumLengthAddress = new Address(MAXIMUM_LENGTH_ADDRESS);
        assertEquals(maximumLengthAddress, ParserUtil.parseAddress(MAXIMUM_LENGTH_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));

        String maximumLengthAddressWithWhitespace = WHITESPACE + MAXIMUM_LENGTH_ADDRESS + WHITESPACE;
        Address maximumLengthAddress = new Address(MAXIMUM_LENGTH_ADDRESS);
        assertEquals(maximumLengthAddress, ParserUtil.parseAddress(maximumLengthAddressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(TOO_LONG_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));

        Email maximumLengthEmail = new Email(MAXIMUM_LENGTH_EMAIL);
        assertEquals(maximumLengthEmail, ParserUtil.parseEmail(MAXIMUM_LENGTH_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));

        String maximumLengthEmailWithWhitespace = WHITESPACE + MAXIMUM_LENGTH_EMAIL + WHITESPACE;
        Email maximumLengthEmail = new Email(MAXIMUM_LENGTH_EMAIL);
        assertEquals(maximumLengthEmail, ParserUtil.parseEmail(maximumLengthEmailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(TOO_LONG_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));

        Tag maximumLengthTag = new Tag(MAXIMUM_LENGTH_TAG);
        assertEquals(maximumLengthTag, ParserUtil.parseTag(MAXIMUM_LENGTH_TAG));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));

        String maximumLengthTagWithWhitespace = WHITESPACE + MAXIMUM_LENGTH_TAG + WHITESPACE;
        Tag maximumLengthTag = new Tag(MAXIMUM_LENGTH_TAG);
        assertEquals(maximumLengthTag, ParserUtil.parseTag(maximumLengthTagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, TOO_LONG_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseEventDescription_null_throwsParseException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEventDescription((String) null));
    }

    @Test
    public void parseEventDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEventDescription(INVALID_EVENT_DESCRIPTION));
        assertThrows(ParseException.class, () -> ParserUtil.parseEventDescription(TOO_LONG_EVENT_DESCRIPTION));
    }

    @Test
    public void parseEventDescription_validValueWithoutWhitespace_returnsEmail() throws Exception {
        EventDescription expectedEventDescription = new EventDescription(VALID_EVENT_DESCRIPTION);
        assertEquals(expectedEventDescription, ParserUtil.parseEventDescription(VALID_EVENT_DESCRIPTION));

        EventDescription maximumLengthEventDescription = new EventDescription(MAXIMUM_LENGTH_EVENT_DESCRIPTION);
        assertEquals(maximumLengthEventDescription, ParserUtil.parseEventDescription(MAXIMUM_LENGTH_EVENT_DESCRIPTION));
    }

    @Test
    public void parseEventDescription_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String eventDescriptionWithWhitespace = WHITESPACE + VALID_EVENT_DESCRIPTION + WHITESPACE;
        EventDescription expectedEventDescription = new EventDescription(VALID_EVENT_DESCRIPTION);
        assertEquals(expectedEventDescription, ParserUtil.parseEventDescription(eventDescriptionWithWhitespace));

        String maximumLengthEventDescriptionWithWhitespace = WHITESPACE + MAXIMUM_LENGTH_EVENT_DESCRIPTION + WHITESPACE;
        EventDescription maximumLengthEventDescription = new EventDescription(MAXIMUM_LENGTH_EVENT_DESCRIPTION);
        assertEquals(maximumLengthEventDescription,
                ParserUtil.parseEventDescription(maximumLengthEventDescriptionWithWhitespace));
    }

    @Test
    public void parseDate_invalidValues_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE_FORMAT));
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE_NEGATIVE));
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE_YEAR_1999));
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE_YEAR_2101));
    }

    @Test
    public void parseDate_nullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
    }

    @Test
    public void parseDate_validValue_returnsDate() throws Exception {
        LocalDate expectedDate = LocalDate.parse(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseTime_invalidValues_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_TIME));
        assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_TIME_FORMAT));
        assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_TIME_HAS_SECONDS));
    }

    @Test
    public void parseTime_nullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTime(null));
    }

    @Test
    public void parseTime_validValue_returnsTime() throws Exception {
        LocalTime expectedTime = LocalTime.parse(VALID_TIME);
        assertEquals(expectedTime, ParserUtil.parseTime(VALID_TIME));
    }

    @Test
    public void parseDuration_invalidValues_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDuration(INVALID_DURATION_MINUTES_60));
        assertThrows(ParseException.class, () -> ParserUtil.parseDuration(INVALID_DURATION_FORMAT));
        assertThrows(ParseException.class, () -> ParserUtil.parseDuration(INVALID_DURATION_NEGATIVE));
        assertThrows(ParseException.class, () -> ParserUtil.parseDuration(INVALID_DURATION_NON_INTEGER));
        assertThrows(ParseException.class, () -> ParserUtil.parseDuration(INVALID_DURATION_EXCEEDS_2_WEEKS));
    }

    @Test
    public void parseDuration_nullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDuration(null));
    }

    @Test
    public void parseDuration_validValue_returnsDuration() throws Exception {
        Duration expectedDurationHours = Duration.parse("PT" + VALID_DURATION_HOURS);
        assertEquals(expectedDurationHours, ParserUtil.parseDuration(VALID_DURATION_HOURS));

        Duration expectedDurationMinutes = Duration.parse("PT" + VALID_DURATION_MINUTES);
        assertEquals(expectedDurationMinutes, ParserUtil.parseDuration(VALID_DURATION_MINUTES));

        Duration expectedDurationHoursMinutes = Duration.parse("PT" + VALID_DURATION_HOURS_MINUTES);
        assertEquals(expectedDurationHoursMinutes, ParserUtil.parseDuration(VALID_DURATION_HOURS_MINUTES));

        Duration expectedDurationInteger = Duration.parse("PT" + VALID_DURATION_INTEGER + "H");
        assertEquals(expectedDurationInteger, ParserUtil.parseDuration(VALID_DURATION_INTEGER));
    }

    @Test
    public void parseRecurFrequency_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRecurFrequency(INVALID_RECUR_FREQUENCY));
    }

    @Test
    public void parseRecurFrequency_null_throwsParseException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRecurFrequency(null));
    }

    @Test
    public void parseRecurFrequency_validValue_returnsRecurFrequency() throws Exception {
        RecurFrequency expectedRecurFrequency = RecurFrequency.BIWEEKLY;
        assertEquals(expectedRecurFrequency, ParserUtil.parseRecurFrequency(VALID_RECUR_FREQUENCY));
    }
}
