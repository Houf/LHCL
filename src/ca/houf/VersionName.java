package ca.houf;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class VersionName
{
    public static String getVersionName(final Context ctx)
    {
        try
        {
            return ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
        }
        catch(final NameNotFoundException e)
        {
            return "Unknown version";
        }
    }
}
