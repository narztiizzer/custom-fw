package plnr.custom.framework.logger.model;


import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import plnr.custom.framework.core.base.model.BaseArrayList;
import plnr.custom.framework.core.base.model.BaseDateTime;

/**
 * Created by NARZTIIZZER on 10/27/2015.
 */
public class LogFilter {
    private static LogFilter ourInatance = null;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Map<String, Map> objectFilter;

    public static LogFilter getInstance() {
        if (ourInatance == null)
            ourInatance = new LogFilter();
        return ourInatance;
    }

    public void set(Map<String, Map> objectFilter) {
        this.objectFilter = new TreeMap<String, Map>(String.CASE_INSENSITIVE_ORDER);
        this.objectFilter = objectFilter;
    }

    public BaseArrayList getFilterFrom(String column) {
        BaseArrayList<String> returnList = BaseArrayList.Builder(String.class);
        Map<String, Integer> mapResult = objectFilter.get(column);
        if (mapResult == null)
            return returnList;

        if (column.equalsIgnoreCase("updatedate")) {
            for (String key : mapResult.keySet()) {
                String dateParse = dateFormat.format(new BaseDateTime(key));

                if (!returnList.contains(dateParse))
                    returnList.add(dateParse);
            }
        } else {
            for (String key : mapResult.keySet()) {
                if (!returnList.contains(key))
                    returnList.add(key);
            }


        }
        Collections.sort(returnList, String.CASE_INSENSITIVE_ORDER);
        return returnList;
    }

    public int getFilterChildCount(String column, String child) {
        Map<String, Integer> filterObject = this.objectFilter.get(column);
        return filterObject.get(child);
    }

    public void setFilterChildCount(String column, String child, int value) {
        this.objectFilter.get(column).put(child, value);
    }

    public void increaseFilterChildCount(String column, String child) {
        int childCount = getFilterChildCount(column, child);
        setFilterChildCount(column, child, (childCount + 1));
    }

    public boolean decreaseFilterChildCount(String column, String child) {
        int childCountBefore = getFilterChildCount(column, child);
        this.setFilterChildCount(column, child, (childCountBefore - 1));
        int childCountAfter = getFilterChildCount(column, child);

        if (childCountAfter <= 0) return false;
        else return true;
    }
}
