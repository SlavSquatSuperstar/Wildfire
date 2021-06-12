import greenfoot.*;

public class Tile extends Actor {

    private int health = Constants.TILE_HEALTH;
    private int fireCounter = Constants.FIRE_DELAY;
    private boolean burning = false;

    public void act() {
        if (burning)
            fireCounter--;

        if (fireCounter == 0) {
            fireCounter = Constants.FIRE_DELAY;

            this.health--;
            double percentHealth = (double) health / 10;

            // Spread fire to neighbours if under health threshold
            if (percentHealth <= Constants.SPREAD_THRESHOLD)
                for (Tile tile : getNeighbours(1, false, Tile.class))
                    tile.setBurning(true);

            // Change texture based on health
            GreenfootImage img = getImage();
            if (percentHealth <= 0)
                getWorld().removeObject(this);
            else if (percentHealth <= 0.25)
                img.drawImage(Constants.OVERLAY4, 0, 0);
            else if (percentHealth <= 0.5)
                img.drawImage(Constants.OVERLAY3, 0, 0);
            else if (percentHealth <= 0.75)
                img.drawImage(Constants.OVERLAY2, 0, 0);
            else if (percentHealth <= 1.0)
                img.drawImage(Constants.OVERLAY1, 0, 0);
        }
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }

}
