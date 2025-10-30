package seedu.henri.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.henri.commons.exceptions.DataLoadingException;
import seedu.henri.model.Henri;
import seedu.henri.model.ReadOnlyHenri;

/**
 * Represents a storage for {@link Henri}.
 */
public interface HenriStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getHenriFilePath();

    /**
     * Returns Henri data as a {@link ReadOnlyHenri}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyHenri> readHenri() throws DataLoadingException;

    /**
     * @see #getHenriFilePath()
     */
    Optional<ReadOnlyHenri> readHenri(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyHenri} to the storage.
     * @param henri cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHenri(ReadOnlyHenri henri) throws IOException;

    /**
     * @see #saveHenri(ReadOnlyHenri)
     */
    void saveHenri(ReadOnlyHenri henri, Path filePath) throws IOException;

}
