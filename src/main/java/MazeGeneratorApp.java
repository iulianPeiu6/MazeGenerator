import javafx.application.Application;
import javafx.stage.Stage;
import scenes.MenuScene;

public class MazeGeneratorApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(MenuScene.getScene());
        primaryStage.setTitle("Menu");
        primaryStage.setMaximized(true);


        primaryStage.show();
    }
}
