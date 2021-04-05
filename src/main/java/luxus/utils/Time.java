package luxus.utils;

public class Time {

    /**
     * Constant used to convert nanoseconds to seconds when a value
     * given in nanoseconds is multiplied by this value.
     */
    private static final double nanoToSeconds = 1E-9;

    private static float startedTime = System.nanoTime();

    public static float getTime() {
        return (float) ((System.nanoTime() - startedTime) * nanoToSeconds);
    }
}
