package seedu.henri.logic.commands;

import static seedu.henri.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.henri.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.henri.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.henri.testutil.TypicalPersons.getTypicalHenri;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.henri.model.Model;
import seedu.henri.model.ModelManager;
import seedu.henri.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHenri(), new UserPrefs());
        expectedModel = new ModelManager(model.getHenri(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
