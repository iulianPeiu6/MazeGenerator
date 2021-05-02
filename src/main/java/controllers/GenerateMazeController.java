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
import models.Cell;
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
    private GraphicsContext graphicsContext;

    @FXML
    void generateMaze(ActionEvent event) {
        graphicsContext = mazeCanvas.getGraphicsContext2D();

        initMaze();
        drawMaze();
    }

    private void initMaze() {
        int cellDimension = Integer.parseInt(cellDimensionTextArea.getText());
        maze = new Maze((int)mazeCanvas.getWidth(), (int)mazeCanvas.getHeight(), cellDimension);
        maze.setStart(new Coordinate((int) (Math.random()* maze.width), (int) (Math.random()* maze.height)));

        List<Coordinate> cellsStack = new ArrayList<>();
        Coordinate current = maze.getStart();
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

    private void drawMaze() {
        graphicsContext.setStroke(Color.valueOf("#0b0054"));
        graphicsContext.fillRect(0,0,mazeCanvas.getWidth(),mazeCanvas.getHeight());
        for (int i=0;i<= maze.width;i++)
            for (int j=0;j<= maze.height;j++)
                drawCell(maze.cells[i][j], new Coordinate(i,j));
    }

    private void drawCell(Cell cell, Coordinate coordinate) {

        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setLineWidth(2.5);

        if (cell.wallExists("top"))
            graphicsContext.strokeLine(coordinate.x * maze.cellDimension,
                    coordinate.y * maze.cellDimension,
                    (coordinate.x +1) * maze.cellDimension,
                    coordinate.y * maze.cellDimension);
        if (cell.wallExists("bottom"))
            graphicsContext.strokeLine(coordinate.x * maze.cellDimension,
                    (coordinate.y+1) * maze.cellDimension,
                    (coordinate.x +1) * maze.cellDimension,
                    (coordinate.y+1) * maze.cellDimension);
        if (cell.wallExists("left"))
            graphicsContext.strokeLine(coordinate.x * maze.cellDimension,
                    coordinate.y * maze.cellDimension,
                    coordinate.x * maze.cellDimension,
                    (coordinate.y+1) * maze.cellDimension);
        if (cell.wallExists("right"))
            graphicsContext.strokeLine((coordinate.x+1) * maze.cellDimension,
                    coordinate.y * maze.cellDimension,
                    (coordinate.x+1) * maze.cellDimension,
                    (coordinate.y+1) * maze.cellDimension);
    }

    private void removeWallBetween(int fromX, int fromY, int toX, int toY) {

        if (fromX == toX && fromY+1 == toY){
            maze.cells[fromX][fromY].removeWall("bottom");
            maze.cells[toX][toY].removeWall("top");
        }
        if (fromY == toY && fromX+1==toX){
            maze.cells[fromX][fromY].removeWall("right");
            maze.cells[toX][toY].removeWall("left");
        }
        if (fromX == toX && toY+1 == fromY)
            removeWallBetween(toX,toY,fromX,fromY);
        if (fromY == toY && toX+1==fromX)
            removeWallBetween(toX,toY,fromX,fromY);
    }
}