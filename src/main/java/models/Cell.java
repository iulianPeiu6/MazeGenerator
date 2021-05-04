package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cell {
    public List<Boolean> walls;

    private Boolean visited;

    private Boolean finished;

    public Cell() {
        this.walls = Arrays.asList(true, true, true, true);
        this.visited = false;
        this.finished = false;
    }

    public Cell(Cell cell) {
        this.walls = new ArrayList<>(cell.walls);
        this.visited = Boolean.valueOf(cell.visited);
        this.finished = Boolean.valueOf(cell.finished);
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
