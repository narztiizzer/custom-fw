package plnr.custom.framework.location.models;

import plnr.custom.framework.core.base.annotation.ColumnType;
import plnr.custom.framework.core.base.annotation.IQueryObject;
import plnr.custom.framework.core.base.annotation.JSONVariable;
import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseListAdapter;
import plnr.custom.framework.core.base.ui.listview.BaseScrollListAdapter;

/**
 * Created by nattapongr on 8/24/15 AD.
 */

@IQueryObject(name = "gps_log")
public class GPSLog extends BaseModel {
    @JSONVariable
    private boolean isActive;

    @JSONVariable
    @IQueryObject(
            name = "lat",
            type = ColumnType.TEXT
    )
    private String lat;

    @JSONVariable
    @IQueryObject(
            name = "lon",
            type = ColumnType.TEXT
    )
    private String lon;

    @JSONVariable
    @IQueryObject(
            name = "tag",
            type = ColumnType.TEXT
    )
    private String tag;

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public double getLat() {
        if (lat == null)
            lat = "0";
        return Double.parseDouble(lat);
    }

    public void setLat(double lat) {
        this.lat = lat + "";
    }

    public double getLon() {
        if (lon == null)
            lon = "0";
        return Double.parseDouble(lon);
    }

    public void setLon(double lon) {
        this.lon = lon + "";
    }

    public String getTag() {
        if (tag == null)
            tag = "";
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean saveListHandler(BaseListAdapter list, int row, BaseCellDataSource dataSource) {
        return false;
    }

    @Override
    public boolean saveListHandler(BaseScrollListAdapter list, int row, BaseCellDataSource dataSource) {
        return false;
    }

    @Override
    public boolean shouldSaveListHandler(BaseCellDataSource dataSource) {
        return false;
    }

    @Override
    public String getObjectIdentificationRequest() {
        return null;
    }

    @Override
    public String getKeyForObjectIdentificationRequest() {
        return null;
    }
}
