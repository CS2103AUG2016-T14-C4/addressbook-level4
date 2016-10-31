//@@author A0153467Y-reused
package seedu.task.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ListView;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.ui.TaskListPanel.TaskListViewCell;


/**
 * Controller for a help page
 */
public class HelpPane extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpPane.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    
    private VBox panel;
    private AnchorPane placeHolderPane;
    
    @FXML
    private ListView<String> commandList;

    public HelpPane() {
        super();
    }
    
    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane= pane;
    }
    
    public static HelpPane load (Stage primaryStage, AnchorPane helpPlaceholder, ObservableList<String> helpInstruction) {
        logger.fine("Showing help page about the application.");
        HelpPane helpPane =
                UiPartLoader.loadUiPart(primaryStage, helpPlaceholder, new HelpPane());
        helpPane.configure(helpInstruction);
        return helpPane;
    }
    

    private void configure(ObservableList<String> helpInstruction){
        setConnections(helpInstruction);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<String> helpInstruction) {
        /*TableColumn<String, String> commandWord = new TableColumn<String, String> ("Command Word");
        //commandWord.setMinWidth(150);
        commandWord.setCellFactoryProperty(new PropertyValueFactory<String, String>helpCommandWord);
        TableColumn<String, String> commandUsage = new TableColumn<String,String> ("Command Usage");
        //commandUsage.setMinWidth(value);
        //helpTable.setItems();*/
        commandList.setItems(helpInstruction);
        
    }
    
    private void addToPlaceholder() {
        //SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }
}
