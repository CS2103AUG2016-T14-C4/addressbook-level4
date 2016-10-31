package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Provides a handle to the help window of the app.
 */
public class HelpWindowHandle extends GuiHandle {

    private static final String HELP_WINDOW_TITLE = "Help";
    private static final String HELP_WINDOW_ROOT_FIELD_ID = "#helpWindowRoot";
    
    private static final String HELP_LIST_VIEW_ID = "#commandList";

    public HelpWindowHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage,null);//, HELP_WINDOW_TITLE);
        guiRobot.sleep(1000);
    }

    public ListView<ReadOnlyTask> getListView() {
        return (ListView<ReadOnlyTask>) getNode(HELP_LIST_VIEW_ID);
    }
    
    public boolean isWindowOpen() {
        return getNode(HELP_WINDOW_ROOT_FIELD_ID) != null;
    }

    public void closeWindow() {
        super.closeWindow();
        guiRobot.sleep(500);
    }

}
