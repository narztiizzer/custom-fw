package split.tiizzer.narz;

import android.util.Log;

import java.text.DateFormatSymbols;

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
        Log.d("TAG" , "Test");
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


        int day = 7;
        int month = 2;
        int year = 2016;

        String selectDate = day + DateFormatSymbols.getInstance().getMonths()[month] + year;

        // 7March2016
    }

    @Override
    public String AppID() {
        return null;
    }
}
