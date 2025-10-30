package seedu.henri.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.henri.commons.util.ToStringBuilder;
import seedu.henri.logic.commands.exceptions.CommandException;
import seedu.henri.model.Model;
import seedu.henri.model.person.Person;

/**
 * Sets the salary for a person.
 */
public final class SetSalaryCommand extends Command {
    public static final String COMMAND_WORD = "set-salary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the salary for a person. "
            + "Parameters: <PERSON_ID> <SALARY>\n"
            + "Example: " + COMMAND_WORD + " "
            + "E12046 3670\n";

    public static final String MESSAGE_SUCCESS = "Set salary %1$.2f for: %2$s";
    public static final String MESSAGE_NON_EXISTENT_PERSON = "The person does not exist!";


    private final String toSet;
    private final int salaryInCents;

    /**
     * Creates an SetSalaryCommand for the specified {@code Person}
     */
    public SetSalaryCommand(String personId, int salaryInCents) {
        requireNonNull(personId);
        toSet = personId;
        this.salaryInCents = salaryInCents;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person person = model.find(p -> p.id().equals(toSet));
        model.deletePerson(person);
        model.addPerson(person.duplicate(toSet).withSalary(salaryInCents).build());
        return new CommandResult(String.format(MESSAGE_SUCCESS, salaryInCents / 100.0, person.id()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetSalaryCommand command)) {
            return false;
        }

        return command.toSet.equals(this.toSet) && command.salaryInCents == this.salaryInCents;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toSet", toSet)
                .add("salaryInCents", salaryInCents)
                .toString();
    }
}
