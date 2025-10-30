package seedu.henri.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.henri.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.henri.logic.commands.SetSalaryCommand.MESSAGE_SUCCESS;
import static seedu.henri.testutil.TypicalPersons.getTypicalHenri;

import org.junit.jupiter.api.Test;

import seedu.henri.model.Model;
import seedu.henri.model.ModelManager;
import seedu.henri.model.UserPrefs;

class SetSalaryCommandTest {
    private Model model = new ModelManager(getTypicalHenri(), new UserPrefs());

    @Test
    void execute_validIdAndSalary_success() {
        SetSalaryCommand command = new SetSalaryCommand("E0001", 10000);
        assertCommandSuccess(command, model, String.format(MESSAGE_SUCCESS, 100.0, "E0001"), model);
    }

    @Test
    void equals() {
        SetSalaryCommand command = new SetSalaryCommand("E12345", 100);
        SetSalaryCommand sameCommand = new SetSalaryCommand("E12345", 100);
        SetSalaryCommand differentCommand = new SetSalaryCommand("E12345", 200);
        SetSalaryCommand anotherDifferentCommand = new SetSalaryCommand("E12346", 100);
        assertEquals(command, sameCommand);
        assertNotEquals(command, differentCommand);
        assertNotEquals(command, anotherDifferentCommand);
    }

    @Test
    void toString_method() {
        SetSalaryCommand command = new SetSalaryCommand("E12345", 100);
        assertEquals("seedu.henri.logic.commands.SetSalaryCommand{toSet=E12345, salaryInCents=100}",
                     command.toString());
    }
}
