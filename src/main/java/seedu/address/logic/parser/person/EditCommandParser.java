package seedu.address.logic.parser.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditUtil.EditPersonDescriptor;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Tag;
import seedu.address.model.person.Telegram;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_TELEGRAM, PREFIX_GITHUB, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            editPersonDescriptor.setTelegram(parseTelegramForEdit(argMultimap.getValue(PREFIX_TELEGRAM).get()));
        }
        if (argMultimap.getValue(PREFIX_GITHUB).isPresent()) {
            editPersonDescriptor.setGithub(parseGithubForEdit(argMultimap.getValue(PREFIX_GITHUB).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(parseEmailForEdit(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(parseAddressForEdit(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses {@code String username} into a {@code Telegram} if {@code username} is a valid username
     * or an empty username. If it is an empty username, it will be parsed into {@code Telegram.EMPTY_TELEGRAM}.
     */
    private Telegram parseTelegramForEdit(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Telegram.isValidTelegram(trimmedUsername)) {
            throw new ParseException(Telegram.MESSAGE_CONSTRAINTS);
        }
        if (trimmedUsername.equals("")) {
            return Telegram.EMPTY_TELEGRAM;
        }
        return new Telegram(trimmedUsername);
    }

    /**
     * Parses {@code String username} into a {@code GitHub} if {@code username} is a valid username
     * or an empty username. If it is an empty username, it will be parsed into {@code GitHub.EMPTY_GITHUB}.
     */
    private GitHub parseGithubForEdit(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!GitHub.isValidGitHub(trimmedUsername)) {
            throw new ParseException(GitHub.MESSAGE_CONSTRAINTS);
        }
        if (trimmedUsername.equals("")) {
            return GitHub.EMPTY_GITHUB;
        }
        return new GitHub(trimmedUsername);
    }

    /**
     * Parses {@code String email} into a {@code Email} if {@code username} is a valid email
     * or an empty email. If it is an empty email, it will be parsed into {@code Email.EMPTY_EMAIL}.
     */
    private Email parseEmailForEdit(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        if (trimmedEmail.equals("")) {
            return Email.EMPTY_EMAIL;
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses {@code String address} into a {@code Address} if {@code address} is a valid address
     * or an empty address. If it is an empty address, it will be parsed into {@code Address.EMPTY_ADDRESS}.
     */
    private Address parseAddressForEdit(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        if (trimmedAddress.equals("")) {
            return Address.EMPTY_ADDRESS;
        }
        return new Address(trimmedAddress);
    }

}
