package models;

import java.util.ArrayList;
import java.util.List;

public class Maze {

    public Integer width, height;

    public Integer cellDimension;

    public Cell[][] cells;

    private Coordinate start, current;

    public Maze(int width, int height, int cellDimension) {
        this.width = width/cellDimension;
        this.height = height/cellDimension;
        this.cellDimension = cellDimension;
        this.cells = new Cell[width][height];
        for (int i=0; i<width;i++)
            for (int j=0; j<height; j++)
                this.cells[i][j] = new Cell();
    }

    public Maze(Maze maze){
        this.width = Integer.valueOf(maze.width);
        this.height = Integer.valueOf(maze.height);
        this.cellDimension = Integer.valueOf(maze.cellDimension);
        this.cells = new Cell[width][height];
        for (int i=0; i< width;i++)
            for (int j=0; j< height; j++)
                this.cells[i][j] = new Cell(maze.cells[i][j]);
        this.start = new Coordinate(maze.start);
        this.current = new Coordinate(maze.current);
    }

    public Boolean existsUnvisitedCells(){
        for (int i=0; i<width;i++)
            for (int j=0; j<height; j++)
                if (!cells[i][j].isVisited())
                    return true;
        return false;
    }

    public void setVisitedCell(Coordinate coordinate){
        cells[coordinate.x][coordinate.y].setVisited();
    }

    public Coordinate getRandomUnvisitedNeighbour(Coordinate current) {
        List<Coordinate> sample = new ArrayList<>();
        if (validNeighbour(current.x-1,current.y))
            if (!cells[current.x-1][current.y].isVisited())
                sample.add(new Coordinate(current.x-1, current.y));

        if (validNeighbour(current.x,current.y-1))
            if (!cells[current.x][current.y-1].isVisited())
                sample.add(new Coordinate(current.x, current.y-1));

        if (validNeighbour(current.x,current.y+1))
            if (!cells[current.x][current.y+1].isVisited())
                sample.add(new Coordinate(current.x, current.y+1));

        if (validNeighbour(current.x+1,current.y))
            if (!cells[current.x+1][current.y].isVisited())
                sample.add(new Coordinate(current.x+1, current.y));

        if (sample.isEmpty())
            return null;

        int randomIndex = (int)(Math.random()*sample.size());
        return sample.get(randomIndex);
    }

    private boolean validNeighbour(int x, int y) {
        if (x<0 || y<0)
            return false;
        if (x>=width || y>=height)
            return false;
        return true;
    }

    public Coordinate getStart() {
        return start;
    }

    public void setStart(Coordinate start) {
        this.start = new Coordinate(start);
        this.current = new Coordinate(start);
    }

    public Coordinate getCurrent() {
        return current;
    }

    public void setCurrent(Coordinate current) {
        this.current = current;
    }
}
