package plnr.custom.framework.logger.model;

import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseListAdapter;
import plnr.custom.framework.core.base.ui.listview.BaseScrollListAdapter;

/**
 * Created by narztiizzer on 14/10/2558.
 */

public class LogModel extends BaseModel {

    private String type;
    private String target;
    private String module;
    private String log;
    private String TYPE_NAME;

    public LogModel() {
    }

    public String getType() {
        return type;
    }

    public String getTarget() {
        return target;
    }

    public String getModule() {
        return module;
    }

    public String getLog() {
        return log;
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
