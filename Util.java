public final class Util {
    private Util() {}

    public static void log(Object msg, Object... args) {
        try {
            System.out.println(String.format("%s", String.format(msg.toString(), args)));
        } catch (Exception e) {
            System.out.println("Error logging message");
        }
    }

}
