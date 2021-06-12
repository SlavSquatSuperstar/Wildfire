package wildfire.actors;

import greenfoot.*;
import wildfire.Assets;
import wildfire.Constants;
import wildfire.Counter;

/**
 * An interactable image that provides a cooldown.
 */
public class Powerup extends UIIcon {

    private Counter cooldown;
    private State state = State.WAIT;

    public Powerup() {
        super(State.WAIT.icon);
        cooldown = new Counter(Constants.RAIN_COOLDOWN, true);
    }

    public void act() {
        cooldown.count();

        switch (state) {
            case WAIT:
                if (cooldown.value() <= 0)
                    state = State.READY;
                break;

            case READY:
                if (Greenfoot.mouseClicked(this) || Greenfoot.isKeyDown("Space"))
                    state = State.ACTIVE;
                break;
        }

        setImage(state.icon);
    }

    /**
     * @return Whether the powerup has finished its cooldown.
     */
    public boolean isActive() {
        return state == State.ACTIVE;
    }

    public void reset() {
        cooldown.reset();
        state = State.WAIT;
    }

    private enum State {
        READY(Assets.RAIN_READY),
        ACTIVE(Assets.RAIN_ACTIVE),
        WAIT(Assets.RAIN_WAIT);

        GreenfootImage icon;

        State(GreenfootImage icon) {
            this.icon = icon;
        }
    }

}
