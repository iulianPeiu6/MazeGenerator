package controllers;

import jakarta.xml.bind.JAXBContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mazebuilders.CellBuilder;
import mazebuilders.CoordinateBuilder;
import mazebuilders.MazeBuilder;
import scenes.PlayScene;
import scenes.ShowLevelsScene;
import xmlmodels.Maze;

import javax.xml.bind.Marshaller;
import java.io.File;

public class PlayController {

    @FXML
    private Canvas mazeCanvas;

    private GraphicsContext graphicsContext;

    private CoordinateBuilder currentCoordinate, finish;

    @FXML
    public void initialize(){
        graphicsContext = mazeCanvas.getGraphicsContext2D();
        drawMaze();

        currentCoordinate =  mazeBuilder.getStart();

        finish = new CoordinateBuilder();
        finish.x = (int) (Math.random() * mazeBuilder.getWidth());
        finish.y = (int) (Math.random() * mazeBuilder.getHeight());
        drawFinish();

        graphicsContext.setFill(Color.GREEN);
        drawPlayer();

        mazeCanvas.getScene().addEventHandler(KeyEvent.ANY, this::move);
    }

    private MazeBuilder mazeBuilder;

    private void drawFinish(){
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(
                finish.x * mazeBuilder.getCellDimension() + mazeBuilder.getCellDimension()/4,
                finish.y * mazeBuilder.getCellDimension() + mazeBuilder.getCellDimension()/4,
                mazeBuilder.getCellDimension()/2,
                mazeBuilder.getCellDimension()/2
        );

    }

    private void drawPlayer() {
        graphicsContext.fillRect(
                currentCoordinate.x * mazeBuilder.getCellDimension() + mazeBuilder.getCellDimension()/4,
                currentCoordinate.y * mazeBuilder.getCellDimension() + mazeBuilder.getCellDimension()/4,
                mazeBuilder.getCellDimension()/2,
                mazeBuilder.getCellDimension()/2
        );
        if (currentCoordinate.equals(finish)) {
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("You won!");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
            reSave();
            back();
        }
    }

    private void drawMaze() {
        mazeBuilder = new MazeBuilder(PlayScene.maze);
        drawMazeSkeleton();
        for (int i = 0; i < mazeBuilder.getWidth(); i++)
            for (int j = 0; j < mazeBuilder.getHeight(); j++)
                drawCell(mazeBuilder.getCellBuilders()[i][j], new CoordinateBuilder(i, j));
    }

    private void drawMazeSkeleton() {
        graphicsContext.setStroke(Color.valueOf("#3e3553"));
        graphicsContext.setLineWidth(5);
        graphicsContext.fillRect(0, 0, mazeCanvas.getWidth(), mazeCanvas.getHeight());

        for (int i = 0; i < mazeBuilder.getHeight(); i++)
            graphicsContext.strokeLine(0, i * mazeBuilder.getCellDimension(),
                    mazeCanvas.getWidth(), i * mazeBuilder.getCellDimension());

        for (int i = 0; i < mazeBuilder.getWidth(); i++)
            graphicsContext.strokeLine(i * mazeBuilder.getCellDimension(), 0,
                    i * mazeBuilder.getCellDimension(), mazeCanvas.getHeight());
    }

    private void drawCell(CellBuilder cellBuilder, CoordinateBuilder coordinateBuilder) {
        final double lineWidth = 5;
        graphicsContext.setLineWidth(lineWidth);

        setProperWallStrokeColor(cellBuilder, "top");
        graphicsContext.strokeLine(coordinateBuilder.x * mazeBuilder.getCellDimension() + lineWidth,
                coordinateBuilder.y * mazeBuilder.getCellDimension(),
                (coordinateBuilder.x + 1) * mazeBuilder.getCellDimension() - lineWidth,
                coordinateBuilder.y * mazeBuilder.getCellDimension());

        setProperWallStrokeColor(cellBuilder, "bottom");
        graphicsContext.strokeLine(coordinateBuilder.x * mazeBuilder.getCellDimension() + lineWidth,
                (coordinateBuilder.y + 1) * mazeBuilder.getCellDimension(),
                (coordinateBuilder.x + 1) * mazeBuilder.getCellDimension() - lineWidth,
                (coordinateBuilder.y + 1) * mazeBuilder.getCellDimension());

        setProperWallStrokeColor(cellBuilder, "left");
        graphicsContext.strokeLine(coordinateBuilder.x * mazeBuilder.getCellDimension(),
                coordinateBuilder.y * mazeBuilder.getCellDimension() + lineWidth,
                coordinateBuilder.x * mazeBuilder.getCellDimension(),
                (coordinateBuilder.y + 1) * mazeBuilder.getCellDimension() - lineWidth);

        setProperWallStrokeColor(cellBuilder, "right");
        graphicsContext.strokeLine((coordinateBuilder.x + 1) * mazeBuilder.getCellDimension(),
                coordinateBuilder.y * mazeBuilder.getCellDimension() + lineWidth,
                (coordinateBuilder.x + 1) * mazeBuilder.getCellDimension(),
                (coordinateBuilder.y + 1) * mazeBuilder.getCellDimension() - lineWidth);

        graphicsContext.setFill(Color.valueOf("#e2a65e"));
        graphicsContext.fillRect(coordinateBuilder.x * mazeBuilder.getCellDimension() + 2.5,
                coordinateBuilder.y * mazeBuilder.getCellDimension() + 2.5,
                mazeBuilder.getCellDimension() - 5,
                mazeBuilder.getCellDimension() - 5);
    }

    private void setProperWallStrokeColor(CellBuilder cellBuilder, String wall) {
        if (cellBuilder.wallExists(wall))
            graphicsContext.setStroke(Color.valueOf("#3e3553"));
        else
            graphicsContext.setStroke(Color.valueOf("#e2a65e"));
    }

    @FXML
    void back() {
        Stage currentStage = (Stage) mazeCanvas.getScene().getWindow();
        currentStage.hide();

        Stage playModeStage = new Stage();
        playModeStage.setMaximized(true);
        playModeStage.setTitle("Play Mode");
        playModeStage.setScene(new ShowLevelsScene().getScene());
        playModeStage.show();
    }

    @FXML
    void move(KeyEvent event) {
        var keyCode = event.getCode();
        switch( keyCode ) {
            case LEFT :
                moveLeft();
                break;
            case UP :
                moveUp();
                break;
            case RIGHT :
                moveRight();
                break;
            case DOWN :
                moveDown();
                break;
        }
    }

    private void moveDown() {
        Boolean canMove = !mazeBuilder
                .getCellBuilders()[currentCoordinate.x][currentCoordinate.y]
                .wallExists("bottom");
        if (canMove){
            currentCoordinate.y++;
                graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(
                    currentCoordinate.x * mazeBuilder.getCellDimension() + mazeBuilder.getCellDimension()/4,
                    currentCoordinate.y * mazeBuilder.getCellDimension() - mazeBuilder.getCellDimension()/4,
                    mazeBuilder.getCellDimension()/2,
                    mazeBuilder.getCellDimension()/2
            );
            drawPlayer();
        }

    }

    private void moveRight() {
        Boolean canMove = !mazeBuilder
                .getCellBuilders()[currentCoordinate.x][currentCoordinate.y]
                .wallExists("right");
        if (canMove){
            currentCoordinate.x++;
            graphicsContext.fillRect(
                    currentCoordinate.x * mazeBuilder.getCellDimension() - mazeBuilder.getCellDimension()/4,
                    currentCoordinate.y * mazeBuilder.getCellDimension() + mazeBuilder.getCellDimension()/4,
                    mazeBuilder.getCellDimension()/2,
                    mazeBuilder.getCellDimension()/2
            );
            drawPlayer();
        }
    }

    private void moveLeft() {
        Boolean canMove = !mazeBuilder
                .getCellBuilders()[currentCoordinate.x][currentCoordinate.y]
                .wallExists("left");

        if (canMove){
            currentCoordinate.x--;
            graphicsContext.fillRect(
                    currentCoordinate.x * mazeBuilder.getCellDimension() + 3*mazeBuilder.getCellDimension()/4,
                    currentCoordinate.y * mazeBuilder.getCellDimension() + mazeBuilder.getCellDimension()/4,
                    mazeBuilder.getCellDimension()/2,
                    mazeBuilder.getCellDimension()/2
            );
            drawPlayer();
        }
    }

    private void moveUp() {
        Boolean canMove = !mazeBuilder
                .getCellBuilders()[currentCoordinate.x][currentCoordinate.y]
                .wallExists("top");

        if (canMove){
            currentCoordinate.y--;

            graphicsContext.fillRect(
                    currentCoordinate.x * mazeBuilder.getCellDimension() + mazeBuilder.getCellDimension()/4,
                    currentCoordinate.y * mazeBuilder.getCellDimension() + 3*mazeBuilder.getCellDimension()/4,
                    mazeBuilder.getCellDimension()/2,
                    mazeBuilder.getCellDimension()/2
            );
            drawPlayer();
        }
    }

    public void reSave() {
        if (mazeBuilder == null)
            return ;
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
                    .createContext(new Class[]{Maze.class}, null);

            var jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PlayScene.maze.setCompleted("yes");
            String filename = "Level" + "_" + PlayScene.maze.getId() + ".xml";
            jaxbMarshaller.marshal(PlayScene.maze, new File("src/main/resources/mazes/" + filename));

        } catch (jakarta.xml.bind.JAXBException e) {
            e.printStackTrace();
        }
    }
}
