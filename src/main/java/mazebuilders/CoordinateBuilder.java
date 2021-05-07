package mazebuilders;

public class CoordinateBuilder {
    public Integer x, y;

    public CoordinateBuilder(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CoordinateBuilder(CoordinateBuilder start) {
        this.x = Integer.valueOf(start.x);
        this.y = Integer.valueOf(start.y);
    }
}
