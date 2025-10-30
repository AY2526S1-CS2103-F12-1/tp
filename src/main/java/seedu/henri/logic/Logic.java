package seedu.henri.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.henri.commons.core.GuiSettings;
import seedu.henri.logic.commands.CommandResult;
import seedu.henri.logic.commands.exceptions.CommandException;
import seedu.henri.logic.parser.exceptions.ParseException;
import seedu.henri.model.ReadOnlyHenri;
import seedu.henri.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the Henri.
     *
     * @see seedu.henri.model.Model#getHenri()
     */
    ReadOnlyHenri getHenri();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' Henri file path.
     */
    Path getHenriFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the organization hierarchy in string format.
     * @return a string in Linux tree format representing the organization hierarchy.
     */
    String getOrganizationHierarchyString();
}
