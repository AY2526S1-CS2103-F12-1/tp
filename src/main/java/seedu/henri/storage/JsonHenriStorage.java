package seedu.henri.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.henri.commons.core.LogsCenter;
import seedu.henri.commons.exceptions.DataLoadingException;
import seedu.henri.commons.exceptions.IllegalValueException;
import seedu.henri.commons.util.FileUtil;
import seedu.henri.commons.util.JsonUtil;
import seedu.henri.model.ReadOnlyHenri;

/**
 * A class to access Henri data stored as a json file on the hard disk.
 */
public class JsonHenriStorage implements HenriStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonHenriStorage.class);

    private Path filePath;

    public JsonHenriStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getHenriFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyHenri> readHenri() throws DataLoadingException {
        return readHenri(filePath);
    }

    /**
     * Similar to {@link #readHenri()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyHenri> readHenri(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableHenri> jsonHenri = JsonUtil.readJsonFile(
                filePath, JsonSerializableHenri.class);
        if (!jsonHenri.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonHenri.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveHenri(ReadOnlyHenri henri) throws IOException {
        saveHenri(henri, filePath);
    }

    /**
     * Similar to {@link #saveHenri(ReadOnlyHenri)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveHenri(ReadOnlyHenri henri, Path filePath) throws IOException {
        requireNonNull(henri);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableHenri(henri), filePath);
    }

}
