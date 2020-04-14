package com.example.mvp_1.app;

import android.app.Activity;
import android.app.Application;

import com.example.mvp_1.di.component.DaggerAppComponent;
import com.example.mvp_1.di.module.AppModule;

import java.util.HashSet;
import java.util.Set;

public class App extends Application {
    private static App mInstance;
    private Set<Activity> mActivities;
    private static DaggerAppComponent mDaggerAppCompat;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initInject();
    }
    //创建一个注入的方法
    private void initInject() {
        if (mDaggerAppCompat == null)
            mDaggerAppCompat = (DaggerAppComponent) DaggerAppComponent.builder().
                    appModule(new AppModule(this)).build();
    }
    public static DaggerAppComponent daggerAppComponent() {
        return mDaggerAppCompat;
    }
    public static synchronized App getInstance(){
        return mInstance;
    }
    public void addActivity(Activity activity){
        if (mActivities == null)
            mActivities = new HashSet<Activity>();
        mActivities.add(activity);
    }
    public void romeActivity(){
        if (mActivities != null){
            for (Activity mActivity : mActivities) {
                mActivity.finish();
            }
        }
        //android.os.Process.killProcess(Process.myPid());
        System.exit(0);

    }
}
