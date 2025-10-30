package seedu.henri.model;

import java.nio.file.Path;

import seedu.henri.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getHenriFilePath();

}
