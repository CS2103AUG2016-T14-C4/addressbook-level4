package guitests;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Meier", td.laundry, td.daniel); //multiple results
        
        assertFindResult("find t/urgent", td.laundry); //find by tag
        
        commandBox.runCommand("delete 1"); //find after deleting one result
        assertFindResult("find Meier",td.daniel);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
        
        assertFindResult("find t/friends"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
