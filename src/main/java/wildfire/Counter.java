package wildfire;

/**
 * An game component used to time events.
 */
public class Counter {

    private int max, value;
    /**
     * Whether the timer should be counting.
     */
    public boolean started;

    public Counter(int max, boolean started) {
        this.started = started;
        setMax(max);
        reset();
    }

    /**
     * Counts down the value if the timer is started.
     */
    public void count() {
        value = started ? --value : value;
    }

    /**
     * The current amount of counts left until the timer is ready.
     */
    public int value() {
        return value;
    }

    /**
     * Reverts the timer's count to its original value.
     */
    public void reset() {
        value = max;
    }

    /**
     * Sets the amount the timer should count down from.
     * 
     * @param max the number of game ticks the timer should wait until ready.
     */
    public void setMax(int max) {
        this.max = max;
    }
}
