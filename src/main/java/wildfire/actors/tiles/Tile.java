package wildfire.actors.tiles;

import greenfoot.*;

import java.awt.Point;

/**
 * A physical object inside the world.
 */
public abstract class Tile extends Actor {

    /**
     * @return The distance in cell between this tile and another.
     */
    public double getDistance(Tile other) {
        return getDistance(new Point(other.getX(), other.getY()));
    }

    /**
     * @return The distance in cell between this tile and a point in space.
     */
    public double getDistance(Point other) {
        return Math.sqrt(
            Math.pow((this.getX() - other.x), 2) +
            Math.pow((this.getY() - other.y), 2)
        );
    }

    /**
     * Measure how optimal this tile is to travel on. Used in the A* Pathfinding Algorithm.
     *
     * @return The aggregate distance from this tile to both endpoints.
     */
    public int getFCost(Tile start, Tile finish) {
        int g_cost = (int) getDistance(start) * 10;
        int h_cost = (int) getDistance(finish) * 10;
        return g_cost + h_cost;
    }

    // Override method to copy rather than reference the source image
    @Override
    public void setImage(GreenfootImage img) {
        super.setImage(new GreenfootImage(img));
    }

    @Override
    public String toString() {
        return String.format("%s (%d, %d)", getClass().getName(), getX(), getY());
    }

}
