package ca.houf.lhcl.activity;

import roboguice.activity.RoboActivity;
import android.os.Bundle;
import ca.houf.lhcl.R;

public class MainActivity extends RoboActivity
{
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_screen);
    }
}
