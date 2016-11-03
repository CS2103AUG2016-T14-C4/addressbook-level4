//@@author A0144939R
package guitests;


import java.io.File;
import org.junit.Test;
import seedu.task.logic.commands.ChangePathCommand;
import seedu.task.testutil.TestUtil;

public class ChangePathCommandTest extends TaskManagerGuiTest {
    @Test
    public void changePath() throws InterruptedException {       
        
        //Try with non xml file
        String nonXmlFilePath = TestUtil.getFilePathInSandboxFolder("taskmanager.txt");
        commandBox.runCommand("change-to " + nonXmlFilePath);
        assertResultMessage(String.format(ChangePathCommand.MESSAGE_PATH_CHANGE_FAIL, nonXmlFilePath));
        
        //Try with unwritable file path
        String unWriteableFilePath = TestUtil.getFilePathInSandboxFolder("unwritable.xml");
        File unWriteableFolder = new File(unWriteableFilePath).getParentFile();
        unWriteableFolder.setWritable(false);
        Thread.sleep(300);
        commandBox.runCommand("change-to " + unWriteableFilePath);
        assertResultMessage(String.format(ChangePathCommand.MESSAGE_PATH_CHANGE_FAIL, unWriteableFilePath));
        unWriteableFolder.setWritable(true);
        Thread.sleep(300);
        
        //Try with empty String
        String emptyPath = TestUtil.getFilePathInSandboxFolder("");
        commandBox.runCommand("change-to "+emptyPath);
        assertResultMessage(String.format(ChangePathCommand.MESSAGE_PATH_CHANGE_FAIL, emptyPath));

    }
}
