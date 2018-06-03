package com.application;

import android.app.Application;

/**
 * Created by tony on 2018/5/15.
 */

public class BaseApplication extends Application {
    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Application getCurrentApplication() {
        return mApplication;
    }
}
