package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import scenes.GenerateMazeScene;
import scenes.PlayModeScene;

public class MenuController {

    @FXML
    private Button playButton;

    @FXML
    private Button generateButton;

    @FXML
    private Button exitButton;

    @FXML
    public void exitButtonPressed(ActionEvent event) {
        Stage currentStage = (Stage) generateButton.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void generateButtonPressed(ActionEvent event) {
        Stage currentStage = (Stage) generateButton.getScene().getWindow();
        currentStage.hide();

        Stage generateMazeStage = new Stage();
        generateMazeStage.setMaximized(true);
        generateMazeStage.setTitle("GenerateMaze");
        generateMazeStage.setScene(GenerateMazeScene.getScene());
        generateMazeStage.show();

    }

    @FXML
    public void playButtonPressed(ActionEvent event) {
        Stage currentStage = (Stage) generateButton.getScene().getWindow();
        currentStage.close();

        Stage playModeStage = new Stage();
        playModeStage.setMaximized(true);
        playModeStage.setTitle("Play Mode");
        playModeStage.setScene(PlayModeScene.getScene());
        playModeStage.show();
    }

}
