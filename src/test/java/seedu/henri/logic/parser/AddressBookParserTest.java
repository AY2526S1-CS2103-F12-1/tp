package seedu.henri.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.henri.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.henri.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.henri.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.henri.logic.commands.AddCommand;
import seedu.henri.logic.commands.AddToTeamCommand;
import seedu.henri.logic.commands.AuditCommand;
import seedu.henri.logic.commands.ClearCommand;
import seedu.henri.logic.commands.CreateTeamCommand;
import seedu.henri.logic.commands.DeleteCommand;
import seedu.henri.logic.commands.EditCommand;
import seedu.henri.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.henri.logic.commands.ExitCommand;
import seedu.henri.logic.commands.HelpCommand;
import seedu.henri.logic.commands.ListCommand;
import seedu.henri.logic.commands.RemoveFromTeamCommand;
import seedu.henri.logic.commands.SetSalaryCommand;
import seedu.henri.logic.commands.TagCommand;
import seedu.henri.logic.commands.UntagCommand;
import seedu.henri.logic.commands.ViewCommand;
import seedu.henri.logic.parser.exceptions.ParseException;
import seedu.henri.model.person.NameContainsKeywordsPredicate;
import seedu.henri.model.person.Person;
import seedu.henri.model.tag.Tag;
import seedu.henri.testutil.EditPersonDescriptorBuilder;
import seedu.henri.testutil.PersonBuilder;
import seedu.henri.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().withoutTags().build();
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
                DeleteCommand.COMMAND_WORD + " E1234");
        assertEquals(new DeleteCommand("E1234"), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().withoutTags().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(person.name().fullName())
                .withPhone(person.phone().value())
                .withEmail(person.email().value())
                .withAddress(person.address().value())
                .withGitHubUsername(person.gitHubUsername().value())
                .build();
        String employeeId = "E0001";
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + employeeId + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(employeeId, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new ViewCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_tag() throws Exception {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friends"));
        TagCommand command = (TagCommand) parser.parseCommand(
                TagCommand.COMMAND_WORD + " E1001 friends");
        assertEquals(new TagCommand("E1001", tags), command);
    }

    @Test
    public void parseCommand_untag() throws Exception {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friends"));
        UntagCommand command = (UntagCommand) parser.parseCommand(
                UntagCommand.COMMAND_WORD + " E1001 friends");
        assertEquals(new UntagCommand("E1001", tags), command);
    }


    @Test
    public void parseCommand_setSalary() throws Exception {
        Person person = new PersonBuilder().build();
        SetSalaryCommand command = (SetSalaryCommand) parser.parseCommand(
                SetSalaryCommand.COMMAND_WORD + " " + person.id() + " 100");
        assertEquals(new SetSalaryCommand(person.id(), 10000), command);
        command = (SetSalaryCommand) parser.parseCommand(
                SetSalaryCommand.COMMAND_WORD + " " + person.id() + " 100.23");
        assertEquals(new SetSalaryCommand(person.id(), 10023), command);
    }

    @Test
    public void parseCommand_audit() throws Exception {
        assertTrue(parser.parseCommand(AuditCommand.COMMAND_WORD) instanceof AuditCommand);
        assertTrue(parser.parseCommand(AuditCommand.COMMAND_WORD + " 3") instanceof AuditCommand);
    }


    @Test
    public void parseCommand_addToTeam() throws Exception {
        AddToTeamCommand command = (AddToTeamCommand) parser.parseCommand(
                AddToTeamCommand.COMMAND_WORD + " T0001 E0001");
        assertEquals(new AddToTeamCommand("T0001", "E0001"), command);
    }

    @Test
    public void parseCommand_removeFromTeam() throws Exception {
        RemoveFromTeamCommand command = (RemoveFromTeamCommand) parser.parseCommand(
                RemoveFromTeamCommand.COMMAND_WORD + " T0001 E0001");
        assertEquals(new RemoveFromTeamCommand("T0001", "E0001"), command);
    }

    @Test
    public void parseCommand_createTeam() throws Exception {
        Person person = new PersonBuilder().build();
        CreateTeamCommand command = (CreateTeamCommand) parser.parseCommand(
                CreateTeamCommand.COMMAND_WORD + " Systems " + person.id());
        assertEquals(new CreateTeamCommand("Systems", person.id()), command);
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
