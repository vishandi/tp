package seedu.address.logic.commands.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.IsTagInPersonPredicate;
import seedu.address.model.person.Tag;


/**
 * Contains integration tests (interaction with the Model) for {@code ViewGroupCommand}.
 */
class ViewGroupCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Tag testTag = new Tag("friends");
        IsTagInPersonPredicate testPredicate = new IsTagInPersonPredicate(testTag);
        Tag secondTestTag = new Tag("teammates");
        IsTagInPersonPredicate secondTestPredicate = new IsTagInPersonPredicate(secondTestTag);
        boolean arePredicatesEqual = testPredicate.equals(secondTestPredicate);
        assertFalse(arePredicatesEqual);
    }

    @Test
    public void expectedResultOccurs() {
        IsTagInPersonPredicate testPredicate = new IsTagInPersonPredicate(new Tag("friends"));
        expectedModel.updateFilteredPersonList(testPredicate);
        String expectedMessage =
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        ViewGroupCommand testCommand = new ViewGroupCommand(testPredicate);

        assertCommandSuccess(testCommand, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }
}
