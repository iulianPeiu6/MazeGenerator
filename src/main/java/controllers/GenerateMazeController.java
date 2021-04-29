package controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import models.Maze;

public class GenerateMazeController {

    @FXML
    private Canvas mazeCanvas;

    @FXML
    public void setUpCanvas(ContextMenuEvent event) {
        System.out.println("ioo");
    }

    @FXML
    void generateMaze(KeyEvent event) {
        if (event.getCode().getChar().charAt(0) == 'G')
            generateMaze();
    }

    private Maze maze;

    private void generateMaze() {
        System.out.println("generating maze");
        var graphicsContext = mazeCanvas.getGraphicsContext2D();
        maze = new Maze(8,6);
        graphicsContext.setFill(Color.GRAY);
        for (int i=0; i< maze.width; i++){
            for (int j=0; j< maze.height; j++){
                graphicsContext.fillRect(i*maze.cellWidth,
                        j*maze.cellHeight,
                        maze.cellWidth,
                        maze.cellHeight);
            }
        }

    }
}