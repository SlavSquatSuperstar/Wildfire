
import greenfoot.*;

public class Powerup extends UIIcon {

    private Counter cooldown;
    private boolean active;

    public Powerup(GreenfootImage image) {
        super(image);
        setVisible(false);
        cooldown = new Counter(Constants.EXTINGUISH_COOLDOWN, true);
    }

    public void act() {
        cooldown.count();
        if (cooldown.value() <= 0) {
            setVisible(true);
            if (Greenfoot.mouseClicked(this))
                active = true;
        } else {
            setVisible(false);
        }

    }

    public boolean isActive() {
        return active;
    }

    public void reset() {
        cooldown.reset();
        active = false;
        setVisible(false);
    }

}
