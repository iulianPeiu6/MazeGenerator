import javafx.application.Application;
import javafx.stage.Stage;
import scenes.MenuStage;

public class MazeGenerator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(MenuStage.getScene());
        primaryStage.setTitle("Menu");
        primaryStage.setMaximized(true);


        primaryStage.show();
    }
}
