package models;

import java.util.ArrayList;
import java.util.List;

public class Maze {

    public int width, height;

    public int cellDimension;

    public Cell[][] cells;

    private Coordinate start;

    public Maze(int width, int height, int cellDimension) {
        this.width = width/cellDimension-1;
        this.height = height/cellDimension-1;
        this.cellDimension = cellDimension;
        this.cells = new Cell[width][height];
        for (int i=0; i<width;i++)
            for (int j=0; j<height; j++)
                this.cells[i][j] = new Cell();
    }

    public Boolean existsUnvisitedCells(){
        for (int i=0; i<width;i++)
            for (int j=0; j<height; j++)
                if (!cells[i][j].visited)
                    return true;
        return false;
    }

    public void setVisitedCell(Coordinate coordinate){
        cells[coordinate.x][coordinate.y].setVisited();
    }

    public Coordinate getRandomUnvisitedNeighbour(Coordinate current) {
        List<Coordinate> sample = new ArrayList<>();
        if (validNeighbour(current.x-1,current.y))
            if (!cells[current.x-1][current.y].visited)
                sample.add(new Coordinate(current.x-1, current.y));

        if (validNeighbour(current.x,current.y-1))
            if (!cells[current.x][current.y-1].visited)
                sample.add(new Coordinate(current.x, current.y-1));

        if (validNeighbour(current.x,current.y+1))
            if (!cells[current.x][current.y+1].visited)
                sample.add(new Coordinate(current.x, current.y+1));

        if (validNeighbour(current.x+1,current.y))
            if (!cells[current.x+1][current.y].visited)
                sample.add(new Coordinate(current.x+1, current.y));

        if (sample.isEmpty())
            return null;

        int randomIndex = (int)(Math.random()*sample.size());
        return sample.get(randomIndex);
    }

    private boolean validNeighbour(int x, int y) {
        if (x<0 || y<0)
            return false;
        if (x>width || y>height)
            return false;
        return true;
    }

    public Coordinate getStart() {
        return start;
    }

    public void setStart(Coordinate start) {
        this.start = start;
    }
}
