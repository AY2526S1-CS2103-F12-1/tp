package seedu.henri.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.henri.commons.exceptions.DataLoadingException;
import seedu.henri.model.ReadOnlyHenri;
import seedu.henri.model.ReadOnlyUserPrefs;
import seedu.henri.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends HenriStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getHenriFilePath();

    @Override
    Optional<ReadOnlyHenri> readHenri() throws DataLoadingException;

    @Override
    void saveHenri(ReadOnlyHenri henri) throws IOException;

}
