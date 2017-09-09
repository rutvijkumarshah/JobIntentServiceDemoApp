package app.jobintentservicedemoapp;

/**
 * Created by Rutvijkumar Shah on 9/8/17.
 */

public class AppState {

    private static boolean isAppBackground;

    public static boolean isIsAppBackground() {
        return isAppBackground;
    }

    public static void setIsAppBackground(boolean isAppBackground) {
        AppState.isAppBackground = isAppBackground;
    }
}
