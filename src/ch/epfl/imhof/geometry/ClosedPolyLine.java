package ch.epfl.imhof.geometry;

import java.util.List;

/**
 * @author Lorenz Rasch
 */
public final class ClosedPolyLine extends PolyLine{

    public ClosedPolyLine(List<Point> points) {
        super(points);
    }

    @Override
    public boolean isClosed() {
        return true;
    }

    public double area(){
        double area = 0;
        for(int i = 0; i < points().size(); i++){
            area += getPoint(i).x()*(getPoint(i+1).y()-getPoint(i-1).y());
        }
        return Math.abs(area)/2;
    }

    public boolean containsPoint(Point p){
        int index = 0;
        for(int i = 0; i < points().size(); i++){
            if(getPoint(i).y() <= p.y()){
                if (getPoint(i + 1).y() > p.y() && isLeft(p, getPoint(i), getPoint(i + 1))) {
                    ++index;
                }
            } else {
                if(getPoint(i+1).y() <= p.y() && isLeft(p, getPoint(i+1), getPoint(i))){
                    --index;
                }
            }
        }
        return index != 0;
    }

    private Point getPoint(int i){
        return points().get(Math.floorMod(i, points().size()));
    }

    private boolean isLeft(Point p, Point p1, Point p2){
        return (p1.x()-p.x())*(p2.y()-p.y())>(p2.x()-p.x())*(p1.y()-p.y());
    }
}
