import greenfoot.*;

import java.util.IllegalFormatException;

public final class Util {

    private static long startTime = System.currentTimeMillis();

    private Util() {}

    public static long currentTime() {
        return System.currentTimeMillis() - startTime;
    }

    public static void log(Object msg, Object... args) {
        try {
            System.out.printf("%s\n", String.format(msg.toString(), args));
        } catch (NullPointerException e) {
            System.out.println("null");
        } catch (IllegalFormatException e) {
            System.out.println("Error formatting message");
        }
    }

    public static int random(int min, int max) {
        return Greenfoot.getRandomNumber(max - min) + min;
    }

}
