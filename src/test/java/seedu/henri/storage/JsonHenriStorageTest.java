package seedu.henri.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.henri.testutil.Assert.assertThrows;
import static seedu.henri.testutil.TypicalPersons.ALICE;
import static seedu.henri.testutil.TypicalPersons.HOON;
import static seedu.henri.testutil.TypicalPersons.IDA;
import static seedu.henri.testutil.TypicalPersons.getTypicalHenri;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.henri.commons.exceptions.DataLoadingException;
import seedu.henri.model.Henri;
import seedu.henri.model.ReadOnlyHenri;

public class JsonHenriStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonHenriStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readHenri_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readHenri(null));
    }

    private java.util.Optional<ReadOnlyHenri> readHenri(String filePath) throws Exception {
        return new JsonHenriStorage(Paths.get(filePath)).readHenri(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readHenri("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readHenri("notJsonFormatHenri.json"));
    }

    @Test
    public void readHenri_invalidPersonHenri_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readHenri("invalidPersonHenri.json"));
    }

    @Test
    public void readHenri_invalidAndValidPersonHenri_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readHenri("invalidAndValidPersonHenri.json"));
    }

    @Test
    public void readAndSaveHenri_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempHenri.json");
        Henri original = getTypicalHenri();
        JsonHenriStorage jsonHenriStorage = new JsonHenriStorage(filePath);

        // Save in new file and read back
        jsonHenriStorage.saveHenri(original, filePath);
        ReadOnlyHenri readBack = jsonHenriStorage.readHenri(filePath).get();
        assertEquals(original, new Henri(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonHenriStorage.saveHenri(original, filePath);
        readBack = jsonHenriStorage.readHenri(filePath).get();
        assertEquals(original, new Henri(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonHenriStorage.saveHenri(original); // file path not specified
        readBack = jsonHenriStorage.readHenri().get(); // file path not specified
        assertEquals(original, new Henri(readBack));

    }

    @Test
    public void saveHenri_nullHenri_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHenri(null, "SomeFile.json"));
    }

    /**
     * Saves {@code henri} at the specified {@code filePath}.
     */
    private void saveHenri(ReadOnlyHenri henri, String filePath) {
        try {
            new JsonHenriStorage(Paths.get(filePath))
                    .saveHenri(henri, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveHenri_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHenri(new Henri(), null));
    }
}
