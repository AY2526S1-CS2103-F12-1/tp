package seedu.henri.model;

import static java.util.Objects.requireNonNull;
import static seedu.henri.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.henri.commons.core.GuiSettings;
import seedu.henri.commons.core.LogsCenter;
import seedu.henri.model.audit.AuditLog;
import seedu.henri.model.person.Person;
import seedu.henri.model.team.Team;

/**
 * Represents the in-memory model of Henri data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Henri henri;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given henri and userPrefs.
     */
    public ModelManager(ReadOnlyHenri henri, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(henri, userPrefs);

        logger.fine("Initializing with henri: " + henri + " and user prefs " + userPrefs);

        this.henri = new Henri(henri);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.henri.getPersonList());
    }

    public ModelManager() {
        this(new Henri(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getHenriFilePath() {
        return userPrefs.getHenriFilePath();
    }

    @Override
    public void setHenriFilePath(Path henriFilePath) {
        requireNonNull(henriFilePath);
        userPrefs.setHenriFilePath(henriFilePath);
    }

    //=========== Henri ================================================================================

    @Override
    public void setHenri(ReadOnlyHenri henri) {
        this.henri.resetData(henri);
    }

    @Override
    public ReadOnlyHenri getHenri() {
        return henri;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return henri.hasPerson(person);
    }

    @Override
    public boolean hasTeam(Team team) {
        requireNonNull(team);
        return henri.hasTeam(team);
    }

    @Override
    public void deletePerson(Person target) {
        henri.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        henri.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addTeam(Team team) {
        requireNonNull(team);
        henri.addTeam(team);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        henri.setPerson(target, editedPerson);
    }

    @Override
    public Person find(Predicate<Person> predicate) {
        requireNonNull(predicate);
        return henri.getPersonList().stream().filter(predicate).findFirst().orElse(null);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedHenri}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager otherModelManager)) {
            return false;
        }

        boolean isSameHenri = henri.equals(otherModelManager.henri);
        boolean isSameUserPrefs = userPrefs.equals(otherModelManager.userPrefs);
        boolean isSameFilteredPersons = filteredPersons.equals(otherModelManager.filteredPersons);

        return henri.equals(otherModelManager.henri)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    //=========== Audit Log Entry  =============================================================
    @Override
    public void addAuditEntry(String action, String details) {
        henri.addAuditEntry(action, details);
    }

    @Override
    public AuditLog getAuditLog() {
        return henri.getAuditLog();
    }

    //=========== Organization Hierarchy Accessors =============================================================
    @Override
    public String getOrganizationHierarchyString() {
        return """
                    ├── Finance Department
                    │   ├── Samuel Lee
                    │   └── Rachel Tan
                    ├── Engineering Department
                    │   ├── API Development
                    │   │   └── Alice Chen
                    │   └── Frontend
                    │       └── Felicia Wong
                    └── Quality Assurance
                        ├── Michael Ong
                        └── Derrick Lim
                """;
    }

    @Override
    public void removeTeam(Team team) {
        requireNonNull(team);
        henri.removeTeam(team);
    }

    @Override
    public void sortPersons(Comparator<Person> comparator) {
        requireNonNull(comparator);
        henri.sortPersons(comparator);
    }

    @Override
    public void setTeam(Team target, Team editedTeam) {
        requireAllNonNull(target, editedTeam);
        henri.setTeam(target, editedTeam);
    }
}
