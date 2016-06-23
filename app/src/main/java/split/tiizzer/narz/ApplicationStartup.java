package split.tiizzer.narz;

import plnr.custom.framework.ServicesMapping;
import plnr.custom.framework.core.BaseApplication;
import plnr.custom.framework.core.base.ui.listview.BaseViewClassHolder;

/**
 * Created by NARZTIIZZER on 6/23/2016.
 */
public class ApplicationStartup extends BaseApplication {
    @Override
    public void applicationCreated() {
        BaseViewClassHolder.getInstance().addViewClass(CellEditText.class , CellEditTextDatasource.TAG);
    }

    @Override
    public Object getConfiguration() {
        return null;
    }

    @Override
    public ServicesMapping getServicesMapping() {
        return null;
    }

    @Override
    public void setupDatabaseRepository() {

    }

    @Override
    public String AppID() {
        return null;
    }
}
