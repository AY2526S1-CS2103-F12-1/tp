package seedu.henri.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.henri.logic.commands.exceptions.CommandException;
import seedu.henri.model.Henri;
import seedu.henri.model.Model;
import seedu.henri.model.ModelManager;
import seedu.henri.model.UserPrefs;
import seedu.henri.model.person.Person;
import seedu.henri.testutil.PersonBuilder;
import seedu.henri.testutil.TeamBuilder;

/**
 * Tests for {@link RemoveFromTeamCommand}.
 */
public class RemoveFromTeamCommandTest {

    @Test
    public void execute_removesMemberAndUpdatesPerson() throws Exception {
        Model model = new ModelManager(new Henri(), new UserPrefs());
        Person p = new PersonBuilder().withId(1).build();
        model.addPerson(p);

        // create team that contains the person
        model.addTeam(new TeamBuilder().withId("T0001").withMembers("E0001").build());

        RemoveFromTeamCommand cmd = new RemoveFromTeamCommand("T0001", "E0001");
        CommandResult result = cmd.execute(model);

        assertEquals(String.format(RemoveFromTeamCommand.MESSAGE_SUCCESS, "E0001", "T0001"),
                result.getFeedbackToUser());

        // team no longer contains member
        assertTrue(model.getAddressBook().getTeamList().stream()
                .filter(t -> "T0001".equals(t.getId()))
                .findFirst()
                .map(t -> !t.getMembers().contains("E0001"))
                .orElse(false));

        // person no longer has team id
        Person updated = model.find(person -> "E0001".equals(person.id()));
        assertTrue(!updated.teamIds().contains("T0001"));
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        Model model = new ModelManager(new Henri(), new UserPrefs());
        model.addTeam(new TeamBuilder().withId("T0001").withMembers("E0001").build());

        RemoveFromTeamCommand cmd = new RemoveFromTeamCommand("T0001", "E9999");
        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(String.format(RemoveFromTeamCommand.MESSAGE_PERSON_NOT_FOUND, "E9999"), ex.getMessage());
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() {
        Model model = new ModelManager(new Henri(), new UserPrefs());
        Person p = new PersonBuilder().withId(1).build();
        model.addPerson(p);

        RemoveFromTeamCommand cmd = new RemoveFromTeamCommand("T9999", "E0001");
        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(String.format(RemoveFromTeamCommand.MESSAGE_TEAM_NOT_FOUND, "T9999"), ex.getMessage());
    }

    @Test
    public void execute_notMember_throwsCommandException() {
        Model model = new ModelManager(new Henri(), new UserPrefs());
        Person p = new PersonBuilder().withId(1).build();
        model.addPerson(p);

        // team exists but person is not a member
        model.addTeam(new TeamBuilder().withId("T0001").build());

        RemoveFromTeamCommand cmd = new RemoveFromTeamCommand("T0001", "E0001");
        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(String.format(RemoveFromTeamCommand.MESSAGE_NOT_MEMBER, "E0001", "T0001"), ex.getMessage());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        RemoveFromTeamCommand a = new RemoveFromTeamCommand("T0001", "E0001");
        RemoveFromTeamCommand b = new RemoveFromTeamCommand("T0001", "E0001");
        assertTrue(a.equals(b));
        assertTrue(a.equals(a));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        RemoveFromTeamCommand base = new RemoveFromTeamCommand("T0001", "E0001");
        RemoveFromTeamCommand diffTeam = new RemoveFromTeamCommand("T0002", "E0001");
        RemoveFromTeamCommand diffPerson = new RemoveFromTeamCommand("T0001", "E9999");
        assertFalse(base.equals(diffTeam));
        assertFalse(base.equals(diffPerson));
        assertFalse(base.equals(null));
        assertFalse(base.equals(new Object()));
    }

    @Test
    public void toString_containsIds() {
        RemoveFromTeamCommand cmd = new RemoveFromTeamCommand("T0001", "E0001");
        String s = cmd.toString();
        assertTrue(s.contains("teamId"));
        assertTrue(s.contains("T0001"));
        assertTrue(s.contains("personId"));
        assertTrue(s.contains("E0001"));
    }
}
