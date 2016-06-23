package split.tiizzer.narz;

import android.content.Context;

import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;

/**
 * Created by NARZTIIZZER on 6/23/2016.
 */
public class CellEditTextDatasource extends BaseCellDataSource {
    public static String TAG = "app.CellEdittext";
    /**
     * This is constructor class for BaseCellDataSource Object.
     *
     * @param dataObject model that provided data or will recieve data from cell
     * @param tag        string that use for differentiate each cell and object it serve
     */
    public CellEditTextDatasource(BaseModel dataObject, String tag) {
        super(dataObject, tag);
    }

    @Override
    public void didSaveRow(boolean isSuccess, Context context) {

    }

    @Override
    public boolean shouldSaveRow(Context context) {
        return false;
    }
}
