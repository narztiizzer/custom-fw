package plnr.custom.framework.logger.model;

import plnr.custom.framework.core.base.annotation.ColumnType;
import plnr.custom.framework.core.base.annotation.IQueryObject;
import plnr.custom.framework.core.base.annotation.JSONVariable;
import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseListAdapter;
import plnr.custom.framework.core.base.ui.listview.BaseScrollListAdapter;

/**
 * Created by narztiizzer on 14/10/2558.
 */

@IQueryObject(name = "log_debug")
public class LogDebugModel extends BaseModel {

    public static final String TYPE_NAME = "debug";
    @JSONVariable
    @IQueryObject(name = "type",
            type = ColumnType.TEXT)
    public String type;
    @JSONVariable
    @IQueryObject(name = "target",
            type = ColumnType.TEXT)
    public String target;
    @JSONVariable
    @IQueryObject(name = "module",
            type = ColumnType.TEXT)
    public String module;
    @JSONVariable
    @IQueryObject(name = "log",
            type = ColumnType.TEXT)
    public String log;

    public LogDebugModel() {
        type = TYPE_NAME;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setLog(String log) {
        this.log = log;
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
