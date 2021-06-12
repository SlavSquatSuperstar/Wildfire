package wildfire.actors;

import greenfoot.*;

/**
 * An image in the world that does not interact with other objects.
 */
public class UIIcon extends Actor {

    public UIIcon(GreenfootImage image) {
        setImage(image);
    }

    public void setVisible(boolean visible) {
        getImage().setTransparency(visible ? 255 : 0);
    }

}
