package xmlmodels;

import jakarta.xml.bind.annotation.*;
import mazebuilders.CoordinateBuilder;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Coordinate {
    @XmlAttribute
    private int x;

    @XmlAttribute
    private  int y;

    public Coordinate() {
    }

    public Coordinate(CoordinateBuilder coordinateBuilder) {
        this.x = coordinateBuilder.x;
        this.y = coordinateBuilder.y;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CoordinateBuilder getCoordinate(){
        return new CoordinateBuilder(x,y);
    }
}
