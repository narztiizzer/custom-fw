package plnr.custom.framework.location.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import plnr.custom.framework.api.WebAPIUpdateObject;
import plnr.custom.framework.api.intf.IWebAPINotification;
import plnr.custom.framework.authentication.CurrentUser;
import plnr.custom.framework.authentication.enumulation.URLService;
import plnr.custom.framework.core.base.model.BaseArrayList;
import plnr.custom.framework.core.base.model.BaseDateTime;
import plnr.custom.framework.location.models.GPSLog;
import plnr.custom.framework.location.models.GPSTrakingModel;
import plnr.custom.framework.logger.LogManager;
import plnr.custom.framework.repository.ObjectFactory;

/**
 * Created by nattapongr on 8/24/15 AD.
 */
public class LocationManager implements LocationListener, IWebAPINotification {
    private static LocationManager shareInstance;
    Location currentLocation;
    private Context mContext;
    private android.location.LocationManager location;

    private LocationManager() {

    }

    public static LocationManager getInstance() {
        if (shareInstance == null) {
            shareInstance = new LocationManager();
        }
        return shareInstance;
    }

    public void registerGPSDatabase() {
        ObjectFactory.register(GPSLog.class);
    }

    public BaseArrayList<GPSLog> getGPSLog(BaseDateTime from, BaseDateTime to) {
        BaseArrayList<GPSLog> aLog = BaseArrayList.Builder(GPSLog.class);
        aLog.addAll(ObjectFactory.select(GPSLog.class).getAll());
        return aLog;
    }

    public GPSLog getCurrentLocation() {
        GPSLog gps = new GPSLog();
        gps.updateTimeStamp();
        if (currentLocation != null) {
            gps.setIsActive(true);
            gps.setLat(currentLocation.getLatitude());
            gps.setLon(currentLocation.getLongitude());
        } else gps.setIsActive(false);
        return gps;
    }

    public double getTotalDistance(BaseArrayList<GPSLog> arrLog) {
        double tDis = 0.0;
        for (int i = 0; i < arrLog.size() - 1; i++) {
            GPSLog first = (GPSLog) arrLog.get(i);
            GPSLog seccound = (GPSLog) arrLog.get(i + 1);
            tDis += calDistance(first.getLat(), first.getLon(), seccound.getLat(), seccound.getLon());
        }
        return tDis;
    }

    public void startService(Context mContext) {
        this.mContext = mContext;
        registerGPSDatabase();
        this.location = (android.location.LocationManager) this.mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.location.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 0, 0, this);
        clearAllLog();
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    if (currentLocation != null) {
                        serviceRunning();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private void serviceRunning() {
        if (currentLocation != null) {
            if (currentLocation.getLatitude() > 0 && currentLocation.getLongitude() > 0) {
                GPSLog gps = new GPSLog();
                gps.setLat(currentLocation.getLatitude());
                gps.setLon(currentLocation.getLongitude());
                gps.setTag(currentLocation.getProvider() == null ? "" : currentLocation.getProvider());
                gps.updatedate = new BaseDateTime();
                ObjectFactory.insert(GPSLog.class).asNewItem(gps);
                LogManager.getInstance().debug(gps.getLat() + " , " + gps.getLon(), this.getClass().getSimpleName(), gps.toJSONString());
            }
        }
    }

    public void startServiceTraking(Context mContext, final String module, final String target) {
        this.mContext = mContext;
        registerGPSDatabase();
        this.location = (android.location.LocationManager) this.mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.location.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 0, 0, this);
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    if (currentLocation != null) {
                        sentLocationTrakingToServer(module, target);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private void sentLocationTrakingToServer(String module, String target) {

        GPSTrakingModel data = new GPSTrakingModel();
        data.setLat(currentLocation.getLatitude() + "");
        data.setLng(currentLocation.getLongitude() + "");

        WebAPIUpdateObject.getInstance().update(
                CurrentUser.getInstance().getCurrentUser().getWepAPIForKey(URLService.URL_UPDATED),
                null,
                data.toJSONString(),
                target,
                module,
                CurrentUser.getInstance().getCurrentUser().getToken(),
                this,
                getClass());
    }

    public void clearAllLog() {
        ObjectFactory.delete(GPSLog.class).deleteTable();
        registerGPSDatabase();
    }

    private double calDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.609344 * 1000; // *1000 = Km.
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void webAPISuccess(String code, String message, String jsonData, Class Class) {
        Log.d("LocationManager", "Class : " + Class.getName() + "Code : " + code + " MSG : " + message + " RES : " + jsonData);
    }

    @Override
    public void webAPIFailed(String code, String message, Class Class) {
        Log.d("LocationManager", "Class : " + Class.getName() + "Code : " + code + " MSG : " + message);
    }
}
