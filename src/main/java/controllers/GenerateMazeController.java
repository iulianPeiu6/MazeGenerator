package controllers;

import jakarta.xml.bind.JAXBContext;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import mazebuilders.CellBuilder;
import mazebuilders.CoordinateBuilder;
import mazebuilders.MazeBuilder;
import scenes.MenuScene;
import xmlmodels.Cell;
import xmlmodels.Coordinate;
import xmlmodels.Maze;
import xmlmodels.Walls;

import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GenerateMazeController {

    @FXML
    private Canvas mazeCanvas;

    @FXML
    private Button generateButton;

    @FXML
    private TextField cellDimensionTextArea;

    @FXML
    private Button backButton;

    @FXML
    private Button saveButton;

    private MazeBuilder mazeBuilder;
    private GraphicsContext graphicsContext;
    private double lineWidth;
    AtomicBoolean wasRun;

    public GenerateMazeController() {
        this.lineWidth = 5;
        mazeBuilder = null;
        wasRun = new AtomicBoolean(false);
    }
    @FXML
    public void initialize(){
        graphicsContext = mazeCanvas.getGraphicsContext2D();
    }

    @FXML
    public void back(ActionEvent event) {
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.hide();

        Stage menuStage = new Stage();
        menuStage.setMaximized(true);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(MenuScene.getScene());
        menuStage.show();
    }

    @FXML
    public void save(ActionEvent event) {
        if (mazeBuilder == null)
            return ;
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
                    .createContext(new Class[]{Maze.class}, null);

            var jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            int mazeId = getValidMazeId();
            Maze maze = new Maze(mazeId,mazeBuilder);

            jaxbMarshaller.marshal(maze, new File("src/main/resources/mazes/classic_" + mazeId + ".xml"));

        } catch (jakarta.xml.bind.JAXBException e) {
            e.printStackTrace();
        }

    }

    private int getValidMazeId() {
        // TODO
        return 1;
    }

    @FXML
    private synchronized void generateMaze() {
        if (wasRun.getAndSet(true))
            return;
        mazeBuilder = new MazeBuilder((int) mazeCanvas.getWidth(), (int) mazeCanvas.getHeight(), getCellDimension());
        mazeBuilder.setStart(new CoordinateBuilder((int) (Math.random() * mazeBuilder.getWidth()),
                (int) (Math.random() * mazeBuilder.getHeight())));

        List<CoordinateBuilder> cellsStack = new ArrayList<>();
        CoordinateBuilder current = mazeBuilder.getStart();
        cellsStack.add(current);
        mazeBuilder.setVisitedCell(current);
        double pauseTime = 0;

        while (!cellsStack.isEmpty()) {
            CoordinateBuilder unvisitedNeighbour = mazeBuilder.getRandomUnvisitedNeighbour(current);
            if (unvisitedNeighbour != null) {
                cellsStack.add(current);
                removeWallBetween(current.x, current.y, unvisitedNeighbour.x, unvisitedNeighbour.y);
                current = unvisitedNeighbour;
                mazeBuilder.setCurrent(current);
                mazeBuilder.setVisitedCell(current);
            } else {
                mazeBuilder.getCellBuilders()[current.x][current.y].setFinished();
                current = cellsStack.get(cellsStack.size() - 1);
                mazeBuilder.setCurrent(current);
                cellsStack.remove(cellsStack.size() - 1);
            }

            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(pauseTime += 0.0625));
            MazeBuilder finalMazeBuilder = new MazeBuilder(mazeBuilder);
            pauseTransition.setOnFinished(event -> drawMaze(finalMazeBuilder));
            pauseTransition.play();
        }

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(pauseTime));
        pauseTransition.setOnFinished(event -> wasRun.set(false));
        pauseTransition.play();
    }

    private int getCellDimension() {
        int cellDimension;
        try {
            cellDimension = Integer.parseInt(cellDimensionTextArea.getText());
        } catch (NumberFormatException e) {
            cellDimension = 100;
        }
        return cellDimension;
    }

    private void drawMaze(MazeBuilder mazeBuilder) {
        drawMazeSkeleton(mazeBuilder);
        for (int i = 0; i < mazeBuilder.getWidth(); i++)
            for (int j = 0; j < mazeBuilder.getHeight(); j++){
                if (mazeBuilder.getCurrent().x == i && mazeBuilder.getCurrent().y == j)
                    onCurrent = true;
                else
                    onCurrent = false;
                drawCell(mazeBuilder.getCellBuilders()[i][j], new CoordinateBuilder(i, j));
            }
    }

    private void drawMazeSkeleton(MazeBuilder mazeBuilder) {
        graphicsContext.setFill(Color.valueOf("#2f8daf"));
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.fillRect(0, 0, mazeCanvas.getWidth(), mazeCanvas.getHeight());

        for (int i = 0; i < mazeBuilder.getHeight(); i++)
            graphicsContext.strokeLine(0, i * mazeBuilder.getCellDimension(),
                    mazeCanvas.getWidth(), i * mazeBuilder.getCellDimension());

        for (int i = 0; i < mazeBuilder.getWidth(); i++)
            graphicsContext.strokeLine(i * mazeBuilder.getCellDimension(), 0,
                    i * mazeBuilder.getCellDimension(), mazeCanvas.getHeight());
    }

    private void drawCell(CellBuilder cellBuilder, CoordinateBuilder coordinateBuilder) {
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

        setProperCellFillColor(cellBuilder);
        graphicsContext.fillRect(coordinateBuilder.x * mazeBuilder.getCellDimension() + 2.5,
                coordinateBuilder.y * mazeBuilder.getCellDimension() + 2.5,
                mazeBuilder.getCellDimension() - 5,
                mazeBuilder.getCellDimension() - 5);
    }

    private Boolean onCurrent = false;

    private void setProperCellFillColor(CellBuilder cellBuilder) {
        if (onCurrent)
            graphicsContext.setFill(Color.valueOf("#cafa8c"));
        else if (cellBuilder.isFinished())
            graphicsContext.setFill(Color.valueOf("#5ee2a7"));
        else if (cellBuilder.isVisited()) {
            graphicsContext.setFill(Color.valueOf("#e2a65e"));
        } else {
            graphicsContext.setFill(Color.valueOf("#2f8daf"));
        }
    }

    private void setProperWallStrokeColor(CellBuilder cellBuilder, String wall) {
        if (cellBuilder.wallExists(wall))
            graphicsContext.setStroke(Color.valueOf("#3e3553"));
        else if (cellBuilder.isFinished()) {
            graphicsContext.setStroke(Color.valueOf("#5ee2a7"));
        } else {
            graphicsContext.setStroke(Color.valueOf("#e2a65e"));
        }
    }

    private void removeWallBetween(int fromX, int fromY, int toX, int toY) {

        if (fromX == toX && fromY + 1 == toY) {
            mazeBuilder.getCellBuilders()[fromX][fromY].removeWall("bottom");
            mazeBuilder.getCellBuilders()[toX][toY].removeWall("top");
        }
        if (fromY == toY && fromX + 1 == toX) {
            mazeBuilder.getCellBuilders()[fromX][fromY].removeWall("right");
            mazeBuilder.getCellBuilders()[toX][toY].removeWall("left");
        }
        if (fromX == toX && toY + 1 == fromY)
            removeWallBetween(toX, toY, fromX, fromY);
        if (fromY == toY && toX + 1 == fromX)
            removeWallBetween(toX, toY, fromX, fromY);
    }
}