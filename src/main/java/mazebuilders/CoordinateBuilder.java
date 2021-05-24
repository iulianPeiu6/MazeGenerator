package mazebuilders;

import java.util.Objects;

public class CoordinateBuilder {
    public Integer x, y;

    public CoordinateBuilder(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CoordinateBuilder(CoordinateBuilder start) {
        this.x = start.x;
        this.y = start.y;
    }

    public CoordinateBuilder() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoordinateBuilder)) return false;
        CoordinateBuilder that = (CoordinateBuilder) o;
        return x.equals(that.x) && y.equals(that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
