package seedu.henri.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.henri.commons.core.LogsCenter;
import seedu.henri.commons.exceptions.DataLoadingException;
import seedu.henri.model.ReadOnlyHenri;
import seedu.henri.model.ReadOnlyUserPrefs;
import seedu.henri.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private HenriStorage henriStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(HenriStorage henriStorage, UserPrefsStorage userPrefsStorage) {
        this.henriStorage = henriStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return henriStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyHenri> readAddressBook() throws DataLoadingException {
        return readAddressBook(henriStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyHenri> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return henriStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyHenri addressBook) throws IOException {
        saveAddressBook(addressBook, henriStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyHenri addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        henriStorage.saveAddressBook(addressBook, filePath);
    }

}
