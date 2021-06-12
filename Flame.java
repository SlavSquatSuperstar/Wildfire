import greenfoot.*;

public class Flame extends Actor {

    private Direction dir;
    private int speed = Constants.METEOR_SPEED;
    private int strength = Constants.METEOR_STRENGTH;
    private int moveCounter;
    
    public Flame(String dir) {
        this.dir = Direction.valueOf(dir.toUpperCase());
    }

    public void act() {
        if (--moveCounter <= 0) {
            moveCounter = speed;

            // Set any touching bridge tiles on fire
            if (!getIntersectingObjects(BridgeTile.class).isEmpty()) {
                getIntersectingObjects(BridgeTile.class).get(0).setState(BridgeTile.State.BURNING);
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

    public static enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }

}
