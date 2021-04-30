package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import models.Coordinate;
import models.Maze;

import java.util.ArrayList;
import java.util.List;

public class GenerateMazeController {

    @FXML
    private Canvas mazeCanvas;

    @FXML
    private TextField cellDimensionTextArea;

    public GenerateMazeController() {

    }

    private Maze maze;
    private double lineWidth = 2.5;
    private GraphicsContext graphicsContext;

    @FXML
    void generateMaze(ActionEvent event) {
        graphicsContext = mazeCanvas.getGraphicsContext2D();
        int cellDimension = Integer.parseInt(cellDimensionTextArea.getText());
        maze = new Maze((int)mazeCanvas.getWidth(), (int)mazeCanvas.getHeight(), cellDimension);

        initMaze();
        iterateMaze(3, 1);
    }

    private void initMaze() {
        graphicsContext.setFill(Color.GRAY);
        graphicsContext.fillRect(0, 0, mazeCanvas.getWidth(), mazeCanvas.getHeight());

        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setLineWidth(lineWidth);

        for (int i=0; i <= maze.height+1; i++)
            graphicsContext.strokeLine(0,i* maze.cellDimension , mazeCanvas.getWidth(), i* maze.cellDimension);

        for (int i=0; i <= maze.width+1; i++)
            graphicsContext.strokeLine(i* maze.cellDimension,0 , i* maze.cellDimension, mazeCanvas.getHeight());
    }

    private void iterateMaze(int startX, int startY) {
        graphicsContext.setStroke(Color.GRAY);
        graphicsContext.setLineWidth(lineWidth+0.5);
        List<Coordinate> cellsStack = new ArrayList<>();
        Coordinate current = new Coordinate(startX, startY);
        cellsStack.add(current);
        maze.setVisitedCell(current);

        while (!cellsStack.isEmpty()){
            Coordinate unvisitedNeighbour = maze.getRandomUnvisitedNeighbour(current);
            if (unvisitedNeighbour != null){
                cellsStack.add(current);
                removeWallBetween(current.x, current.y, unvisitedNeighbour.x, unvisitedNeighbour.y);
                current = unvisitedNeighbour;
                maze.setVisitedCell(current);
            }
            else{
                current = cellsStack.get(cellsStack.size()-1);
                cellsStack.remove(cellsStack.size()-1);
            }
        }
    }

    private void removeWallBetween(int fromX, int fromY, int toX, int toY) {

        if (fromX == toX && fromY+1 == toY){
            maze.cells[fromX][fromY].removeWall("bottom");
            maze.cells[toX][toY].removeWall("top");
            graphicsContext.strokeLine(fromX* maze.cellDimension + lineWidth,
                    (fromY+1)* maze.cellDimension,
                    (toX+1)* maze.cellDimension - lineWidth,
                    toY* maze.cellDimension);
        }
        if (fromY == toY && fromX+1==toX){
            maze.cells[fromX][fromY].removeWall("right");
            maze.cells[toX][toY].removeWall("left");
            graphicsContext.strokeLine((fromX+1)* maze.cellDimension,
                    fromY* maze.cellDimension + lineWidth,
                    toX* maze.cellDimension,
                    (toY+1)* maze.cellDimension - lineWidth);
        }
        if (fromX == toX && toY+1 == fromY)
            removeWallBetween(toX,toY,fromX,fromY);
        if (fromY == toY && toX+1==fromX)
            removeWallBetween(toX,toY,fromX,fromY);
    }
}