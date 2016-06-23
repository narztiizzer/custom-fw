package plnr.custom.framework.core;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import plnr.custom.framework.ServicesMapping;
import plnr.custom.framework.core.base.ui.listview.BaseViewClassHolder;


/**
 * Created by ponlavitlarpeampaisarl on 2/3/15 AD.
 */
public abstract class BaseApplication extends Application {
    private static Context mContext;
    private static BaseApplication libContext;

    public BaseApplication() {
        libContext = this;
    }

    /**
     * Get the sdk version number of current device.
     *
     * @return Android build version as integer.
     */
    public static int getAndroidVersionAsInt() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Get Application context singleton.
     *
     * @return application context.
     */
    public static Context getContext() {
        return mContext;
    }

    public static BaseApplication getLibContext() {
        return libContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = getApplicationContext();
        this.libContext = this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setupDatabaseRepository();
        applicationCreated();
        BaseViewClassHolder.getInstance();
    }

    public abstract void applicationCreated();

    public abstract Object getConfiguration();

    public abstract ServicesMapping getServicesMapping();

    public abstract void setupDatabaseRepository();

    public abstract String AppID();
}
