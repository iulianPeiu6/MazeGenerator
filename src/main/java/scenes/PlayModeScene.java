package scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class PlayModeScene {
    private static Scene playModeScene = null;

    private PlayModeScene() {
        playModeScene = null;

        try {
            playModeScene = FXMLLoader.load(getClass().getResource("..\\views\\playMode.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getScene(){
        if (playModeScene == null)
            new PlayModeScene();

        return playModeScene;
    }
}
