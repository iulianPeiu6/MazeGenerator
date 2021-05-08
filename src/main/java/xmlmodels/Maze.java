package xmlmodels;

import jakarta.xml.bind.annotation.*;
import mazebuilders.MazeBuilder;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Maze {
    @XmlAttribute
    private int id;

    @XmlAttribute
    private String mode;

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
    }

    public Maze() {
    }
}
