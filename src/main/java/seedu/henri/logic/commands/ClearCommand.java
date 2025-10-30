package seedu.henri.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.henri.model.Henri;
import seedu.henri.model.Model;

/**
 * Clears the henri.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Henri has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        int personCount = model.getHenri().getPersonList().size();
        model.setHenri(new Henri());
        model.addAuditEntry("CLEAR", String.format("Cleared all data (%d persons)", personCount));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
