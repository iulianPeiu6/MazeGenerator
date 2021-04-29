package models;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Cell {
    public List<Boolean> walls;

    public Cell() {
        this.walls = Arrays.asList(true, true, true, true);
    }

    public void removeWall(String wall) throws IOException {
        int wallIndex = getindexWall(wall);
        walls.set(wallIndex, false);
    }

    public Boolean wallExists(String wall) throws IOException {
        int wallIndex = getindexWall(wall);
        return walls.get(wallIndex);
    }

    private int getindexWall(String wall) throws IOException {
        if (wall.equals("top"))
            return 0;
        if (wall.equals("right"))
            return 1;
        if (wall.equals("bottom"))
            return 2;
        if (wall.equals("left"))
            return 3;
        throw new IOException();
    }
}
