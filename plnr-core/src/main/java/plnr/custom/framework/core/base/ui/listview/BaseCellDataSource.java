package plnr.custom.framework.core.base.ui.listview;

import android.content.Context;

import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBlankDataSource;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBreakDataSource;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBreakWithTitleDataSource;


/**
 * Created by ponlavitlarpeampaisarl on 2/3/15 AD.
 */
public abstract class BaseCellDataSource {

    /**
     *
     */
    private BaseModel dataObject;

    /**
     *
     */
    private String tag;

    /**
     * This is constructor class for BaseCellDataSource Object.
     *
     * @param dataObject     model that provided data or will recieve data from cell
     * @param tag            string that use for differentiate each cell and object it serve
     */
    public BaseCellDataSource(BaseModel dataObject, String tag) {
        this.dataObject = dataObject;
        this.tag = tag;
        if (!getClass().equals(CellSectionBreakDataSource.class) &&
                !getClass().equals(CellSectionBlankDataSource.class) &&
                !getClass().equals(CellSectionBreakWithTitleDataSource.class) &&
                this.dataObject == null) throw new NullPointerException();
    }

    /**
     * This is the source data object that use for populate data for row
     *
     * @return source data object
     */
    public BaseModel getDataObject() {
        return this.dataObject;
    }

    /**
     * This function run after row save done. "see BaseModel.java saveListHandler()"
     *
     * @param isSuccess true if the row saving is success, otherwise data is not save.
     * @param context   view context use for populate alert or dialog.
     */
    public abstract void didSaveRow(boolean isSuccess, Context context);

    /**
     * Use this function for data validation, preparation
     *
     * @param context view context use for populate alert or dialog.
     * @return true if all success and will process to next step of saving row, false if not.
     */
    public abstract boolean shouldSaveRow(Context context);

    /**
     * Get tag for this dataSource.
     *
     * @return tag that will differentiate for each dataSource
     */
    public String getTag() {
        return tag;
    }
}



