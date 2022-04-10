package seedu.address.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.IsTagInPersonPredicate;
import seedu.address.model.person.Tag;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommonTimingCommand}.
 */
class FindCommonTimingCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        LocalDate testDate = LocalDate.parse("2022-12-28");
        LocalDate secondTestDate = LocalDate.parse("2022-12-30");
        Tag testTag = new Tag("friends");
        Tag secondTestTag = new Tag("mates");

        IsTagInPersonPredicate testPredicate = new IsTagInPersonPredicate(testTag);
        IsTagInPersonPredicate secondTestPredicate = new IsTagInPersonPredicate(secondTestTag);

        FindCommonTimingCommand testCommand = new FindCommonTimingCommand(testPredicate, testDate);
        FindCommonTimingCommand secondTestCommand = new FindCommonTimingCommand(secondTestPredicate, secondTestDate);

        // same object -> returns true
        assertEquals(testCommand, testCommand);

        // same values -> returns true
        FindCommonTimingCommand testCommandCopy = new FindCommonTimingCommand(testPredicate, testDate);
        assertEquals(testCommand, testCommandCopy);

        // null -> returns false
        assertNotEquals(null, testCommand);

        // different values -> returns false
        assertNotEquals(testCommand, secondTestCommand);
    }

    @Test
    public void tagFilterWorks() {
        IsTagInPersonPredicate testPredicate =
                new IsTagInPersonPredicate(new Tag("friends"));
        expectedModel.updateFilteredPersonList(testPredicate);
        model.updateFilteredPersonList(testPredicate);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }
}
