import greenfoot.*;

public final class Constants {

    // TODO read from config file
    private Constants() {}

    // World Parameters
    public static final int WORLD_WIDTH = 20;
    public static final int WORLD_HEIGHT = 15;
    public static final int TILE_SIZE = 32;
    public static final int TILE_HEALTH = 12;
    public static final int OBSTACLE_COUNT = 3;
    public static final int WORLDGEN_BUFFER = 2;

    // Build Parameters
    public static final int BUILD_COOLDOWN = 60; // How long to wait until placing/breaking blocks
    public static final double ADRENALINE_THRESHOLD = 0.3; // When to apply "adrenaline" (% bridge burning)
    public static final double ADRENALINE_REDUCTION = 0.5; // How much to reduce build time when under adrenaline

    // Fire Spread Parameters
    public static final int FIRE_DELAY = 70; // How long to wait spread fire
    public static final double SPREAD_THRESHOLD = 0.75; // When to spread fire (% health remaining)
    public static final int METEOR_DELAY = FIRE_DELAY * 5; // How long to wait create a meteor event
    public static final int METEOR_SPEED = 8; // How long to wait to move the flame particles
    public static final int METEOR_STRENGTH = 2; // How many tiles the flame particles can ignite
    
    // Extinguish Parameters
    public static final int EXTINGUISH_COOLDOWN = FIRE_DELAY * 3;
    public static final int WET_DURATION = FIRE_DELAY * 2; // How long to wait till water dries

    // Tile Textures   
    public static final GreenfootImage FLAME = new GreenfootImage("flame.png");
    public static final GreenfootImage BRIDGE_TILE = new GreenfootImage("wood_tile.png");
    public static final GreenfootImage OVERLAY1 = new GreenfootImage("fire_overlay1.png");
    public static final GreenfootImage OVERLAY2 = new GreenfootImage("fire_overlay2.png");
    public static final GreenfootImage OVERLAY3 = new GreenfootImage("fire_overlay3.png");
    public static final GreenfootImage OVERLAY4 = new GreenfootImage("fire_overlay4.png");
    public static final GreenfootImage WATER_OVERLAY = new GreenfootImage("water_overlay.png");

    // UI Textures   
    public static final GreenfootImage BUILD_READY = new GreenfootImage("build_icon_ready.png");
    public static final GreenfootImage BUILD_WAIT = new GreenfootImage("build_icon_wait.png");
    public static final GreenfootImage BUILD_EMPTY = new GreenfootImage("build_icon_empty.png");
    public static final GreenfootImage ADRENALINE = new GreenfootImage("adrenaline_icon.png");
    public static final GreenfootImage EXTINGUISH = new GreenfootImage("raincloud.png");

}
