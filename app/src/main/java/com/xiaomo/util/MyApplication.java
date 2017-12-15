package com.xiaomo.util;


import android.app.Application;

import com.xiaomo.chcarappnew.activity.LoginActivity;
import com.xiaomo.chcarappnew.activity.MainActivity;
import com.zxy.recovery.core.Recovery;

/**
 * Created by Stefan on 17/11/23.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .recoverEnabled(true)
                //.callback(new MyCrashCallback())
                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .skip(LoginActivity.class)
                .init(this);
    }

}
