package plnr.custom.framework.core.component.cell.datasource;

import android.content.Context;

import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;


/**
 * Created by ponlavitlarpeampaisarl on 2/6/15 AD.
 */
public class CellSectionBreakWithTitleDataSource extends BaseCellDataSource {

    public static String TAG = "core.framework.CellSectionBreakWithTitle";

    public String title;
    public int titleTextColor;

    /**
     * Get value from listView
     *
     * @param dataObject     Object
     * @param tag            Tag
     * @param title          Text title
     * @param titleTextColor set title text color
     */
    public CellSectionBreakWithTitleDataSource(BaseModel dataObject, String tag, String title, int titleTextColor) {
        super(dataObject , tag);
        this.title = title;
        this.titleTextColor = titleTextColor;
    }

    /**
     * This function run after row save done. "see BaseModel.java saveListHandler()"
     *
     * @param isSuccess true if the row saving is success, otherwise data is not save.
     * @param context   view context use for populate alert or dialog.
     */
    @Override
    public void didSaveRow(boolean isSuccess, Context context) {

    }

    /**
     * Use this function for data validation, preparation
     *
     * @param context view context use for populate alert or dialog.
     * @return true if all success and will process to next step of saving row, false if not.
     */
    @Override
    public boolean shouldSaveRow(Context context) {
        return false;
    }
}
