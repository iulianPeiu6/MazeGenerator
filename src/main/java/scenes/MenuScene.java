package scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuScene {

    private static Scene menuScene = null;

    private MenuScene() {
        menuScene = null;

        try {
            menuScene = FXMLLoader.load(getClass().getResource("..\\views\\menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getScene(){
        if (menuScene == null)
            new MenuScene();

        return menuScene;
    }
}
