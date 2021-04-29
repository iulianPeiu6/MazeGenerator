package models;

import java.util.ArrayList;
import java.util.List;

public class Maze {

    public int width, height;

    public int cellWidth, cellHeight;

    public List<Cell> cells;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        cellWidth = cellHeight = 100;
        this.cells = new ArrayList<>();
        for (int i=0; i<width*height;i++)
            cells.add(new Cell());
    }
}
