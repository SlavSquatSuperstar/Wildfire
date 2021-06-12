import greenfoot.*;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

public class MyWorld extends World {

    // Counters
    private Counter fireCounter, buildCounter;
    private boolean firstMeteor;

    // Tiles
    private Endpoint start, finish;
    private Buildable lastNearest;

    // Icons
    private UIIcon buildIcon, adrenalineIcon;
    private Powerup rainIcon;

    public MyWorld() {
        super(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, Constants.TILE_SIZE);
        Util.log("===========================");
        Util.log("Game starting. Place any tile!");
        init();
    }

    // Set up the stage
    private void init() {
        buildCounter = new Counter(0, false);
        fireCounter = new Counter(Constants.METEOR_DELAY, false);

        // Create world generator
        WorldGenerator gen = new WorldGenerator(getWidth(), getHeight());

        // Create UI elements
        addObject(buildIcon = new UIIcon(Constants.BUILD_READY), getWidth() - 2, getHeight() - 1);
        addObject(adrenalineIcon = new UIIcon(Constants.ADRENALINE), buildIcon.getX() - 2, buildIcon.getY());
        adrenalineIcon.setVisible(false);
        addObject(rainIcon = new Powerup(Constants.EXTINGUISH), adrenalineIcon.getX() - 2, buildIcon.getY());

        // Add start and finish tiles
        addObject(start = new Endpoint(false), gen.getStart().x, gen.getStart().y);
        addObject(finish = new Endpoint(true), gen.getFinish().x, gen.getFinish().y);

        // Add random obstacles
        int added = 0;
        for (Point p : gen.getObstacleLocations().keySet()) {
            if (Greenfoot.getRandomNumber(100) < gen.getObstacleLocations().get(p)) {
                addObject(new Obstacle(), p.x, p.y);
                if (++added >= Constants.OBSTACLE_COUNT)
                    break;
            }
        }
    }

    @Override
    public void act() {
        buildCounter.count();
        fireCounter.count();

        // Show the player valid build spots
        if (Greenfoot.mouseMoved(null))
            showBuildPreview(Greenfoot.getMouseInfo());

        // Check for player input
        if (buildCounter.value() <= 0) { // Only allow buidling/breaking tiles once cooldown reached
            buildIcon.setImage(Constants.BUILD_READY);
            if (Greenfoot.mouseClicked(null))
                buildTile(Greenfoot.getMouseInfo());
        } else if (buildCounter.value() <= Constants.BUILD_COOLDOWN / 2) {
            buildIcon.setImage(Constants.BUILD_WAIT);
        } else {
            buildIcon.setImage(Constants.BUILD_EMPTY);
        }

        if (!buildCounter.started)
            return;

        // Win the game if the start and end are connected
        if (checkForWin())
            winGame();

        // Lose if all the tiles have been burned
        // Flaw: can still lose the game if no tiles placed or if break all tiles
        // TODO: confirmation message: do you want to break your last tile?
        if (fireCounter.started && getObjects(BridgeTile.class).isEmpty()) //
            loseGame();

        // Spread the fire
        if (fireCounter.value() <= 0) {
            createMeteorEvent();
            fireCounter.reset();
        }
    }

    private void showBuildPreview(MouseInfo mouse) {
        if (mouse == null) // Sometimes when pressing "Act", Greenfoot returns a null MouseInfo object
            return;

        int x = mouse.getX();
        int y = mouse.getY();

        Buildable nearest = null;
        double min = Integer.MAX_VALUE;
        for (Buildable tile : getObjects(Buildable.class)) {
            if (tile instanceof Endpoint && ((Endpoint) tile).locked)
                continue;
            // Don't count if tile completely blocked
            double distance = tile.getDistance(new Point(x, y));
            if (distance < min){
                min = distance;
                nearest = tile;
            }
        }

        if (nearest.equals(lastNearest))
            return;

        lastNearest = nearest;

        removeObjects(getObjects(BuildPreview.class));
        if (lastNearest != null)
            lastNearest.showBuildableSpots();
    }

    private void buildTile(MouseInfo mouse) {
        if (mouse == null) // Sometimes when pressing "Act", Greenfoot returns a null MouseInfo object
            return;

        int x = mouse.getX();
        int y = mouse.getY();
        boolean worldChanged = false; // Only reset build cooldown if a block was modified

        // When mouse clicked on a tile
        if (mouse.getButton() == 1) {  
            // If the powerup is active, extinguish the tile at this cell if it exists
            if (rainIcon.isActive() && getObject(x, y, BridgeTile.class) != null) {
                getObject(x, y, BridgeTile.class).setState(BridgeTile.State.WET);
                rainIcon.reset();
                worldChanged = true;
            }
            // Place a tile if clicking an empty cell and a buildable is nearby
            if (checkAdjacent(x, y) && getObject(x, y, Tile.class) == null) {
                addObject(new BridgeTile(), x, y);
                worldChanged = true;
            }
        } else if (mouse.getButton() == 3) {
            BridgeTile tileAtPos = getObject(x, y, BridgeTile.class);
            // If a tile exists at this cell, then remove it unless it is burning
            if (tileAtPos != null && !tileAtPos.isBurning()) {
                removeObject(tileAtPos);
                worldChanged = true;
            }
        }

        if (!worldChanged)
            return;

        if (!buildCounter.started) {
            buildCounter.setMax(Constants.BUILD_COOLDOWN);
            buildCounter.started = true;
            fireCounter.started = true;
            Util.log("The race is on! Cross the gap!");
        }

        // Apply cooldown buff based on amount of bridge burning
        int burningCount = 0;
        List<BridgeTile> tiles = getObjects(BridgeTile.class);
        for (BridgeTile tile : tiles) {
            if (tile.isBurning())
                burningCount++;
        }

        // Reset build timer
        if (burningCount / (double) tiles.size() >= Constants.ADRENALINE_THRESHOLD) {
            buildCounter.setMax((int) (Constants.BUILD_COOLDOWN * Constants.ADRENALINE_REDUCTION));
            adrenalineIcon.setVisible(true);
        }
        else {
            buildCounter.setMax(Constants.BUILD_COOLDOWN);
            adrenalineIcon.setVisible(false);
        }
        buildCounter.reset();

        // Show build preview
        showBuildPreview(mouse);
    }

    // Game Methods

    private void createMeteorEvent() {
        // Create initial round of fire
        if (!firstMeteor) {
            firstMeteor = true;
            Util.log("The wildfire is spreading! Can you escape in time?");

            // Set all tiles in the leftmost column on fire
            boolean found = false;
            for (int x = 0; x < getWidth(); x++) {
                for (int y = 0; y < getHeight(); y++) {
                    if (getObject(x, y, BridgeTile.class) != null) {
                        found = true;
                    }
                }

                if (found) {
                    addObject(new Flame("up"), x, start.getY());
                    addObject(new Flame("down"), x, start.getY());
                    break;
                }
            }
        } else {
            // Generate random waves of fire
            ArrayList<BridgeTile> tiles = new ArrayList<>();
            tiles.addAll(getObjects(BridgeTile.class));
            int r = Greenfoot.getRandomNumber(tiles.size());
            int x = tiles.get(r).getX() + Util.random(-Constants.WORLDGEN_BUFFER, Constants.WORLDGEN_BUFFER);
            int y = tiles.get(r).getY() + Util.random(-Constants.WORLDGEN_BUFFER, Constants.WORLDGEN_BUFFER);

            if (Util.random(0, 100) < 80) {
                addObject(new Flame("up"), x, y);
                addObject(new Flame("down"), x, y);
            } else {
                addObject(new Flame("left"), x, y);
                addObject(new Flame("right"), x, y);
            }
        }
    }

    /*
     * A* Pathfinding Algorithm
     * Source: https://www.youtube.com/watch?v=-L-WgKMFuhE
     */
    private boolean checkForWin() {
        if (getObjects(Buildable.class).size() < (int) start.getDistance(finish))         // Skip if too few tiles
            return false;
        if (!(start.hasNeighbour() && finish.hasNeighbour())) // Skip if both sides have no neighbours
            return false;

        List<Buildable> open = new ArrayList<>();
        List<Buildable> closed = new ArrayList<>();
        open.add(start);

        Buildable current = start;
        while (!open.isEmpty()) {
            // Set current to the open tile with the lowest f-cost
            int min = Integer.MAX_VALUE;
            for (Buildable tile : open) {
                int f_cost = tile.getFCost(start, finish);
                if (f_cost < min) {
                    min = f_cost;
                    current = tile;
                }
            }

            // Stop searching if we found the end
            if (current.equals(finish))
                return true;

            open.remove(current);
            closed.add(current);

            // Add all the non-closed neighbours to open
            List<Buildable> nbrs = current.getNeighbours();
            nbrs.removeAll(closed);
            open.addAll(nbrs);
        }

        return false;
    }

    private void winGame() {
        showText("You Escaped the Wildfire!", getWidth() / 2, getHeight() / 2);
        /*TextBanner winMsg = new TextBanner();
        winMsg.setImage("win.png");
        addObject(winMsg, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);*/
        Util.log("Game won! Can you beat your time?");
        Greenfoot.stop();
    }

    private void loseGame() {
        showText("The Wildfire Consumed You!", getWidth() / 2, getHeight() / 2);
        Util.log("Game lost! Better luck next time!");
        Greenfoot.stop();
    }

    @Override
    public void stopped() {
        // Util.log("Time: %.1fsec", (double) Util.currentTime() / 1000.0);
        // Still off by magnitude
    }

    // Tile Methods

    private boolean checkAdjacent(int x, int y) {
        int[][] coords = {{x, y-1}, {x, y+1}, {x-1, y}, {x+1, y}};
        for (int i = 0; i < coords.length; i++) {
            Buildable tile = getObject(coords[i][0], coords[i][1], Buildable.class);
            if (tile != null) { // Check if has neighbouring tiles
                if (tile instanceof Endpoint && ((Endpoint) tile).locked)
                    continue;
                return true;
            }
        }
        return false;
    }

    private <T extends Actor> T getObject(int x, int y, Class<T> cls) {
        List<T> objectsAtPos = getObjectsAt(x, y, cls);
        if (!objectsAtPos.isEmpty())
            return objectsAtPos.get(0); // Should only exist one, so just return the first
        return null;
    }

}
