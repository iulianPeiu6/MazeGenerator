package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import scenes.GenerateMazeScene;
import scenes.ShowLevelsScene;

public class MenuController {

    @FXML
    private Button generateButton;

    @FXML
    public void exitButtonPressed() {
        Stage currentStage = (Stage) generateButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void generateButtonPressed() {
        Stage currentStage = (Stage) generateButton.getScene().getWindow();
        currentStage.hide();

        Stage generateMazeStage = new Stage();
        generateMazeStage.setMaximized(true);
        generateMazeStage.setTitle("GenerateMaze");
        generateMazeStage.setScene(GenerateMazeScene.getScene());
        generateMazeStage.show();

    }

    @FXML
    public void playButtonPressed() {
        Stage currentStage = (Stage) generateButton.getScene().getWindow();
        currentStage.hide();

        Stage playModeStage = new Stage();
        playModeStage.setMaximized(true);
        playModeStage.setTitle("Play Mode");
        playModeStage.setScene(new ShowLevelsScene().getScene());
        playModeStage.show();
    }

}
