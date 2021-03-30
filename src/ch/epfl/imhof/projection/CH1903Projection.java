package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * @author Lorenz Rasch
 */
public final class CH1903Projection implements Projection {

        @Override
        public Point project(PointGeo point) {
                double lon1 = (Math.toDegrees(point.longitude()) * 3600.0 - 26782.5) / 1e4;
                double lat1 = (Math.toDegrees(point.latitude()) * 3600.0 - 169028.66) / 1e4;
                double x = 600072.37 + 211455.93 * lon1 - 10938.51 * lon1 * lat1 - 0.36 * lon1 * Math.pow(lat1, 2)
                                - 44.54 * Math.pow(lon1, 3);
                double y = 200147.07 + 308807.95 * lat1 + 3745.25 * Math.pow(lon1, 2) + 76.63 * Math.pow(lat1, 2)
                                - 194.56 * Math.pow(lon1, 2) * lat1 + 119.79 * Math.pow(lat1, 3);
                return new Point(x, y);
        }

        @Override
        public PointGeo inverse(Point point) {
                double x1 = (point.x() - 6e5) / 1e6;
                double y1 = (point.y() - 2e5) / 1e6;
                double lon0 = 2.6779094 + 4.728982 * x1 + 0.791484 * x1 * y1 + 0.1306 * x1 * Math.pow(y1, 2)
                                - 0.0436 * Math.pow(x1, 3);
                double lat0 = 16.9023892 + 3.238272 * y1 - 0.270978 * Math.pow(x1, 2) - 0.002528 * Math.pow(y1, 2)
                                - 0.0447 * Math.pow(x1, 2) * y1 - 0.014 * Math.pow(y1, 3);
                double lon = Math.toRadians(lon0 * 100 / 36);
                double lat = Math.toRadians(lat0 * 100 / 36);
                return new PointGeo(lon, lat);
        }
}
