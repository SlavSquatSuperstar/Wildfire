public class Counter {

    private int max, value;
    public boolean started;

    public Counter(int max, boolean started) {
        this.started = started;
        setMax(max);
        reset();
    }

    public void count() {
        if (started)
            value--;
    }

    public int value() {
        return value;
    }

    public void reset() {
        value = max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
