import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class MazeGenerator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("layouts\\menu.fxml"));
            root.getStylesheets().add(getClass().getResource("stylesheets\\menu.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene initialScene = new Scene(root);
        primaryStage.setScene(initialScene);
        primaryStage.show();
    }
}
