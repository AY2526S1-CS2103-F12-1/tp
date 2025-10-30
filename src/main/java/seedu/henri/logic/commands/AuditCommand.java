package seedu.henri.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.henri.commons.util.ToStringBuilder;
import seedu.henri.model.Model;
import seedu.henri.model.audit.AuditLog;
import seedu.henri.model.audit.AuditLogEntry;

/**
 * Displays the audit log of all past actions.
 */
public class AuditCommand extends Command {

    public static final String COMMAND_WORD = "audit";
    public static final String MESSAGE_SUCCESS = "Audit Log:\n%s";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        AuditLog auditLog = model.getAuditLog();
        List<AuditLogEntry> entries = auditLog.getEntries();

        if (entries.isEmpty()) {
            return new CommandResult("No audit log entries found.");
        }

        StringBuilder result = new StringBuilder("Audit Log:\n");
        for (AuditLogEntry entry : entries) {
            result.append(entry.toString()).append("\n");
        }

        return new CommandResult(result.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return other instanceof AuditCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
