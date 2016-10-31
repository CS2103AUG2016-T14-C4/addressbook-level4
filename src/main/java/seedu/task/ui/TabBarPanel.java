//@@author A0141052Y
package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TabBarPanel extends UiPart {
    private static final String FXML = "TabBarPanel.fxml";
    
    @FXML
    private HBox tabBarPanel;
    
    public static TabBarPanel load(Stage primaryStage, AnchorPane tabBarPlaceholder) {
        TabBarPanel tabBarPanel = UiPartLoader.loadUiPart(primaryStage, tabBarPlaceholder, new TabBarPanel());
        
        return tabBarPanel;
    }

    @Override
    public void setNode(Node node) {
        tabBarPanel = (HBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

}
