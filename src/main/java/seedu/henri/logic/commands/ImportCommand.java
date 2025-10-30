package seedu.henri.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.henri.commons.core.LogsCenter;
import seedu.henri.commons.exceptions.DataLoadingException;
import seedu.henri.commons.util.ToStringBuilder;
import seedu.henri.logic.commands.exceptions.CommandException;
import seedu.henri.model.Model;
import seedu.henri.model.ReadOnlyHenri;
import seedu.henri.model.person.Person;
import seedu.henri.model.util.SampleDataUtil;
import seedu.henri.storage.JsonHenriStorage;
import seedu.henri.storage.StorageManager;

/**
 * Imports a custom set of contacts stored in JSON format into the current Henri
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_IMPORT_SUCCESS = "Imported contacts from JSON file";
    public static final String MESSAGE_INVALID_PATH = "Invalid file path provided";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports contact data from a specific JSON file from the data folder into the main Henri.\n"
            + "Parameters: FILENAME (must be a valid file with .json extension)\n"
            + "Example: " + COMMAND_WORD + " friends.json";
    private static final Logger storageLogger = LogsCenter.getLogger(StorageManager.class);

    private final Path filePath;

    public ImportCommand(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Copies the data from the given path as Person objects into main Henri
     * @param model {@code Model} which the command should operate on.
     * @return the status message of the operation
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        JsonHenriStorage tempBookStorage = new JsonHenriStorage(filePath);
        try {
            Optional<ReadOnlyHenri> importedHenriOptional = tempBookStorage.readHenri();
            ReadOnlyHenri importedData = importedHenriOptional
                    .orElseGet(SampleDataUtil::getSampleHenri);
            List<Person> importedPersons = importedData.getPersonList();
            this.addImportedPersons(model, importedPersons);
        } catch (DataLoadingException e) {
            throw new CommandException(MESSAGE_INVALID_PATH);
        }

        return new CommandResult(MESSAGE_IMPORT_SUCCESS);
    }

    /**
     * Helper function to add a list of Person into model
     * @param model {@code Model} which the command should operate on.
     * @param list a list of Person objects
     */
    private void addImportedPersons(Model model, List<Person> list) {
        // Add each imported person into the model iteratively.
        for (Person person : list) {
            try {
                model.addPerson(person);
            } catch (Exception ex) {
                storageLogger.info("Skipping person during import: " + person + " (" + ex.getMessage() + ")");
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        ImportCommand otherImportCommand = (ImportCommand) other;
        return filePath.equals(otherImportCommand.filePath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filePath", filePath)
                .toString();
    }
}
