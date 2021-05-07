package xmlmodels;

import jakarta.xml.bind.annotation.XmlAttribute;

import java.util.List;

public class Walls {
    @XmlAttribute
    private Boolean top;

    @XmlAttribute
    private Boolean right;

    @XmlAttribute
    private Boolean bottom;

    @XmlAttribute
    private Boolean left;

    public Walls(List<Boolean> walls) {
        top = walls.get(0);
        right = walls.get(1);
        bottom = walls.get(2);
        left = walls.get(3);
    }

    public Walls() {
    }
}
