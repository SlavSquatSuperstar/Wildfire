package wildfire.world;

import wildfire.Constants;
import wildfire.Util;

import java.util.*;
import java.awt.Point;

/**
 * A utility class for randomly generating worlds.
 */
public class WorldGenerator {

    // ensure the tiles aren't too close to the top/bottom edges
    private static final int BUFFER = Constants.WORLDGEN_BUFFER;
    private final int WIDTH, HEIGHT;
    private Point start, finish;
    private int[][] obstacleLocations;
//    private HashMap<Point, Integer> obstacleLocations;

    public WorldGenerator(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    /**
     * Return a random location for the start endpoint.
     */
    public Point getStart() {
        if (start == null)
            start = new Point(0, Util.random(BUFFER, HEIGHT - BUFFER));
        return start;
    }

    /**
     * Return a random location for the finish endpoint.
     */
    public Point getFinish() {
        if (finish == null)
            finish = new Point(WIDTH, Util.random(BUFFER, HEIGHT - BUFFER));
        return finish;
    }

    /**
     * Return weighted locations for generating obstacles.
     */
    public int[][] getObstacleLocations() {
        if (obstacleLocations == null) {
            obstacleLocations = new int[WIDTH][HEIGHT];
            //Rectangle2D bounds = new Rectangle2D.Double(buffer, buffer, width - 1 - buffer, height - 1 - buffer);
            float slope = (float) (finish.y - start.y) / (finish.x - start.x);
            for (int x = BUFFER; x < WIDTH - BUFFER; x++) {
                Point p = new Point(x, (int) yOfX(x, slope, start.y));
                obstacleLocations[p.x][p.y] = 20;
                obstacleLocations[p.x][p.y+1] = 10;
                obstacleLocations[p.x][p.y-1] = 10;
            }
        }
        return obstacleLocations;
    }

    /**
     * Evaluate the function y = f(x) at the provided x.
     */
    private float yOfX(int x, float slope, int xInt) {
        return x * slope + xInt;
    }

}
