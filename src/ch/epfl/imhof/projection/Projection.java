package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * @author Lorenz Rasch
 */
public interface Projection {

    Point project(PointGeo point);

    PointGeo inverse(Point point);
}
