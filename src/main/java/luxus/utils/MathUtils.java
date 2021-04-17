package luxus.utils;

public class MathUtils {

    /**
     * Linearly interpolates between two float numbers by the interpolant.
     *
     * @param start the starting value.
     * @param end the desired ending value.
     * @param factor value used to interpolate between the start and the end numbers.
     * @return the interpolated value.
     */
    public static float lerp(float start, float end, float factor) {
        return (start * (1.0f - factor)) + (end * factor);
    }

}
