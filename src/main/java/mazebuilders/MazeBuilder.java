package mazebuilders;

import xmlmodels.Maze;

import java.util.ArrayList;
import java.util.List;

public class MazeBuilder {

    private final Integer width;
    private final Integer height;

    private final Integer cellDimension;

    private final CellBuilder[][] cellBuilders;

    private CoordinateBuilder start;
    private CoordinateBuilder current;

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
        this.width = mazeBuilder.width;
        this.height = mazeBuilder.height;
        this.cellDimension = mazeBuilder.cellDimension;
        this.cellBuilders = new CellBuilder[width][height];
        for (int i=0; i< width;i++)
            for (int j=0; j< height; j++)
                this.cellBuilders[i][j] = new CellBuilder(mazeBuilder.cellBuilders[i][j]);
        this.start = new CoordinateBuilder(mazeBuilder.start);
        this.current = new CoordinateBuilder(mazeBuilder.current);
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
        return x < width && y < height;
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

    public Integer getHeight() {
        return height;
    }

    public Integer getCellDimension() {
        return cellDimension;
    }

    public CellBuilder[][] getCellBuilders() {
        return cellBuilders;
    }
}
