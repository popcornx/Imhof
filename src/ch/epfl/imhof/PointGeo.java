package ch.epfl.imhof;

/**
 * @author Lorenz Rasch
 */
public final class PointGeo {

    private final double lon, lat;

    public PointGeo(double longitude, double latitude) throws IllegalArgumentException{
        if(Math.abs(longitude) > Math.PI)
            throw new IllegalArgumentException("Longitude out of range: " + longitude);
        if(Math.abs(latitude) > Math.PI/2)
            throw new IllegalArgumentException("Latitude out of range: " + latitude);
        this.lon = longitude;
        this.lat = latitude;
    }

    public double longitude() {
        return lon;
    }

    public double latitude() {
        return lat;
    }
}
