package controllers;

import jakarta.xml.bind.JAXBException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import scenes.MenuScene;
import scenes.PlayScene;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ShowLevelsController {

    @FXML
    private VBox levelsBox;

    public ShowLevelsController() {
    }

    @FXML
    public void initialize(){
        int numOfLevels = getNumOfLevels();

        for (int levelIndex = 0; levelIndex < numOfLevels; levelIndex++){
            Button currentLevelButton = new Button("Level " + (levelIndex + 1));
            currentLevelButton.setFont(new Font(48));
            int finalLevelIndex = levelIndex + 1;
            currentLevelButton.setOnAction(event -> {
                try {
                    Stage currentStage = (Stage) levelsBox.getScene().getWindow();
                    PlayScene playScene = new PlayScene("Level_" + finalLevelIndex);

                    Stage playStage = new Stage();
                    playStage.setMaximized(true);
                    playStage.setTitle("Level " + finalLevelIndex);
                    playStage.setScene(playScene.getScene());

                    currentStage.close();
                    playStage.show();

                } catch (IOException | ParserConfigurationException | SAXException | JAXBException e) {
                    e.printStackTrace();
                }
            });
            levelsBox.getChildren().add(currentLevelButton);
        }
    }

    private int getNumOfLevels() {
        File directory=new File("src/main/resources/mazes/");
        int fileCount= Objects.requireNonNull(directory.list()).length;
        return fileCount;
    }

    @FXML
    void back() {
        Stage currentStage = (Stage) levelsBox.getScene().getWindow();
        currentStage.close();

        Stage menuStage = new Stage();
        menuStage.setMaximized(true);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(MenuScene.getScene());
        menuStage.show();
    }

}
