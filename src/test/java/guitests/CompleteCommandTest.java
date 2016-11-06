//@@author A0153467Y
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.testutil.TestTask;

public class CompleteCommandTest extends TaskManagerGuiTest {
    private TestTask[] currentList;

    @Before
    public void runOnceBeforeClass() {
        currentList = td.getTypicalTasks();
    }

    /**
     * Complete tests -- Task ID is guaranteed the last one due to preservation of 
     * total ordering within completed tasks.
     */
    @Test
    public void complete() {
        int targetIndex = 1;

        // mark the first task as complete
        commandBox.runCommand("complete " + targetIndex);
        ReadOnlyTask newTask = taskListPanel.getTask(currentList.length - 1);
        assertTrue(newTask.getComplete());

        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, newTask));

        // mark another task as complete
        targetIndex = 3;
        commandBox.runCommand("complete " + targetIndex);
        ReadOnlyTask otherTask = taskListPanel.getTask(currentList.length - 1);
        assertTrue(otherTask.getComplete());
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, otherTask));

        // mark the last task as complete
        targetIndex = currentList.length;
        commandBox.runCommand("complete " + targetIndex);
        newTask = taskListPanel.getTask(currentList.length - 1);
        assertTrue(newTask.getComplete());
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, newTask));
    }

    @Test
    public void invalidComplete() {
        // invalid index
        commandBox.runCommand("complete " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // mark at an empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("complete " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

}
