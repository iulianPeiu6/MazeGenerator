package scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class ShowLevelsScene {
    public Scene getScene(){
        try {
            return FXMLLoader.load(getClass().getResource("..\\views\\showLevels.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}
