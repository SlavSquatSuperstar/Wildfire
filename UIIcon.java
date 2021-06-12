import greenfoot.*;

public class UIIcon extends Actor {
    
    public UIIcon(GreenfootImage image) {
        setImage(image);
    }
    
    public void setVisible(boolean visible) {
        getImage().setTransparency(visible ? 255 : 0);
    }
    
}
