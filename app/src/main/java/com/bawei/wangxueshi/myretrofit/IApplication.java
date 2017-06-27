package com.bawei.wangxueshi.myretrofit;

import android.app.Application;

public class IApplication extends Application {


    public static IApplication application ;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this ;


    }
}