package ch.epfl.imhof.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Lorenz Rasch
 */
public final class Polygon {

    private final ClosedPolyLine shell;
    private final List<ClosedPolyLine> holes;

    public Polygon(ClosedPolyLine shell, List<ClosedPolyLine> holes) {
        this.shell = shell;
        this.holes = Collections.unmodifiableList(new ArrayList<ClosedPolyLine>(holes));
    }

    public Polygon(ClosedPolyLine shell) {
        this.shell = shell;
        this.holes = Collections.emptyList();
    }

    public ClosedPolyLine shell() {
        return shell;
    }

    public List<ClosedPolyLine> holes() {
        return holes;
    }
}
