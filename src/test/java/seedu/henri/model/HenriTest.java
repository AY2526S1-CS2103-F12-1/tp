package seedu.henri.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.henri.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.henri.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.henri.testutil.Assert.assertThrows;
import static seedu.henri.testutil.TypicalPersons.ALICE;
import static seedu.henri.testutil.TypicalPersons.getTypicalHenri;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.henri.model.audit.AuditLog;
import seedu.henri.model.person.Person;
import seedu.henri.model.person.exceptions.DuplicatePersonException;
import seedu.henri.model.team.Team;
import seedu.henri.testutil.PersonBuilder;

public class HenriTest {

    private final Henri henri = new Henri();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), henri.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> henri.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyHenri_replacesData() {
        Henri newData = getTypicalHenri();
        henri.resetData(newData);
        assertEquals(newData, henri);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        HenriStub newData = new HenriStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> henri.resetData(newData));
    }

    @Test
    public void resetData_withAuditLog_restoresAuditLog() {
        // Create Henri with audit log entries
        Henri sourceHenri = new Henri();
        sourceHenri.addPerson(ALICE);
        sourceHenri.addAuditEntry("ADD", "Added Alice");
        sourceHenri.addAuditEntry("EDIT", "Edited Alice");

        // Reset data to a new Henri
        Henri targetHenri = new Henri();
        targetHenri.resetData(sourceHenri);

        // Verify audit log is restored
        assertEquals(2, targetHenri.getAuditLog().getEntries().size());
        assertEquals("ADD", targetHenri.getAuditLog().getEntries().get(0).getAction());
        assertEquals("Added Alice", targetHenri.getAuditLog().getEntries().get(0).getDetails());
        assertEquals("EDIT", targetHenri.getAuditLog().getEntries().get(1).getAction());
        assertEquals("Edited Alice", targetHenri.getAuditLog().getEntries().get(1).getDetails());
    }

    @Test
    public void resetData_withExistingAuditLog_clearsAndRestores() {
        // Create target Henri with existing audit log
        Henri targetHenri = new Henri();
        targetHenri.addAuditEntry("DELETE", "Old entry");

        // Create source Henri with different audit log
        Henri sourceHenri = new Henri();
        sourceHenri.addAuditEntry("ADD", "New entry");

        // Reset data
        targetHenri.resetData(sourceHenri);

        // Verify old audit log is cleared and new one is restored
        assertEquals(1, targetHenri.getAuditLog().getEntries().size());
        assertEquals("ADD", targetHenri.getAuditLog().getEntries().get(0).getAction());
        assertEquals("New entry", targetHenri.getAuditLog().getEntries().get(0).getDetails());
    }


    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> henri.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInHenri_returnsFalse() {
        assertFalse(henri.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInHenri_returnsTrue() {
        henri.addPerson(ALICE);
        assertTrue(henri.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInHenri_returnsTrue() {
        henri.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(henri.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> henri.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = Henri.class.getCanonicalName()
                + "{persons=" + henri.getPersonList()
                + ", teams=" + henri.getTeamList() + "}";
        assertEquals(expected, henri.toString());
    }

    /**
     * A stub ReadOnlyHenri whose persons list can violate interface constraints.
     */
    private static class HenriStub implements ReadOnlyHenri {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        HenriStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Team> getTeamList() {
            throw new UnsupportedOperationException("Teams not supported in this stub");
        }

        @Override
        public AuditLog getAuditLog() {
            return new AuditLog();
        }
    }
}
