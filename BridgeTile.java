import greenfoot.*;

public class BridgeTile extends Buildable {

    private int health = Constants.TILE_HEALTH;
    private int actCounter;
    private State state;

    @Override
    public void act() {        
        if (state == State.WET && --actCounter <= 0)
            setState(State.NORMAL);

        if (isBurning()) {
            if (--actCounter <= 0) {          
                actCounter = Constants.FIRE_DELAY;
                this.health--;

                // Remove if burnt up
                if (percentHealth() <= 0) {
                    getWorld().removeObject(this);
                    return;
                }

                // Spread fire to neighbours if under health threshold
                if (percentHealth() <= Constants.SPREAD_THRESHOLD)
                    for (BridgeTile tile : getNeighbours(1, false, BridgeTile.class))
                        if (tile.state == State.NORMAL)
                            tile.setState(State.BURNING);

                updateImage();
            }
        }
    }

    private double percentHealth() {
        return (double) health / Constants.TILE_HEALTH;
    }

    public boolean isBurning() {
        return state == State.BURNING;
    }

    public void setState(State state) {
        this.state = state;
        if (state == State.WET)
            actCounter = Constants.WET_DURATION;
        else
            actCounter = Constants.FIRE_DELAY;
        updateImage();
    }

    private void updateImage() {
        // Set a fresh image
        setImage(Constants.BRIDGE_TILE);

        // Draw the damage overlay
        if (percentHealth() <= 0.25)
            drawImage(Constants.OVERLAY3);
        if (percentHealth() <= 0.5)
            drawImage(Constants.OVERLAY2);
        if (percentHealth() <= 0.75)
            drawImage(Constants.OVERLAY1);

        // Draw the status overlay
        if (state == State.BURNING) 
            drawImage(Constants.FLAME);
        else if (state == State.WET)
            drawImage(Constants.WATER_OVERLAY);
    }

    private void drawImage(GreenfootImage img) {
        getImage().drawImage(img, 0, 0);
    }

    public static enum State {
        NORMAL, BURNING, WET;
    }

}
