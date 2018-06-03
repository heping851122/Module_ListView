package com.tools;

import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.application.BaseApplication;


/**
 * Created by tony on 16/11/19.
 */

public class CommonTools {

    public static int getPixeFromDip(float f) {
        DisplayMetrics dm = BaseApplication.getCurrentApplication().getResources().getDisplayMetrics();
        return getPixelFromDip(dm, f);
    }

    public static int getScreenWidth() {
        DisplayMetrics dm = BaseApplication.getCurrentApplication().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics dm = BaseApplication.getCurrentApplication().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    private static int getPixelFromDip(DisplayMetrics dm, float dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm) + 0.5f);
    }
}
