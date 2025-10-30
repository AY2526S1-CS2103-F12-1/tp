package seedu.henri.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.henri.commons.core.GuiSettings;
import seedu.henri.commons.core.LogsCenter;
import seedu.henri.logic.commands.Command;
import seedu.henri.logic.commands.CommandResult;
import seedu.henri.logic.commands.exceptions.CommandException;
import seedu.henri.logic.parser.AddCommandParser;
import seedu.henri.logic.parser.AddressBookParser;
import seedu.henri.logic.parser.exceptions.ParseException;
import seedu.henri.model.Model;
import seedu.henri.model.ReadOnlyHenri;
import seedu.henri.model.person.Person;
import seedu.henri.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private static final String AUDIT_ACTION = "AUDIT";
    private static final String EXIT_ACTION = "EXIT";
    private static final String HELP_ACTION = "HELP";
    private static final String LIST_ACTION = "LIST";
    private static final String VIEW_ACTION = "VIEW";
    private static final String FIND_ACTION = "FIND";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();

        // In the LogicManager constructor, after model initialization
        List<Person> persons = model.getAddressBook().getPersonList();
        if (!persons.isEmpty()) {
            long maxId = persons.stream()
                    .map(Person::id)
                    .filter(id -> id.startsWith("E"))
                    .mapToLong(id -> Long.parseLong(id.substring(1)))
                    .max()
                    .orElse(0);
            AddCommandParser.setNextId(maxId + 1);
        }

    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        // Only log commands that modify state
        if (shouldLogCommand(command)) {
            String action = extractAction(command);
            String details = generateDetails(commandResult);
            model.getAuditLog().addEntry(action, details, LocalDateTime.now());
        }

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyHenri getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    /**
     * Extracts the action type from the given command.
     *
     * @param command The command executed.
     * @return The action type as a string.
     */
    private String extractAction(Command command) {
        try {
            java.lang.reflect.Field field = command.getClass().getField("COMMAND_WORD");
            return ((String) field.get(null)).toUpperCase();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Fallback to class name if COMMAND_WORD field doesn't exist
            logger.warning("Could not access COMMAND_WORD for " + command.getClass().getSimpleName());
            String className = command.getClass().getSimpleName();
            return className.replace("Command", "").toUpperCase();
        }
    }

    /**
     * Generates meaningful details for the audit log based on the command and its result.
     * @param result    The result of the command execution
     * @return  String of details for the audit log
     */
    private String generateDetails(CommandResult result) {
        return result.getFeedbackToUser();
    }

    /**
     * Determines if a command should be logged in the audit log.
     * Only commands that modify state are logged.
     *
     * @param command The command to check.
     * @return true if the command should be logged, false otherwise.
     */
    private boolean shouldLogCommand(Command command) {
        String action = extractAction(command);
        // Exclude read-only commands
        return !action.equals(AUDIT_ACTION)
                && !action.equals(EXIT_ACTION)
                && !action.equals(HELP_ACTION)
                && !action.equals(LIST_ACTION)
                && !action.equals(VIEW_ACTION)
                && !action.equals(FIND_ACTION);
    }

    @Override
    public String getOrganizationHierarchyString() {
        return model.getOrganizationHierarchyString();
    }
}
