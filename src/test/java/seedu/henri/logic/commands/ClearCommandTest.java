package seedu.henri.logic.commands;

import static seedu.henri.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.henri.testutil.TypicalPersons.getTypicalHenri;

import org.junit.jupiter.api.Test;

import seedu.henri.model.Henri;
import seedu.henri.model.Model;
import seedu.henri.model.ModelManager;
import seedu.henri.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyHenri_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyHenri_success() {
        Model model = new ModelManager(getTypicalHenri(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalHenri(), new UserPrefs());
        expectedModel.setHenri(new Henri());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
