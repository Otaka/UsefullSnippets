package pointsinterpolation;

/**
 * @author sad
 */
public class PathFollower {

    private final FPoint[] points;
    private final FPoint currentPoint = new FPoint();
    private int passedPointIndex =0;

    public PathFollower(FPoint[] points) {
        this.points = points;
        if (points != null && points.length > 0) {
            currentPoint.setLocation(points[0]);
        }
    }

    public FPoint getCurrentPoint() {
        return currentPoint;
    }

    public boolean step(double length) {
        FPoint lp = currentPoint;
        FPoint cp = null;
        boolean found = false;
        for (int i = passedPointIndex; i < points.length; i++) {
            cp = points[i];
            double l = lineLength(lp, cp);
            if (length <= l) {
                found = true;
                break;
            } else {
                length -= l;
            }

            lp = cp;
            passedPointIndex++;
        }

        if (!found || length == -1) {
            return false;
        }

        double angle = Math.atan2(cp.getY() - lp.getY(), cp.getX() - lp.getX());
        double x = Math.cos(angle) * length + lp.getX();
        double y = Math.sin(angle) * length + lp.getY();
        currentPoint.setLocation(x, y);
        return true;
    }

    private double lineLength(FPoint p1, FPoint p2) {
        return Math.sqrt((p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY()));
    }

}
