package xmlmodels;

import jakarta.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Cell {
    @XmlElement(name = "coordinate")
    private Coordinate coordinate;

    @XmlElement(name = "walls")
    private Walls walls;

    public Cell(Coordinate coordinate, Walls walls) {
        this.coordinate = coordinate;
        this.walls = walls;
    }

    public Cell() {
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Walls getWalls() {
        return walls;
    }
}
