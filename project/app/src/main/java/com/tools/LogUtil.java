package com.tools;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by he_p on 2018/6/6.
 */

public class LogUtil {
    private static final String sTag = "Module_Tree";
    private static final String sException = "exception  ";

    public interface LevelEnum {
        int Level_Default = 0;
        int Level_waring = 1;
        int Level_error = 2;
    }

    private static int mLogLevel = LevelEnum.Level_Default;

    public static void setLogLevel(final int level) {
        mLogLevel = level;
    }

    public static void printLog(String msg) {
        if (mLogLevel > LevelEnum.Level_Default) {
            return;
        }

        if (TextUtils.isEmpty(msg)) {
            return;
        }

        Log.e(sTag, msg);
    }

    public static void printExceptionLog(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        Log.e(sTag, sException + msg);
    }
}
