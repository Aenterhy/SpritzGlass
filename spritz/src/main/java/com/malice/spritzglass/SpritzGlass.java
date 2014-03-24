package com.malice.spritzglass;

import android.app.Application;

import com.bugsnag.android.Bugsnag;

/**
 * @author Aenterhy.
 */
public class SpritzGlass extends Application {
    private static SpritzGlass instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Bugsnag.register(this, "4ffe22aacb5a689629d09ba1060a8132");
    }

    public static SpritzGlass getInstance() {
        return instance;
    }
}
