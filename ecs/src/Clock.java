public class Clock {
    Clock() { this.clock = java.lang.System.currentTimeMillis(); }

    public void restart() {
        clock = java.lang.System.currentTimeMillis();
    }

    public long getDuration() {
        return java.lang.System.currentTimeMillis() - clock;
    }

    private long clock;
}
