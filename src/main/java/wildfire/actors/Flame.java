package wildfire.actors;

import greenfoot.*;
import wildfire.Assets;
import wildfire.Constants;
import wildfire.Counter;
import wildfire.actors.tiles.BridgeTile;
import wildfire.actors.tiles.Obstacle;

/**
 * A burning ball of fire that ignites wooden tiles.
 */
public class Flame extends Actor {

    private Direction dir;
    private Counter moveCounter;
    private int strength = Constants.METEOR_STRENGTH;

    /**
     * Initializes a flame with a direction.
     *
     * @param dir Up, down, left, or right.
     */
    public Flame(String dir) {
        setImage(Assets.FLAME);
        this.dir = Direction.valueOf(dir.toUpperCase());
        moveCounter = new Counter(Constants.METEOR_MOVE_DELAY, true);
    }

    public void act() {
        moveCounter.count();
        if (moveCounter.value() <= 0) {
            moveCounter.reset();
            // Set any touching bridge tiles on fire
            if (!getIntersectingObjects(BridgeTile.class).isEmpty()) {
                getIntersectingObjects(BridgeTile.class).get(0).ignite();
                strength--;
            }
            // Move
            switch (dir) {
                case UP:
                    setLocation(getX(), getY() - 1);
                    break;
                case DOWN:
                    setLocation(getX(), getY() + 1);
                    break;
                case LEFT:
                    setLocation(getX() - 1, getY());
                    break;
                case RIGHT:
                    setLocation(getX() + 1, getY());
                    break;
            }
        }

        // Destroy if no more strength or touching the edge/an obstacle
        if (strength == 0 || isAtEdge() || !getIntersectingObjects(Obstacle.class).isEmpty())
            getWorld().removeObject(this);
    }

    /**
     * Which way this flame should move.
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }

}
