package wildfire;

import greenfoot.Greenfoot;

import java.util.IllegalFormatException;

/**
 * A helper class for miscellaneous functions.
 */
public final class Util {

    private static final long START_TIME = System.currentTimeMillis();

    private Util() {}

    /**
     * @return the time in seconds since the simulation started.
     */
    public static double currentTime() {
        return (System.currentTimeMillis() - START_TIME) / 1e3;
    }

    /**
     * Prints a message to the console.
     */
    public static void log(Object msg, Object... args) {
        try {
            System.out.printf("%s\n", String.format(msg.toString(), args));
        } catch (NullPointerException e) {
            System.out.println("null");
        } catch (IllegalFormatException e) {
            System.out.println("Error formatting message");
        }
    }

    /**
     * Generates a random integer.
     * 
     * @param min the lower bound (inclusive).
     * @param max the upper bound (exclusive).
     */
    public static int random(int min, int max) {
        return Greenfoot.getRandomNumber(max - min) + min;
    }

}
