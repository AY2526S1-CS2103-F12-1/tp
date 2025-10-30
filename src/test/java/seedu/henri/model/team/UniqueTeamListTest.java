package seedu.henri.model.team;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.henri.testutil.TypicalTeams.CORE;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.henri.model.team.exceptions.DuplicateTeamException;
import seedu.henri.model.team.exceptions.TeamNotFoundException;
import seedu.henri.testutil.TeamBuilder;

/**
 * Unit tests for {@link UniqueTeamList}.
 */
public class UniqueTeamListTest {

    @Test
    public void contains_teamWithSameId_returnsTrue() {
        UniqueTeamList list = new UniqueTeamList();
        list.add(CORE);
        // team with same id but different name should be considered duplicate / same
        Team edited = new TeamBuilder(CORE).withTeamName("Different Name").build();
        assertTrue(list.contains(edited));
    }

    @Test
    public void add_duplicateTeam_throwsDuplicateTeamException() {
        UniqueTeamList list = new UniqueTeamList();
        list.add(CORE);
        Team duplicate = new TeamBuilder(CORE).build();
        assertThrows(DuplicateTeamException.class, () -> list.add(duplicate));
    }

    @Test
    public void remove_teamNotFound_throwsTeamNotFoundException() {
        UniqueTeamList list = new UniqueTeamList();
        assertThrows(TeamNotFoundException.class, () -> list.remove(CORE));
    }

    @Test
    public void setTeams_listWithDuplicates_throwsDuplicateTeamException() {
        UniqueTeamList list = new UniqueTeamList();
        assertThrows(DuplicateTeamException.class, () -> list.setTeams(Arrays.asList(CORE, CORE)));
    }
}
