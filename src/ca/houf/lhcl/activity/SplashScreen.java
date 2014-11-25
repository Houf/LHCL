package ca.houf.lhcl.activity;

import roboguice.activity.RoboActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import ca.houf.lhcl.R;

public class SplashScreen extends RoboActivity
{
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        splashDisplayTimeout();
    }

    private void splashDisplayTimeout()
    {
        final int SPLASH_TIME_OUT = 10000;
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //startActivity(new Intent(SplashScreen.this, ItemListActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
