package seedu.henri.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.henri.commons.util.ToStringBuilder;
import seedu.henri.model.audit.AuditLog;
import seedu.henri.model.person.Person;
import seedu.henri.model.person.UniquePersonList;
import seedu.henri.model.team.Team;
import seedu.henri.model.team.UniqueTeamList;

/**
 * Represents an in-memory Henri containing persons and teams.
 */
public class Henri implements ReadOnlyHenri {

    private final UniquePersonList persons = new UniquePersonList();
    private final UniqueTeamList teams = new UniqueTeamList();
    private final AuditLog auditLog = new AuditLog();


    public Henri() {}

    /**
     * Creates Henri using the Persons and Teams in the {@code toBeCopied}.
     */
    public Henri(ReadOnlyHenri toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the persons list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the teams list with {@code teams}.
     * {@code teams} must not contain duplicate teams.
     */
    public void setTeams(List<Team> teams) {
        this.teams.setTeams(teams);
    }

    /**
     * Replaces this Henri's data with the provided {@code newData}.
     * Persons are always replaced. Teams are replaced only if {@code newData}
     * exposes a team list; otherwise team data is left unchanged. (to be cleaned further later)
     *
     * @param newData the source data to copy; must not be null
     * @throws NullPointerException if {@code newData} is null
     */
    public void resetData(ReadOnlyHenri newData) {
        requireNonNull(newData);
        setPersons(newData.getPersonList());

        // Restore audit log from persisted data
        auditLog.clear();
        for (var entry : newData.getAuditLog().getEntries()) {
            auditLog.addEntry(entry.getAction(), entry.getDetails(), entry.getTimestamp());
        }
        // ReadOnlyHenri is expected to expose getTeamList()
        if (newData instanceof ReadOnlyHenri) {
            try {
                setTeams(((ReadOnlyHenri) newData).getTeamList());
            } catch (UnsupportedOperationException | ClassCastException e) {
                // If the provided ReadOnlyHenri does not expose teams yet, ignore.
            }
        }
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in Henri.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to Henri.
     * The person must not already exist in Henri.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in Henri.
     */
    public void setPerson(Person target, Person editedPerson) {
        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code Henri}.
     * {@code key} must exist in Henri.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    @Override
    public AuditLog getAuditLog() {
        return auditLog;
    }

    public void addAuditEntry(String action, String details) {
        auditLog.addEntry(action, details, LocalDateTime.now());
    }

    //// team-level operations

    /**
     * Returns true if a team with the same identity as {@code team} exists in Henri.
     */
    public boolean hasTeam(Team team) {
        requireNonNull(team);
        return teams.contains(team);
    }

    /**
     * Adds a team to Henri.
     * The team must not already exist in Henri.
     */
    public void addTeam(Team team) {
        requireNonNull(team);
        teams.add(team);
    }

    /**
     * Replaces the given team {@code target} in the list with {@code editedTeam}.
     * {@code target} must exist in Henri.
     */
    public void setTeam(Team target, Team editedTeam) {
        teams.setTeam(target, editedTeam);
    }

    /**
     * Removes {@code toRemove} from this {@code Henri}.
     * {@code toRemove} must exist in Henri.
     */
    public void removeTeam(Team toRemove) {
        teams.remove(toRemove);
    }

    /**
     * Sorts the list of persons according to the given comparator.
     * @param comparator The comparator used to compare the selected keys.
     */
    public void sortPersons(Comparator<Person> comparator) {
        requireNonNull(comparator);
        persons.sort(comparator);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("teams", teams)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        // Current Internal Code to check for changes
        // (Due to not having UI implemented yet)
        System.out.println(teams);
        System.out.println(persons);
        return persons.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the teams list.
     */
    public ObservableList<Team> getTeamList() {
        return teams.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Henri otherHenri)) {
            return false;
        }

        Henri otherAb = (Henri) other;
        return persons.equals(otherAb.persons) && teams.equals(otherAb.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, teams);
    }
}
