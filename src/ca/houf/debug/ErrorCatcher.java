package ca.houf.debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class ErrorCatcher implements Thread.UncaughtExceptionHandler
{
    private final Context applicationContext;
    private final String errorOutputDirectory;
    private final Thread.UncaughtExceptionHandler previousHandler;
    private final static String TAG = "Houf application error catcher";

    public ErrorCatcher(final Context context, final String folder)
    {
        final File folderToWriteTo = new File(folder);

        if(!folderToWriteTo.exists())
            folderToWriteTo.mkdirs();

        previousHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        applicationContext = context;
        errorOutputDirectory = folder;
    }

    public List<String> getErrorFileList()
    {
        final File localFile = new File(errorOutputDirectory + File.separator);
        localFile.mkdir();
        final String[] filesList = localFile.list(new FilenameFilter()
        {
            @Override
            public boolean accept(final File paramAnonymousFile, final String paramAnonymousString)
            {
                return paramAnonymousString.endsWith(".stacktrace");
            }
        });

        return filesList != null ? Arrays.asList(filesList) : new ArrayList<String>();
    }

    @Override
    public void uncaughtException(final Thread paramThread, final Throwable paramThrowable)
    {
        final Date localDate = new Date();
        final String str1 = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(
            String.valueOf(new StringBuilder(String.valueOf("")).append("Error Report collected on: ").append(localDate.toString()).append("\n\n")
                .toString())).append("Informations:\n").toString())).append("=============\n\n").toString())).append(createInformationString())
                .toString())).append("\n\nStack:\n").toString() + "======\n";

        final StringWriter localStringWriter = new StringWriter();
        final PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
        paramThrowable.printStackTrace(localPrintWriter);

        final String str2 = localStringWriter.toString();
        String str3 = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(str1)).append(str2)
            .toString())).append("\n").toString())).append("Cause:\n").toString() + "======\n";

        for(Throwable localThrowable = paramThrowable.getCause();; localThrowable = localThrowable.getCause())
        {
            if(localThrowable == null)
            {
                localPrintWriter.close();
                saveAsFile(str3 + "****  End of current Report ***");
                previousHandler.uncaughtException(paramThread, paramThrowable);
                return;
            }
            localThrowable.printStackTrace(localPrintWriter);
            str3 = str3 + localStringWriter.toString();
        }
    }

    private void saveAsFile(final String paramString)
    {
        try
        {
            final String str = "houf.stack-" + System.currentTimeMillis() + ".stacktrace";
            final FileOutputStream localFileOutputStream = new FileOutputStream(new File(errorOutputDirectory+str));
            localFileOutputStream.write(paramString.getBytes());
            localFileOutputStream.close();
            return;
        }
        catch(final IOException localIOException)
        {
            Log.e(TAG, "Exception occured in error catcher", localIOException);
        }
    }

    private String createInformationString()
    {
        String informationString = "";
        try
        {
            final PackageInfo localPackageInfo = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0);
            informationString = informationString + "Package: " + localPackageInfo.packageName + "\n";
            final String str = informationString + "Version: " + localPackageInfo.versionName.split(":")[0].trim() + " / " +
                localPackageInfo.versionCode + "\n";
            informationString = str;
        }
        catch(final PackageManager.NameNotFoundException localNameNotFoundException)
        {
            Log.e(TAG, "Exception occured in error catcher", localNameNotFoundException);
        }
        return new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String
            .valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String
                .valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String
                    .valueOf(informationString)).append("FilePath: ").append(errorOutputDirectory).append("\n").toString())).append("Board: ").append(Build.BOARD)
                    .append("\n").toString())).append("Brand: ").append(Build.BRAND).append("\n").toString())).append("Device: ")
                    .append(Build.DEVICE).append("\n").toString())).append("Model: ").append(Build.MODEL).append("\n").toString()))
                .append("Product: ").append(Build.PRODUCT).append("\n").toString())).append("Android Version: ").append(Build.VERSION.RELEASE)
                .append("\n").toString())).append("Build ID: ").append(Build.ID).append(" / ").append(Build.DISPLAY).append("\n").toString()))
            .append("Build Time: ").append(Build.TIME).append("\n").toString())).append("Tags: ").append(Build.TAGS).append("\n").toString()))
            .append("Type: ").append(Build.TYPE).append("\n").toString())).append("Total memory: ").append(getTotalInternalMemorySize()).append("\n")
            .toString() + "Available memory: " + getAvailableInternalMemorySize() + "\n";
    }

    private long getAvailableInternalMemorySize()
    {
        final StatFs localStatFs = new StatFs(Environment.getDataDirectory().getPath());
        return localStatFs.getBlockSizeLong() * localStatFs.getAvailableBlocksLong();
    }

    private long getTotalInternalMemorySize()
    {
        final StatFs localStatFs = new StatFs(Environment.getDataDirectory().getPath());
        return localStatFs.getBlockSizeLong() * localStatFs.getAvailableBlocksLong();
    }
}
