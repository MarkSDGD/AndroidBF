package com.sdsjt.app;


import com.socks.library.KLog;
import com.sdsjt.BuildConfig;
import com.sdsjt.app.base.BaseApp;


public class MyApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.DEBUG, "MARK");//初始化KLog
    }


}





