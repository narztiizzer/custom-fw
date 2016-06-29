package plnr.custom.framework.api.models;


import plnr.custom.framework.core.base.annotation.JSONVariable;
import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseListAdapter;
import plnr.custom.framework.core.base.ui.listview.BaseScrollListAdapter;

/**
 * Created by nattapongr on 5/13/15.
 */
public class Update extends BaseModel {
    @JSONVariable
    private String objectId;
    @JSONVariable
    private String data;
    @JSONVariable
    private String target;
    @JSONVariable
    private String module;
    @JSONVariable
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getObjectStringId() {
        return objectId;
    }

    public void setObjectStringId(String objectId) {
        this.objectId = objectId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
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
