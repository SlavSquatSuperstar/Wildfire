package wildfire;

import greenfoot.GreenfootImage;

/**
 * A storage class for game textures.
 */
public final class Assets {

    private Assets() {}

    private static GreenfootImage createTexture(String filename) {
        return new GreenfootImage(filename);
    }

    // Tile Textures
    public static final GreenfootImage BRIDGE_TILE = createTexture("assets/tiles/wood_tile.png");
    public static final GreenfootImage ENDPOINT = createTexture("assets/tiles/grass_tile.png");
    public static final GreenfootImage OBSTACLE = createTexture("assets/tiles/boulder.png");
    public static final GreenfootImage PREVIEW_TILE = createTexture("assets/tiles/preview_tile.png");

    // Tile Overlays
    public static final GreenfootImage FLAME = createTexture("assets/overlays/flame.png");
    public static final GreenfootImage OVERLAY1 = createTexture("assets/overlays/damaged1.png");
    public static final GreenfootImage OVERLAY2 = createTexture("assets/overlays/damaged2.png");
    public static final GreenfootImage OVERLAY3 = createTexture("assets/overlays/damaged3.png");
    public static final GreenfootImage WATER_OVERLAY = createTexture("assets/overlays/water_overlay.png");

    // Gameplay Icons
    public static final GreenfootImage BUILD_READY = createTexture("assets/icons/build_ready.png");
    public static final GreenfootImage BUILD_WAIT1 = createTexture("assets/icons/build_wait1.png");
    public static final GreenfootImage BUILD_WAIT2 = createTexture("assets/icons/build_wait2.png");
    public static final GreenfootImage ADRENALINE_ACTIVE = createTexture("assets/icons/adrenaline_active.png");
    public static final GreenfootImage ADRENALINE_IDLE = createTexture("assets/icons/adrenaline_idle.png");
    public static final GreenfootImage RAIN_READY = createTexture("assets/icons/rain_ready.png");
    public static final GreenfootImage RAIN_WAIT = createTexture("assets/icons/rain_wait.png");
    public static final GreenfootImage RAIN_ACTIVE = createTexture("assets/icons/rain_active.png");

    // Text Banners
    public static final GreenfootImage WIN_MSG = createTexture("assets/win_message.png");
    public static final GreenfootImage LOSE_MSG = createTexture("assets/lose_message.png");

}
