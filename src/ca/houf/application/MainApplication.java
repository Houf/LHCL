package ca.houf.application;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import ca.houf.debug.ErrorCatcher;

public class MainApplication extends Application implements ActivityLifecycleCallbacks
{
    @SuppressWarnings("unused")
    private ErrorCatcher errorCatcher;

    private final static String APPLICATION_DIRECTORY = "/mnt/sdcard/lhcl/";

    @Override
    public void onCreate()
    {
        super.onCreate();

        errorCatcher = new ErrorCatcher(getApplicationContext(), APPLICATION_DIRECTORY);

        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(final Activity activity, final Bundle savedInstanceState){}
    @Override
    public void onActivityStarted(final Activity activity){}
    @Override
    public void onActivityResumed(final Activity activity){}
    @Override
    public void onActivityPaused(final Activity activity){}
    @Override
    public void onActivityStopped(final Activity activity){}
    @Override
    public void onActivitySaveInstanceState(final Activity activity, final Bundle outState){}
    @Override
    public void onActivityDestroyed(final Activity activity){}
}
