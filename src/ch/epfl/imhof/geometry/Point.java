package ch.epfl.imhof.geometry;

/**
 * @author Lorenz Rasch
 */
public final class Point {

    private final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }
}
