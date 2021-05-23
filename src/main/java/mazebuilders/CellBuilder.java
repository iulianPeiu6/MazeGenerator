package mazebuilders;

import xmlmodels.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CellBuilder {
    public List<Boolean> walls;

    private Boolean visited;

    private Boolean finished;

    public CellBuilder() {
        this.walls = Arrays.asList(true, true, true, true);
        this.visited = false;
        this.finished = false;
    }

    public CellBuilder(List<Boolean> walls) {
        this.walls = walls;
        this.visited = true;
        this.finished = true;
    }

    public CellBuilder(Cell cell) {
        this.walls = cell.getWalls().asList();
    }

    public CellBuilder(CellBuilder cellBuilder) {
        this.walls = new ArrayList<>(cellBuilder.walls);
        this.visited = Boolean.valueOf(cellBuilder.visited);
        this.finished = Boolean.valueOf(cellBuilder.finished);
    }

    public void removeWall(String wall) {
        int wallIndex = getIndexWall(wall);
        walls.set(wallIndex, false);
    }

    public Boolean wallExists(String wall) {
        int wallIndex = getIndexWall(wall);
        return walls.get(wallIndex);
    }

    private int getIndexWall(String wall) {
        if (wall.equals("top"))
            return 0;
        if (wall.equals("right"))
            return 1;
        if (wall.equals("bottom"))
            return 2;
        if (wall.equals("left"))
            return 3;
        System.out.println("???");
        return 0;
    }

    public Boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        visited = true;
    }

    public Boolean isFinished() {
        return finished;
    }

    public void setFinished() {
        this.finished = true;
    }


}
