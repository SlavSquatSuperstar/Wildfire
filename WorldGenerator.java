import greenfoot.*;

import java.util.*;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class WorldGenerator {

    // ensure the tiles aren't too close to the top/bottom edges
    private static int buffer = Constants.WORLDGEN_BUFFER;
    private int width, height;
    private Point start, finish;
    private HashMap<Point, Integer> obstacleLocations;

    public WorldGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Point getStart() {
        if (start == null)
            start = new Point(0, Util.random(buffer, height - buffer));
        return start;
    }

    public Point getFinish() {
        if (finish == null)
            finish = new Point(width, Util.random(buffer, height - buffer));
        return finish;
    }

    public HashMap<Point, Integer> getObstacleLocations() {
        if (obstacleLocations == null) {
            obstacleLocations = new HashMap<Point, Integer>();
            //Rectangle2D bounds = new Rectangle2D.Double(buffer, buffer, width - 1 - buffer, height - 1 - buffer);
            double slope = (double) (finish.y - start.y) / (finish.x - start.x);
            for (int x = buffer; x < width - buffer; x++) {
                Point p = new Point(x, (int) yOfX(x, slope, start.y));

                obstacleLocations.put(p, 20);
                obstacleLocations.put(new Point(p.x, p.y+1), 10);
                obstacleLocations.put(new Point(p.x, p.y-1), 10);
            }
        }
        return obstacleLocations;
    }

    private double yOfX(int x, double slope, int xInt) {
        return x * slope + xInt;
    }

}
