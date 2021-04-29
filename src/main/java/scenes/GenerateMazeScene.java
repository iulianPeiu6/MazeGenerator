package scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class GenerateMazeScene {
    private static Scene generateMazeScene = null;

    private GenerateMazeScene() {
        generateMazeScene = null;

        try {
            generateMazeScene = FXMLLoader.load(getClass().getResource("..\\views\\generateMaze.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Scene getScene(){
        if (generateMazeScene == null)
            new GenerateMazeScene();

        return generateMazeScene;
    }
}
