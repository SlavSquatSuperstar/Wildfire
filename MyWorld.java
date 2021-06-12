import greenfoot.*;
import java.util.List;
import java.util.ArrayList;

public class MyWorld extends World {

    private int fireCounter = Constants.FIRE_DELAY;
    private boolean fireStarted = false;

    public MyWorld() {    
        super(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, Constants.TILE_SIZE); 
        Util.log("Game starting");
        addTile(new Tile(), 0, getHeight() / 2);
    }

    public void act() {
        if (checkForWin())
            winGame();

        if (numberOfObjects() == 0)
            loseGame();

        fireCounter--;
        if (fireCounter == 0) {
            fireCounter = Constants.FIRE_DELAY;

            // Initial round of fire (maybe do periodically instead)
            if (!fireStarted) {  
                fireStarted = true;

                // Set left most tiles on fire
                for (int y = 0; y < getHeight(); y++) {
                    for (int x = 0; x < getWidth(); x++) {
                        Tile  leftMost = getTile(Tile.class, x, y);
                        if (leftMost != null)
                            leftMost.setBurning(true);
                    }
                }
            }
        }

        if (Greenfoot.mouseClicked(null)) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            int x = mouse.getX();
            int y = mouse.getY();

            if (mouse.getButton() == 1 && checkAdjacent(x, y)) {
                // Need more forgiving click detection
                // Only place if touching a cell
                // Shouldn't be able to place off end (unlock when reach first time)
                addTile(new Tile(), x, y);
            }   
            else if (mouse.getButton() == 3) {
                removeTile(x, y);
                // Should only remove if on fire
            }
        }
    }

    // Game Methods

    /**
     * Checks if the game has been won.
     * Obvious flaw: only checks that both edges are populated.
     * Exploit: the middle can contain vacant spaces.
     */
    private boolean checkForWin() {
        boolean[] edges = new boolean[2];
        int[] xCoords = {0, getWidth() - 1};

        for (int i = 0; i < xCoords.length; i++) {
            for (int y = 0; y < getHeight(); y++) {
                if (cellOccupied(xCoords[i], y)) {
                    edges[i] = true;
                    break;
                }
            }
        }

        return edges[0] && edges[1];
    }

    public void winGame() {
        showText("You Escaped the Wildfire!", getWidth() / 2, getHeight() / 2);
        Util.log("Game won");
        Greenfoot.stop();
    }

    public void loseGame() {
        showText("The Wildfire Consumed You!", getWidth() / 2, getHeight() / 2);
        Util.log("Game lost");
        Greenfoot.stop();
    }

    // Tile Methods

    // TODO use Class.instantiate() instead?
    public void addTile(Tile tile, int x, int y) {
        if (!cellOccupied(x, y)) { // Only place if cell is empty
            addObject(tile, x, y);
            Util.log("Added tile at %d, %d", x, y);
        }
    }

    public void removeTile(int x, int y) {
        if (cellOccupied(x, y)) {
            removeObjects(getObjectsAt(x, y, Tile.class));
            Util.log("Removed tile at %d, %d", x, y);
        }
    }

    private boolean checkAdjacent(int x, int y) {
        int[][] coords = {{x, y-1}, {x, y+1}, {x-1, y}, {x+1, y}};
        for (int i = 0; i < coords.length; i++)
            if (cellOccupied(coords[i][0], coords[i][1])) 
                return true;
        return false;
    }

    private <T extends Tile> T getTile(Class<T> tileClass, int x, int y) {
        List<T> tilesAtPos = getObjectsAt(x, y, tileClass);
        if (tilesAtPos.size() > 0)
            return tilesAtPos.get(0); // Should only exist one
        return null;
    }

    private boolean cellOccupied(int x, int y) {        
        //return getObjectsAt(x, y, Tile.class).size() > 0; 
        return getTile(Tile.class, x, y) != null;
    }

}
