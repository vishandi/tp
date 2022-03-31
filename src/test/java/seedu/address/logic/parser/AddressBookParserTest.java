package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalSchedule.TYPICAL_DATE;
import static seedu.address.testutil.TypicalSchedule.TYPICAL_PATH;
import static seedu.address.testutil.TypicalSchedule.TYPICAL_TIME;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.EditUtil.EditEventDescriptor;
import seedu.address.logic.EditUtil.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.commands.person.ClearCommand;
import seedu.address.logic.commands.person.DeleteCommand;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.commands.person.FindCommand;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.commands.person.SetUserCommand;
import seedu.address.logic.commands.person.ViewGroupCommand;
import seedu.address.logic.commands.person.ViewScheduleCommand;
import seedu.address.logic.commands.schedule.AddEventCommand;
import seedu.address.logic.commands.schedule.ClearScheduleCommand;
import seedu.address.logic.commands.schedule.DeleteEventCommand;
import seedu.address.logic.commands.schedule.EditEventCommand;
import seedu.address.logic.commands.schedule.ExportScheduleCommand;
import seedu.address.logic.commands.schedule.ImportScheduleCommand;
import seedu.address.logic.commands.schedule.WhoIsFreeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.IsTagInPersonPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.schedule.Event;
import seedu.address.model.schedule.IsPersonFreePredicate;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private static final String ADD_COMMAND_UPPER = "ADD";
    private static final String CLEAR_COMMAND_UPPER = "CLEAR";
    private static final String DELETE_COMMAND_UPPER = "DELETE";
    private static final String EDIT_COMMAND_UPPER = "EDIT";
    private static final String EXIT_COMMAND_UPPER = "EXIT";
    private static final String FIND_COMMAND_UPPER = "FIND";
    private static final String HELP_COMMAND_UPPER = "HELP";
    private static final String LIST_COMMAND_UPPER = "LIST";
    private static final String ADD_EVENT_COMMAND_UPPER = "ADDEVENT";
    private static final String EDIT_EVENT_COMMAND_UPPER = "EDITEVENT";
    private static final String DELETE_EVENT_COMMAND_UPPER = "DELETEEVENT";
    private static final String WHO_IS_FREE_COMMAND_UPPER = "WHOISFREE";
    private static final String VIEW_GROUP_COMMAND_UPPER = "VIEWGROUP";
    private static final String VIEW_SCHEDULE_COMMAND_UPPER = "VIEWSCHEDULE";
    private static final String EXPORT_SCHEDULE_COMMAND_UPPER = "EXPORTSCHEDULE";
    private static final String IMPORT_SCHEDULE_COMMAND_UPPER = "IMPORTSCHEDULE";
    private static final String SET_USER_COMMAND_UPPER = "SETUSER";
    private static final String CLEAR_SCHEDULE_COMMAND_UPPER = "CLEARSCHEDULE";

    private final AddressBookParser parser = new AddressBookParser();


    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser
                .parseCommand(PersonUtil.getAddEventCommand(INDEX_FIRST_PERSON, event));
        assertEquals(new AddEventCommand(INDEX_FIRST_PERSON, event), command);
    }

    @Test
    public void parseCommand_editEvent() throws Exception {
        Event event = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(event).build();
        EditEventCommand command = (EditEventCommand) parser.parseCommand(EditEventCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_FIRST_EVENT.getOneBased()
                + " " + PersonUtil.getEditEventDescriptorDetails(descriptor));
        assertEquals(new EditEventCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT, descriptor), command);
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT), command);
    }

    @Test
    public void parseCommand_freeSchedule() throws Exception {
        WhoIsFreeCommand command = (WhoIsFreeCommand) parser.parseCommand(
                WhoIsFreeCommand.COMMAND_WORD + " " + PREFIX_TIME + TYPICAL_TIME.toString() + " "
                        + PREFIX_DATE + TYPICAL_DATE.toString()
        );
        WhoIsFreeCommand expectedCommand =
                new WhoIsFreeCommand(new IsPersonFreePredicate(TYPICAL_TIME, TYPICAL_DATE, new HashSet<>()));
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_clearSchedule() throws Exception {
        ClearScheduleCommand command = (ClearScheduleCommand) parser.parseCommand(
                ClearScheduleCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new ClearScheduleCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_viewGroup() throws Exception {
        String keyword = "foo";
        ViewGroupCommand command = (ViewGroupCommand) parser.parseCommand(
                ViewGroupCommand.COMMAND_WORD + " " + PREFIX_TAG + keyword
        );
        assertEquals(new ViewGroupCommand(new IsTagInPersonPredicate(new Tag(keyword))), command);
    }

    @Test
    public void parseCommand_viewSchedule() throws Exception {
        ViewScheduleCommand command = (ViewScheduleCommand) parser.parseCommand(
                ViewScheduleCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new ViewScheduleCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_exportSchedule() throws Exception {
        ExportScheduleCommand command = (ExportScheduleCommand) parser.parseCommand(
                ExportScheduleCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new ExportScheduleCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_importSchedule() throws Exception {
        ImportScheduleCommand command = (ImportScheduleCommand) parser.parseCommand(
                ImportScheduleCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_FILEPATH + TYPICAL_PATH
        );
        assertEquals(new ImportScheduleCommand(INDEX_FIRST_PERSON, Paths.get(TYPICAL_PATH)), command);
    }

    @Test
    public void parseCommand_setUser() throws Exception {
        SetUserCommand command = (SetUserCommand) parser.parseCommand(
                SetUserCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new SetUserCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_allLowerCase_success() throws ParseException {
        // addEvent
        Event event = new EventBuilder().build();
        AddEventCommand addEventCommand = (AddEventCommand) parser
                .parseCommand(AddEventCommand.COMMAND_WORD_LOWER
                        + PersonUtil.getAddEventCommand(INDEX_FIRST_PERSON, event).substring(8));
        assertEquals(new AddEventCommand(INDEX_FIRST_PERSON, event), addEventCommand);

        // editEvent
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(event).build();
        EditEventCommand editEventCommand = (EditEventCommand) parser.parseCommand(EditEventCommand.COMMAND_WORD_LOWER
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_FIRST_EVENT.getOneBased()
                + " " + PersonUtil.getEditEventDescriptorDetails(descriptor));
        assertEquals(new EditEventCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT, descriptor), editEventCommand);

        // deleteEvent
        DeleteEventCommand deleteEventCommand = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD_LOWER + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT), deleteEventCommand);

        // freeSchedule
        WhoIsFreeCommand freeScheduleCommand = (WhoIsFreeCommand) parser.parseCommand(
                WhoIsFreeCommand.COMMAND_WORD_LOWER + " " + PREFIX_TIME + TYPICAL_TIME.toString() + " "
                        + PREFIX_DATE + TYPICAL_DATE.toString()
        );
        assertEquals(new WhoIsFreeCommand(new IsPersonFreePredicate(TYPICAL_TIME, TYPICAL_DATE, new HashSet<>())),
                freeScheduleCommand);

        // viewGroup
        String keyword = "foo";
        ViewGroupCommand viewGroupCommand = (ViewGroupCommand) parser.parseCommand(
                ViewGroupCommand.COMMAND_WORD_LOWER + " " + PREFIX_TAG + keyword
        );
        assertEquals(new ViewGroupCommand(new IsTagInPersonPredicate(new Tag(keyword))), viewGroupCommand);

        // clearSchedule
        ClearScheduleCommand clearScheduleCommand = (ClearScheduleCommand) parser.parseCommand(
                ClearScheduleCommand.COMMAND_WORD_LOWER + " " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new ClearScheduleCommand(INDEX_FIRST_PERSON), clearScheduleCommand);

        // viewSchedule
        ViewScheduleCommand viewScheduleCommand = (ViewScheduleCommand) parser.parseCommand(
                ViewScheduleCommand.COMMAND_WORD_LOWER + " " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new ViewScheduleCommand(INDEX_FIRST_PERSON), viewScheduleCommand);

        // exportSchedule
        ExportScheduleCommand exportScheduleCommand = (ExportScheduleCommand) parser.parseCommand(
                ExportScheduleCommand.COMMAND_WORD_LOWER + " " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new ExportScheduleCommand(INDEX_FIRST_PERSON), exportScheduleCommand);

        // importSchedule
        ImportScheduleCommand importScheduleCommand = (ImportScheduleCommand) parser.parseCommand(
                ImportScheduleCommand.COMMAND_WORD_LOWER + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_FILEPATH + TYPICAL_PATH
        );
        assertEquals(new ImportScheduleCommand(INDEX_FIRST_PERSON, Paths.get(TYPICAL_PATH)), importScheduleCommand);

        // setUser
        SetUserCommand setUserCommand = (SetUserCommand) parser.parseCommand(
                SetUserCommand.COMMAND_WORD_LOWER + " " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new SetUserCommand(INDEX_FIRST_PERSON), setUserCommand);

    }

    @Test
    public void parseCommand_allUpperCase_success() throws ParseException {
        // add
        Person person = new PersonBuilder().build();
        AddCommand addCommand = (AddCommand) parser.parseCommand(ADD_COMMAND_UPPER
                + PersonUtil.getAddCommand(person).substring(3));
        assertEquals(new AddCommand(person), addCommand);

        // clear
        assertTrue(parser.parseCommand(CLEAR_COMMAND_UPPER) instanceof ClearCommand);
        assertTrue(parser.parseCommand(CLEAR_COMMAND_UPPER + " 3") instanceof ClearCommand);

        // delete
        DeleteCommand deleteCommand = (DeleteCommand) parser.parseCommand(
                DELETE_COMMAND_UPPER + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), deleteCommand);

        // edit
        person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand editCommand = (EditCommand) parser.parseCommand(EDIT_COMMAND_UPPER + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), editCommand);

        // exit
        assertTrue(parser.parseCommand(EXIT_COMMAND_UPPER) instanceof ExitCommand);
        assertTrue(parser.parseCommand(EXIT_COMMAND_UPPER + " 3") instanceof ExitCommand);

        // find
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand findCommand = (FindCommand) parser.parseCommand(
                FIND_COMMAND_UPPER + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), findCommand);

        // help
        assertTrue(parser.parseCommand(HELP_COMMAND_UPPER) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HELP_COMMAND_UPPER + " 3") instanceof HelpCommand);

        // list
        assertTrue(parser.parseCommand(LIST_COMMAND_UPPER) instanceof ListCommand);
        assertTrue(parser.parseCommand(LIST_COMMAND_UPPER + " 3") instanceof ListCommand);

        // addEvent
        Event event = new EventBuilder().build();
        AddEventCommand addEventCommand = (AddEventCommand) parser
                .parseCommand(ADD_EVENT_COMMAND_UPPER
                        + PersonUtil.getAddEventCommand(INDEX_FIRST_PERSON, event).substring(8));
        assertEquals(new AddEventCommand(INDEX_FIRST_PERSON, event), addEventCommand);

        // editEvent
        EditEventDescriptor editEventDescriptor = new EditEventDescriptorBuilder(event).build();
        EditEventCommand editEventCommand = (EditEventCommand) parser.parseCommand(EDIT_EVENT_COMMAND_UPPER
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_FIRST_EVENT.getOneBased()
                + " " + PersonUtil.getEditEventDescriptorDetails(editEventDescriptor));
        assertEquals(new EditEventCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT, editEventDescriptor),
                editEventCommand);

        // deleteEvent
        DeleteEventCommand deleteEventCommand = (DeleteEventCommand) parser.parseCommand(
                DELETE_EVENT_COMMAND_UPPER + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT), deleteEventCommand);

        // whoIsFree
        WhoIsFreeCommand freeScheduleCommand = (WhoIsFreeCommand) parser.parseCommand(
                WHO_IS_FREE_COMMAND_UPPER + " " + PREFIX_TIME + TYPICAL_TIME.toString() + " "
                        + PREFIX_DATE + TYPICAL_DATE.toString()
        );
        assertEquals(new WhoIsFreeCommand(new IsPersonFreePredicate(TYPICAL_TIME, TYPICAL_DATE, new HashSet<>())),
                freeScheduleCommand);

        // viewGroup
        String keyword = "foo";
        ViewGroupCommand viewGroupCommand = (ViewGroupCommand) parser.parseCommand(
                VIEW_GROUP_COMMAND_UPPER + " " + PREFIX_TAG + keyword
        );
        assertEquals(new ViewGroupCommand(new IsTagInPersonPredicate(new Tag(keyword))), viewGroupCommand);

        // clearSchedule
        ClearScheduleCommand command = (ClearScheduleCommand) parser.parseCommand(
                CLEAR_SCHEDULE_COMMAND_UPPER + " " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new ClearScheduleCommand(INDEX_FIRST_PERSON), command);

        // viewSchedule
        ViewScheduleCommand viewScheduleCommand = (ViewScheduleCommand) parser.parseCommand(
                VIEW_SCHEDULE_COMMAND_UPPER + " " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new ViewScheduleCommand(INDEX_FIRST_PERSON), viewScheduleCommand);

        // exportSchedule
        ExportScheduleCommand exportScheduleCommand = (ExportScheduleCommand) parser.parseCommand(
                EXPORT_SCHEDULE_COMMAND_UPPER + " " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new ExportScheduleCommand(INDEX_FIRST_PERSON), exportScheduleCommand);

        // importSchedule
        ImportScheduleCommand importScheduleCommand = (ImportScheduleCommand) parser.parseCommand(
                IMPORT_SCHEDULE_COMMAND_UPPER + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_FILEPATH + TYPICAL_PATH
        );
        assertEquals(new ImportScheduleCommand(INDEX_FIRST_PERSON, Paths.get(TYPICAL_PATH)), importScheduleCommand);

        // setUser
        SetUserCommand setUserCommand = (SetUserCommand) parser.parseCommand(
                SET_USER_COMMAND_UPPER + " " + INDEX_FIRST_PERSON.getOneBased()
        );
        assertEquals(new SetUserCommand(INDEX_FIRST_PERSON), setUserCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
