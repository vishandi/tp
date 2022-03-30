package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Format full help instructions for every command for display.
 */
public class SetUserCommand extends Command {

    public static final String COMMAND_WORD = "setUser";
    public static final String COMMAND_WORD_LOWER = "setuser";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the specified person in the contact list as the user and moves the contact to the top.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SET_USER_SUCCESS = "%s set as the user!";

    private final Index targetIndex;

    public SetUserCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person user = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(user);
        model.insertPerson(user, 0);
        return new CommandResult(String.format(MESSAGE_SET_USER_SUCCESS, user.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetUserCommand // instanceof handles nulls
                && targetIndex.equals(((SetUserCommand) other).targetIndex)); // state check
    }

}
