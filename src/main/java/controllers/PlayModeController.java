package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import scenes.MenuScene;

public class PlayModeController {

    @FXML
    private Button classicModeButton;

    @FXML
    private Button combatModeButton;

    @FXML
    private Button timerModeButton;

    @FXML
    private Button backButton;

    @FXML
    void back(ActionEvent event) {
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();

        Stage menuStage = new Stage();
        menuStage.setMaximized(true);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(MenuScene.getScene());
        menuStage.show();
    }

}
