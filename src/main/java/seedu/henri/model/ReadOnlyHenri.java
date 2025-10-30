package seedu.henri.model;

import javafx.collections.ObservableList;
import seedu.henri.model.audit.AuditLog;
import seedu.henri.model.person.Person;
import seedu.henri.model.team.Team;

/**
 * Unmodifiable view of Henri
 */
public interface ReadOnlyHenri {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    AuditLog getAuditLog();
    ObservableList<Team> getTeamList();
}
