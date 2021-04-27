package ch.epfl.imhof.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Lorenz Rasch
 */
public abstract class PolyLine {

    private final List<Point> points;

    public PolyLine(List<Point> points) throws IllegalArgumentException {
        if (points.isEmpty())
            throw new IllegalArgumentException("No Points in this list.");
        this.points = Collections.unmodifiableList(new ArrayList<Point>(points));
    }

    public abstract boolean isClosed();

    public List<Point> points() {
        return points;
    }

    public Point firstPoint() {
        return points.get(0);
    }

    public static final class Builder {
        private List<Point> points = new ArrayList<>();

        public void addPoint(Point newPoint) {
            points.add(newPoint);
        }

        public OpenPolyLine buildOpen() {
            return new OpenPolyLine(points);
        }

        public ClosedPolyLine buildClosed() {
            return new ClosedPolyLine(points);
        }
    }
}
