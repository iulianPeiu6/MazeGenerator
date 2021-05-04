package models;

public class Coordinate {
    public Integer x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate start) {
        this.x = Integer.valueOf(start.x);
        this.y = Integer.valueOf(start.y);
    }
}
