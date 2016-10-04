package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * @author Lorenz Rasch
 */
public final class OpenPolyLine extends PolyLine {

    public OpenPolyLine(List<Point> points) {
        super(points);
    }

    @Override
    public boolean isClosed() {
        return false;
    }
}
