package plnr.custom.framework.core.base.ui.listview;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;

import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBlankDataSource;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBreakDataSource;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBreakWithTitleDataSource;

/**
 * Created by ponlavitlarpeampaisarl on 2/3/15 AD.
 */
public class BaseListAdapter extends BaseAdapter {

    public String tag;
    /**
     * This contains data that will render to each row in the list
     */
    protected ArrayList<BaseCellDataSource> dataSource;
    Context mContext;

    private BaseListAdapter() {

    }

    public BaseListAdapter(Context context, String tag) {
        this();
        this.mContext = context;
    }


    /**
     * BaseCellDataSource list that represent how the list will render.
     *
     * @return the list of BaseCellDataSource.
     */
    public ArrayList<BaseCellDataSource> getDataSource() {
        if (this.dataSource == null) this.dataSource = new ArrayList<BaseCellDataSource>();
        return this.dataSource;
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


    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return this.getDataSource().size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public BaseModel getItem(int position) {
        return this.getDataSource().get(position).getDataObject();
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return this.getDataSource().get(position).hashCode();
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseCellDataSource rowData = getDataSource().get(position);

        try{
            Class viewClass = BaseViewClassHolder.getInstance().findViewClassByTag(rowData.getTag());
            View generateView = BaseCellView.Builder(viewClass , rowData , mContext);
            return generateView;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Save all data from RowDataSource to Model.
     */
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
}
