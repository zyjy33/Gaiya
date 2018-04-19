package com.yunsen.gaea.gaea;

import android.app.Application;

import com.yunsen.gaea.gaea.utils.WebUitls;

/**
 * Created by yunsenA on 2018/4/18.
 */

public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WebUitls.init(this);
    }
}
