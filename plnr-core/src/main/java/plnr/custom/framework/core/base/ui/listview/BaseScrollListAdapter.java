package plnr.custom.framework.core.base.ui.listview;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBlankDataSource;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBreakDataSource;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBreakWithTitleDataSource;

/**
 * Created by NARZTIIZZER on 6/17/2016.
 */
public class BaseScrollListAdapter {

    /**
     * This contains data that will render to each row in the list
     */
    protected ArrayList<BaseCellDataSource> dataSource;
    private String tag;
    private Context mContext;

    public BaseScrollListAdapter(Context cnt , String tag) {
        this.mContext = cnt;
        this.tag = tag;
    }

    /**
     * Add the section break for table list view to the last of the list
     */
    public void addSectionBreak() {
        this.getDataSource().add(new CellSectionBreakDataSource(null, CellSectionBreakDataSource.TAG));
    }

    /**
     * Add the section blank for table list view to the last of the list
     */
    public void addSectionBlank() {
        this.getDataSource().add(new CellSectionBlankDataSource(null, CellSectionBlankDataSource.TAG));
    }

    /**
     * Add the section break for table list view to the last of the list
     *
     * @param title title that will show on top of the section break
     */
    public void addSectionBreakWithTitle(String title, int titleTextColor) {
        this.getDataSource().add(new CellSectionBreakWithTitleDataSource(null, CellSectionBreakWithTitleDataSource.TAG , title, titleTextColor));
    }

    public void saveDataForAllRow() {
        for (int i = 0; i < getDataSource().size(); i++) {
            saveDataForRowAtIndex(i);
        }
    }

    /**
     * Save a specific row data from RowDataSource to Model.
     *
     * @param row row number
     */
    public void saveDataForRowAtIndex(int row) {
        if (getDataSource().get(row).shouldSaveRow(mContext) &&
                getDataSource().get(row).getDataObject()
                        .shouldSaveListHandler(getDataSource().get(row))) {
            boolean isSuccess = getDataSource().get(row).getDataObject()
                    .saveListHandler(this, row, getDataSource().get(row));
            getDataSource().get(row).didSaveRow(isSuccess, mContext);
        }
    }

    public ArrayList<BaseCellDataSource> getDataSource() {
        if (this.dataSource == null) this.dataSource = new ArrayList<>();
        return this.dataSource;
    }

    public String getAdapterTag() { return tag; }

    public int getCount() { return dataSource.size(); }

    public View getChildViewAtIndex(int index) {

        BaseCellDataSource current = dataSource.get(index);
        try{
            Class viewClass = BaseViewClassHolder.getInstance().findViewClassByTag(current.getTag());
            return BaseCellView.Builder(viewClass , current , mContext);
        } catch (Exception e) {
            return null;
        }
    }
}
