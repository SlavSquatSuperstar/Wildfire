import greenfoot.*;

public class Endpoint extends Buildable {
    public boolean locked;
    
    public Endpoint(boolean locked) {
        this.locked = locked;
    }
    
    @Override
    public void act() {
        // Prevent building on a locked tile until it has been reached
        if (locked && hasNeighbour())
            locked = false;
    }
}