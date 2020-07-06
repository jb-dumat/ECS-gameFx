package utils;

/**
 * A simple clock class
 */
public class Clock {
    public Clock() { this.clock = java.lang.System.currentTimeMillis(); }

    public void restart() {
        clock = java.lang.System.currentTimeMillis();
    }

    public long getDuration() {
        return java.lang.System.currentTimeMillis() - clock;
    }

    private long clock;
}
