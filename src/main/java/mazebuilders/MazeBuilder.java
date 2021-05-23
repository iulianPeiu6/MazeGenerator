package mazebuilders;

import xmlmodels.Maze;

import java.util.ArrayList;
import java.util.List;

public class MazeBuilder {

    private Integer width, height;

    private Integer cellDimension;

    private CellBuilder[][] cellBuilders;

    private CoordinateBuilder start, current;

    public MazeBuilder(int width, int height, int cellDimension) {
        this.width = width/cellDimension;
        this.height = height/cellDimension;
        this.cellDimension = cellDimension;
        this.cellBuilders = new CellBuilder[width][height];
        for (int i=0; i<width;i++)
            for (int j=0; j<height; j++)
                this.cellBuilders[i][j] = new CellBuilder();
    }

    public MazeBuilder(Maze maze){
        width = maze.getWidth();
        height = maze.getHeight();
        cellDimension = maze.getCellDimension();
        cellBuilders = new CellBuilder[width][height];
        start = maze.getStart().getCoordinate();
        for (int i=0; i<width;i++)
            for (int j=0; j<height; j++)
                this.cellBuilders[i][j] = new CellBuilder(maze.getCells()[i][j].getWalls().asList());
    }

    public MazeBuilder(MazeBuilder mazeBuilder){
        this.width = Integer.valueOf(mazeBuilder.width);
        this.height = Integer.valueOf(mazeBuilder.height);
        this.cellDimension = Integer.valueOf(mazeBuilder.cellDimension);
        this.cellBuilders = new CellBuilder[width][height];
        for (int i=0; i< width;i++)
            for (int j=0; j< height; j++)
                this.cellBuilders[i][j] = new CellBuilder(mazeBuilder.cellBuilders[i][j]);
        this.start = new CoordinateBuilder(mazeBuilder.start);
        this.current = new CoordinateBuilder(mazeBuilder.current);
    }

    public Boolean existsUnvisitedCells(){
        for (int i=0; i<width;i++)
            for (int j=0; j<height; j++)
                if (!cellBuilders[i][j].isVisited())
                    return true;
        return false;
    }

    public void setVisitedCell(CoordinateBuilder coordinateBuilder){
        cellBuilders[coordinateBuilder.x][coordinateBuilder.y].setVisited();
    }

    public CoordinateBuilder getRandomUnvisitedNeighbour(CoordinateBuilder current) {
        List<CoordinateBuilder> sample = new ArrayList<>();
        if (validNeighbour(current.x-1,current.y))
            if (!cellBuilders[current.x-1][current.y].isVisited())
                sample.add(new CoordinateBuilder(current.x-1, current.y));

        if (validNeighbour(current.x,current.y-1))
            if (!cellBuilders[current.x][current.y-1].isVisited())
                sample.add(new CoordinateBuilder(current.x, current.y-1));

        if (validNeighbour(current.x,current.y+1))
            if (!cellBuilders[current.x][current.y+1].isVisited())
                sample.add(new CoordinateBuilder(current.x, current.y+1));

        if (validNeighbour(current.x+1,current.y))
            if (!cellBuilders[current.x+1][current.y].isVisited())
                sample.add(new CoordinateBuilder(current.x+1, current.y));

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

    public CoordinateBuilder getStart() {
        return start;
    }

    public void setStart(CoordinateBuilder start) {
        this.start = new CoordinateBuilder(start);
        this.current = new CoordinateBuilder(start);
    }

    public CoordinateBuilder getCurrent() {
        return current;
    }

    public void setCurrent(CoordinateBuilder current) {
        this.current = current;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getCellDimension() {
        return cellDimension;
    }

    public void setCellDimension(Integer cellDimension) {
        this.cellDimension = cellDimension;
    }

    public CellBuilder[][] getCellBuilders() {
        return cellBuilders;
    }

    public void setCellBuilders(CellBuilder[][] cellBuilders) {
        this.cellBuilders = cellBuilders;
    }
}
