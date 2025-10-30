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
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyHenri}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyHenri> readAddressBook() throws DataLoadingException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyHenri> readAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyHenri} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyHenri addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyHenri)
     */
    void saveAddressBook(ReadOnlyHenri addressBook, Path filePath) throws IOException;

}
