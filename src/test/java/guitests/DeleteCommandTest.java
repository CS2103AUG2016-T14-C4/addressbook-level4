package guitests;

import org.junit.Test;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

public class DeleteCommandTest extends AddressBookGuiTest {

    @Test
    public void delete() {

        TestTask[] currentList = td.getTypicalTasks();

        // delete a task with specific name
        assertDeleteSuccess(TypicalTestTasks.fiona.getName().taskName, currentList);
        currentList = TestUtil.removeTasksFromList(currentList, TypicalTestTasks.fiona);

        //delete the first in the list
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    /**
     * Runs the delete command to delete the task with the specified targetName and confirms the result is correct.
     * @param targetName e.g. to delete the task named "do work", "do work" should be given as targetName
     * @param currentList A copy of the current list of tasks (before deletion)
     */
    private void assertDeleteSuccess(String targetName, final TestTask[] currentList) {
        /** flag to determine if targetName matches multiple entries */
        boolean multiMatch = false;
        /** task object that matches targetName*/
        TestTask taskToDelete = null;

        // determine if multi match and search for task matching with targetName
        for(TestTask tt: currentList) {
            if(tt.getName().taskName.contentEquals(targetName)) {
                if(taskToDelete == null) {
                    taskToDelete = tt;
                } else {
                    multiMatch = true;
                    break;
                }
            }
        }
        System.out.println(taskToDelete);
        // generate expectedRemainder
        TestTask[] expectedRemainder = multiMatch ?
                currentList :
                TestUtil.removeTasksFromList(currentList, taskToDelete);

        // run deletion command
        commandBox.runCommand("delete " + targetName);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
