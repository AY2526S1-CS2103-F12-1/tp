package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.StorageManager;

/**
 * Imports a custom set of contacts stored in JSON format into the current address book.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_IMPORT_SUCCESS = "Imported contacts from JSON file";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports contact data from a specific JSON file from the data folder into the main address book.\n"
            + "Parameters: FILENAME (must be a valid file with .json extension)\n"
            + "Example: " + COMMAND_WORD + " friends.json";
    private static final Logger storageLogger = LogsCenter.getLogger(StorageManager.class);

    private final Path filePath;

    public ImportCommand(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Copies the data from the given path as Person objects into main address book
     * @param model {@code Model} which the command should operate on.
     * @return the status message of the operation
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        JsonAddressBookStorage tempBookStorage = new JsonAddressBookStorage(filePath);
        try {
            Optional<ReadOnlyAddressBook> importedAddressBookOptional = tempBookStorage.readAddressBook();
            ReadOnlyAddressBook importedData = importedAddressBookOptional
                    .orElseGet(SampleDataUtil::getSampleAddressBook);
            List<Person> importedPersons = importedData.getPersonList();
            List<String> failedImports = this.addImportedPersons(model, importedPersons);
            return new CommandResult(MESSAGE_IMPORT_SUCCESS
                    + (!failedImports.isEmpty() ? (".\nExisting contact imports skipped: " + failedImports) : ""));
        } catch (DataLoadingException e) {
            String errorMsg = e.getMessage().substring(e.getMessage().indexOf(':') + 1).trim();
            throw new CommandException("An error was found in the JSON data you are trying to import:\n" + errorMsg);
        }
    }

    /**
     * Helper function to add a list of Person into model
     * @param model {@code Model} which the command should operate on.
     * @param list a list of Person objects
     */
    private List<String> addImportedPersons(Model model, List<Person> list) {
        Set<Long> usedIds = model.getAddressBook().getPersonList().stream()
                .map(Person::id)
                .filter(id -> id.startsWith("E"))
                .map(id -> Long.parseLong(id.substring(1)))
                .collect(Collectors.toSet());

        // Add each imported person into the model iteratively.
        List<String> skippedPersons = new ArrayList<>();
        for (Person person : list) {
            try {
                long numericId = Long.parseLong(person.id().substring(1));
                if (usedIds.contains(numericId)) {
                    long newId = AddCommand.findNextAvailableId(usedIds);
                    Person personWithId = new Person(String.format("E%04d", newId),
                            person.name(), person.phone(), person.email(),
                            person.address(), person.gitHubUsername(), person.tags());
                    model.addPerson(personWithId);
                    usedIds.add(newId);
                } else {
                    model.addPerson(person);
                    usedIds.add(numericId);
                }
            } catch (Exception ex) {
                skippedPersons.add(person.id());
                storageLogger.info("Skipping person during import: " + person + " (" + ex.getMessage() + ")");
            }
        }
        return skippedPersons;
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
