package xmlmodels;

import jakarta.xml.bind.annotation.*;
import mazebuilders.MazeBuilder;

import java.util.Arrays;

@XmlRootElement
@XmlAccessorType (XmlAccessType.NONE)
public class Maze {
    @XmlAttribute
    private int id;

    @XmlAttribute(name = "mode")
    private String mode;

    @XmlAttribute(name = "completed")
    private String completed;

    @XmlElement(name = "width")
    private int width;

    @XmlElement(name = "height")
    private int height;

    @XmlElement(name = "start")
    private Coordinate start;

    @XmlElement(name = "cell_dimension")
    private int cellDimension;

    @XmlElementWrapper(name = "cells")
    @XmlElement(name ="items")
    private Cell[][] cells;

    public Maze(int id, String mode, MazeBuilder mazeBuilder) {
        this.id = id;
        this.mode = mode;
        this.width = mazeBuilder.getWidth();
        this.height = mazeBuilder.getHeight();
        this.start = new Coordinate(mazeBuilder.getStart());
        this.cellDimension = mazeBuilder.getCellDimension();
        this.cells = new Cell[width][height];

        for (int i=0;i<width;i++)
            for (int j=0;j<height;j++)
                cells[i][j] = new Cell(new Coordinate(i,j),
                        new Walls(mazeBuilder.getCellBuilders()[i][j].walls));

        this.completed = "no";
    }

    public Maze() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Coordinate getStart() {
        return start;
    }

    public int getCellDimension() {
        return cellDimension;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getId() {
        return id;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Maze{" +
                "id=" + id +
                ", mode='" + mode + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", start=" + start +
                ", cellDimension=" + cellDimension +
                ", cells=" + Arrays.toString(cells) +
                '}';
    }
}
