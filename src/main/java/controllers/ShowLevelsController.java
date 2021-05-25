package controllers;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import scenes.MenuScene;
import scenes.PlayScene;
import xmlmodels.Maze;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ShowLevelsController {

    @FXML
    private VBox levelsBox;

    @FXML
    public void initialize(){
        int numOfLevels = getNumOfLevels();

        for (int levelIndex = 0; levelIndex < numOfLevels; levelIndex++){
            String levelName = "Level_" + (levelIndex + 1);
            Button currentLevelButton = new Button(levelName + getEndSymbol(levelName));
            currentLevelButton.setFont(new Font(48));
            int finalLevelIndex = levelIndex;
            currentLevelButton.setOnAction(event -> loadLevel(finalLevelIndex + 1));
            levelsBox.getChildren().add(currentLevelButton);
        }
    }

    private void loadLevel(int levelIndex) {
        try {
            Stage currentStage = (Stage) levelsBox.getScene().getWindow();
            PlayScene playScene = new PlayScene("Level_" + levelIndex);

            Stage playStage = new Stage();
            playStage.setMaximized(true);
            playStage.setTitle("Level " + levelIndex);
            playStage.setScene(playScene.getScene());

            currentStage.close();
            playStage.show();

        } catch (IOException | ParserConfigurationException | SAXException | JAXBException e) {
            e.printStackTrace();
        }
    }

    private String getEndSymbol(String levelName){

        try{
            var jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
                    .createContext(new Class[]{Maze.class}, null);
            Unmarshaller jaxbUnmarshall = jaxbContext.createUnmarshaller();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File( "src/main/resources/mazes/" + levelName + ".xml"));

            Maze maze = (Maze) jaxbUnmarshall.unmarshal(document);
            if (maze.getCompleted().equals("yes"))
                return "\u2714";
            return "\u274C";
        } catch (IOException | ParserConfigurationException | SAXException | JAXBException e) {
            e.printStackTrace();
        }
        return "";
    }

    private int getNumOfLevels() {
        File directory=new File("src/main/resources/mazes/");
        return Objects.requireNonNull(directory.list()).length;
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
