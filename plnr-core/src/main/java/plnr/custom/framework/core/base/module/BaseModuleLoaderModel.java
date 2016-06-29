package plnr.custom.framework.core.base.module;

import plnr.custom.framework.core.base.model.BaseArrayList;
import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseListAdapter;
import plnr.custom.framework.core.base.ui.listview.BaseScrollListAdapter;

/**
 * Created by administartor on 5/27/15.
 */
public class BaseModuleLoaderModel extends BaseModel {

    private static BaseModuleLoaderModel instance;
    BaseArrayList<BaseModule> moduleList;
    BaseModuleLoaderModelDelegate delegate;

    public static BaseModuleLoaderModel getInstance() {
        if (instance == null) {
            instance = new BaseModuleLoaderModel();
        }
        return instance;
    }

    public void setDelegate(BaseModuleLoaderModelDelegate delegate) {
        this.delegate = delegate;
        if (moduleList == null)
            moduleList = BaseArrayList.Builder(BaseModule.class);
        else
            moduleList.clear();

        delegate.initialModuleList(moduleList);
    }

    public BaseArrayList<BaseModule> getModuleList() {
        return moduleList;
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
