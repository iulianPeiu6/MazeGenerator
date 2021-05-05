package controllers;

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
import models.Cell;
import models.Coordinate;
import models.Maze;
import scenes.MenuScene;

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

    private Maze maze;
    private GraphicsContext graphicsContext;
    private double lineWidth;
    AtomicBoolean wasRun = new AtomicBoolean(false);

    public GenerateMazeController() {
        this.lineWidth = 5;
    }
    @FXML
    public void initialize(){
        graphicsContext = mazeCanvas.getGraphicsContext2D();
    }

    @FXML
    public void back(ActionEvent event) {
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();

        Stage menuStage = new Stage();
        menuStage.setMaximized(true);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(MenuScene.getScene());
        menuStage.show();
    }

    @FXML
    public void save(ActionEvent event) {
        //TODO
    }

    @FXML
    private synchronized void generateMaze() {
        if (wasRun.getAndSet(true))
            return;
        maze = new Maze((int) mazeCanvas.getWidth(), (int) mazeCanvas.getHeight(), getCellDimension());
        maze.setStart(new Coordinate((int) (Math.random() * maze.width), (int) (Math.random() * maze.height)));

        List<Coordinate> cellsStack = new ArrayList<>();
        Coordinate current = maze.getStart();
        cellsStack.add(current);
        maze.setVisitedCell(current);
        double pauseTime = 0;

        while (!cellsStack.isEmpty()) {
            Coordinate unvisitedNeighbour = maze.getRandomUnvisitedNeighbour(current);
            if (unvisitedNeighbour != null) {
                cellsStack.add(current);
                removeWallBetween(current.x, current.y, unvisitedNeighbour.x, unvisitedNeighbour.y);
                current = unvisitedNeighbour;
                maze.setVisitedCell(current);
            } else {
                maze.cells[current.x][current.y].setFinished();
                current = cellsStack.get(cellsStack.size() - 1);
                cellsStack.remove(cellsStack.size() - 1);
            }

            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(pauseTime += 0.125));
            Maze finalMaze = new Maze(maze);
            pauseTransition.setOnFinished((event) -> {
                drawMaze(finalMaze);
            });
            pauseTransition.play();
        }
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

    private void drawMaze(Maze maze) {
        drawMazeSkeleton(maze);
        for (int i = 0; i < maze.width; i++)
            for (int j = 0; j < maze.height; j++)
                drawCell(maze.cells[i][j], new Coordinate(i, j));
    }

    private void drawMazeSkeleton(Maze maze) {
        graphicsContext.setFill(Color.valueOf("#2f8daf"));
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.fillRect(0, 0, mazeCanvas.getWidth(), mazeCanvas.getHeight());

        for (int i = 0; i < maze.height; i++)
            graphicsContext.strokeLine(0, i * maze.cellDimension,
                    mazeCanvas.getWidth(), i * maze.cellDimension);

        for (int i = 0; i < maze.width; i++)
            graphicsContext.strokeLine(i * maze.cellDimension, 0,
                    i * maze.cellDimension, mazeCanvas.getHeight());
    }

    private void drawCell(Cell cell, Coordinate coordinate) {
        graphicsContext.setLineWidth(lineWidth);

        setProperWallStrokeColor(cell, "top");
        graphicsContext.strokeLine(coordinate.x * maze.cellDimension + lineWidth,
                coordinate.y * maze.cellDimension,
                (coordinate.x + 1) * maze.cellDimension - lineWidth,
                coordinate.y * maze.cellDimension);

        setProperWallStrokeColor(cell, "bottom");
        graphicsContext.strokeLine(coordinate.x * maze.cellDimension + lineWidth,
                (coordinate.y + 1) * maze.cellDimension,
                (coordinate.x + 1) * maze.cellDimension - lineWidth,
                (coordinate.y + 1) * maze.cellDimension);

        setProperWallStrokeColor(cell, "left");
        graphicsContext.strokeLine(coordinate.x * maze.cellDimension,
                coordinate.y * maze.cellDimension + lineWidth,
                coordinate.x * maze.cellDimension,
                (coordinate.y + 1) * maze.cellDimension - lineWidth);

        setProperWallStrokeColor(cell, "right");
        graphicsContext.strokeLine((coordinate.x + 1) * maze.cellDimension,
                coordinate.y * maze.cellDimension + lineWidth,
                (coordinate.x + 1) * maze.cellDimension,
                (coordinate.y + 1) * maze.cellDimension - lineWidth);

        setProperCellFillColor(cell);
        graphicsContext.fillRect(coordinate.x * maze.cellDimension + 2.5,
                coordinate.y * maze.cellDimension + 2.5,
                maze.cellDimension - 5,
                maze.cellDimension - 5);
    }

    private void setProperCellFillColor(Cell cell) {
        if (cell.isFinished())
            graphicsContext.setFill(Color.valueOf("#5ee2a7"));
        else if (cell.isVisited()) {
            graphicsContext.setFill(Color.valueOf("#e2a65e"));
        } else {
            graphicsContext.setFill(Color.valueOf("#2f8daf"));
        }
    }

    private void setProperWallStrokeColor(Cell cell, String wall) {
        if (cell.wallExists(wall))
            graphicsContext.setStroke(Color.valueOf("#3e3553"));
        else if (cell.isFinished()) {
            graphicsContext.setStroke(Color.valueOf("#5ee2a7"));
        } else {
            graphicsContext.setStroke(Color.valueOf("#e2a65e"));
        }
    }

    private void removeWallBetween(int fromX, int fromY, int toX, int toY) {

        if (fromX == toX && fromY + 1 == toY) {
            maze.cells[fromX][fromY].removeWall("bottom");
            maze.cells[toX][toY].removeWall("top");
        }
        if (fromY == toY && fromX + 1 == toX) {
            maze.cells[fromX][fromY].removeWall("right");
            maze.cells[toX][toY].removeWall("left");
        }
        if (fromX == toX && toY + 1 == fromY)
            removeWallBetween(toX, toY, fromX, fromY);
        if (fromY == toY && toX + 1 == fromX)
            removeWallBetween(toX, toY, fromX, fromY);
    }
}