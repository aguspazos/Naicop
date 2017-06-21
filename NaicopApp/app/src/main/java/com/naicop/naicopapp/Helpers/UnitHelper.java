package com.naicop.naicopapp.Helpers;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by pazos on 21-Jun-17.
 */
public class UnitHelper {
    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics =context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
