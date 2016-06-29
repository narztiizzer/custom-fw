package plnr.custom.framework.core.base.ui.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import plnr.custom.framework.core.component.cell.cell.CellSectionBlank;
import plnr.custom.framework.core.component.cell.cell.CellSectionBreak;
import plnr.custom.framework.core.component.cell.cell.CellSectionBreakWithTitle;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBlankDataSource;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBreakDataSource;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBreakWithTitleDataSource;

/**
 * Created by NARZTIIZZER on 6/18/2016.
 */
public class BaseViewClassHolder {

    protected static BaseViewClassHolder ourInstance;

    private Map<String , Class> listCellViewHolder = new HashMap<>();

    public BaseViewClassHolder() {
        this.addViewClass(CellSectionBlank.class , CellSectionBlankDataSource.TAG);
        this.addViewClass(CellSectionBreak.class , CellSectionBreakDataSource.TAG);
        this.addViewClass(CellSectionBreakWithTitle.class , CellSectionBreakWithTitleDataSource.TAG);
    }

    public static BaseViewClassHolder getInstance() {
        if(ourInstance == null)
            ourInstance = new BaseViewClassHolder();
        return ourInstance;
    }

    public void addViewClass(Class viewClass , String tag) {
        this.listCellViewHolder.put(tag , viewClass);
    }

    public Class findViewClassByTag(String tag) {
        return this.listCellViewHolder.get(tag);
    }

    public ArrayList getAllViewClass() {
        ArrayList<Class> viewClassList = new ArrayList<>();

        for(Class c : listCellViewHolder.values()){
            viewClassList.add(c);
        }

        return viewClassList;
    }

    public int getCountHoldViewClass() { return this.listCellViewHolder.size(); }
}
