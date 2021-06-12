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
    public static final GreenfootImage BRIDGE_TILE = createTexture("tiles/wood_tile.png");
    public static final GreenfootImage ENDPOINT = createTexture("tiles/grass_tile.png");
    public static final GreenfootImage OBSTACLE = createTexture("tiles/boulder.png");
    public static final GreenfootImage PREVIEW_TILE = createTexture("tiles/preview_tile.png");

    // Tile Overlays
    public static final GreenfootImage FLAME = createTexture("overlays/flame.png");
    public static final GreenfootImage OVERLAY1 = createTexture("overlays/damaged1.png");
    public static final GreenfootImage OVERLAY2 = createTexture("overlays/damaged2.png");
    public static final GreenfootImage OVERLAY3 = createTexture("overlays/damaged3.png");
    public static final GreenfootImage OVERLAY4 = createTexture("overlays/damaged4.png");
    public static final GreenfootImage WATER_OVERLAY = createTexture("overlays/water_overlay.png");

    // Gameplay Icons
    public static final GreenfootImage BUILD_READY = createTexture("icons/build_ready.png");
    public static final GreenfootImage BUILD_WAIT1 = createTexture("icons/build_wait1.png");
    public static final GreenfootImage BUILD_WAIT2 = createTexture("icons/build_wait2.png");
    public static final GreenfootImage ADRENALINE_ACTIVE = createTexture("icons/adrenaline_active.png");
    public static final GreenfootImage ADRENALINE_IDLE = createTexture("icons/adrenaline_idle.png");
    public static final GreenfootImage RAIN_READY = createTexture("icons/rain_ready.png");
    public static final GreenfootImage RAIN_WAIT = createTexture("icons/rain_wait.png");
    public static final GreenfootImage RAIN_ACTIVE = createTexture("icons/rain_active.png");

    // Text Banners
    public static final GreenfootImage WIN_MSG = createTexture("win_message.png");
    public static final GreenfootImage LOSE_MSG = createTexture("lose_message.png");

}
