package plnr.custom.framework.logger;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import plnr.custom.framework.Config;
import plnr.custom.framework.core.base.enumulation.UpdatePolicy;
import plnr.custom.framework.core.base.model.BaseArrayList;
import plnr.custom.framework.core.base.model.BaseDateTime;
import plnr.custom.framework.logger.enumulation.LogSearchBy;
import plnr.custom.framework.logger.enumulation.LogType;
import plnr.custom.framework.logger.intf.iLoggerNotification;
import plnr.custom.framework.logger.model.LogDebugModel;
import plnr.custom.framework.logger.model.LogErrorModel;
import plnr.custom.framework.logger.model.LogFilter;
import plnr.custom.framework.logger.model.LogInfoModel;
import plnr.custom.framework.logger.model.LogModel;
import plnr.custom.framework.logger.model.LogWarningModel;
import plnr.custom.framework.repository.ObjectFactory;
import plnr.custom.framework.repository.db.Column;
import plnr.custom.framework.repository.db.Table;

/**
 * Created by nattapongr on 10/14/15 AD.
 */
public class LogManager {
    private static LogManager ourInstance;
    private BaseArrayList<String> logListCreateFilter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private BaseArrayList<LogModel> finalLogList;
    private LogFilter distinctFilter;
    private Table[] getFilterFromTable;

    private LogManager() {

    }

    public static LogManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new LogManager();
        }
        return ourInstance;
    }

    public void clearAllLog() {
        ObjectFactory.delete(LogDebugModel.class).deleteTable();
        ObjectFactory.delete(LogInfoModel.class).deleteTable();
        ObjectFactory.delete(LogWarningModel.class).deleteTable();
        ObjectFactory.delete(LogErrorModel.class).deleteTable();
        this.registerLogDBModel();
    }

    public void clearAllLog(LogType type) {

        switch (type) {
            case DEBUG:
                if (ObjectFactory.delete(LogDebugModel.class).deleteTable())
                    ObjectFactory.register(LogDebugModel.class);
                break;
            case INFO:
                if (ObjectFactory.delete(LogInfoModel.class).deleteTable())
                    ObjectFactory.register(LogDebugModel.class);
                break;
            case WARNING:
                if (ObjectFactory.delete(LogWarningModel.class).deleteTable())
                    ObjectFactory.register(LogWarningModel.class);
                break;
            case ERROR:
                if (ObjectFactory.delete(LogErrorModel.class).deleteTable())
                    ObjectFactory.register(LogErrorModel.class);
                break;
        }
    }

    public boolean clearLog(LogModel obj) {
        if (LogDebugModel.TYPE_NAME.equalsIgnoreCase(obj.getType()))
            return ObjectFactory.delete(LogDebugModel.class).deleteObject(obj);
        else if (LogInfoModel.TYPE_NAME.equalsIgnoreCase(obj.getType()))
            return ObjectFactory.delete(LogInfoModel.class).deleteObject(obj);
        else if (LogWarningModel.TYPE_NAME.equalsIgnoreCase(obj.getType()))
            return ObjectFactory.delete(LogWarningModel.class).deleteObject(obj);
        else
            return ObjectFactory.delete(LogErrorModel.class).deleteObject(obj);
    }

    public void debug(String target, String module, String log) {
        if (Config.FOR_TESTING) {
            LogDebugModel debug = new LogDebugModel();
            debug.updateTimeStamp();
            debug.setTarget(target);
            debug.setModule(module);
            debug.setLog(log);

            ObjectFactory.insert(LogDebugModel.class).asNewItem(debug);
            Log.d("Module : " + module + " Target : " + target, log);
        }
    }

    public void info(String target, String module, String log) {
        if (Config.FOR_TESTING) {
            LogInfoModel info = new LogInfoModel();
            info.updateTimeStamp();
            info.setTarget(target);
            info.setModule(module);
            info.setLog(log);

            ObjectFactory.insert(LogInfoModel.class).asNewItem(info);
            Log.d("Module : " + module + " Target : " + target, log);
        }
    }

    public void warning(String target, String module, String log) {
        if (Config.FOR_TESTING) {
            LogWarningModel warning = new LogWarningModel();
            warning.updateTimeStamp();
            warning.setTarget(target);
            warning.setModule(module);
            warning.setLog(log);

            ObjectFactory.insert(LogWarningModel.class).asNewItem(warning);
            Log.d("Module : " + module + " Target : " + target, log);
        }
    }

    public void error(String target, String module, String log) {
        if (Config.FOR_TESTING) {
            LogErrorModel error = new LogErrorModel();
            error.updateTimeStamp();
            error.setTarget(target);
            error.setModule(module);
            error.setLog(log);

            ObjectFactory.insert(LogErrorModel.class).asNewItem(error);
            Log.d("Module : " + module + " Target : " + target, log);
        }
    }

    public void get(iLoggerNotification callBack, LogType level, String target, String module, BaseDateTime dateFrom, BaseDateTime dateTo, final LogSearchBy sortBy, boolean desc) {
        this.finalLogList = BaseArrayList.Builder(LogModel.class);
        BaseArrayList<LogModel> preLogList = BaseArrayList.Builder(LogModel.class);
        BaseArrayList<?> logRemoveFromList = BaseArrayList.Builder(LogModel.class);

        switch (level) {
            case DEBUG:
                preLogList.addAll(ObjectFactory.select(LogDebugModel.class).getAll());
                break;
            case INFO:
                preLogList.addAll(ObjectFactory.select(LogInfoModel.class).getAll());
                break;
            case WARNING:
                preLogList.addAll(ObjectFactory.select(LogWarningModel.class).getAll());
                break;
            case ERROR:
                preLogList.addAll(ObjectFactory.select(LogErrorModel.class).getAll());
                break;
            default:
                preLogList.addAll(ObjectFactory.select(LogDebugModel.class).getAll());
                preLogList.addAll(ObjectFactory.select(LogInfoModel.class).getAll());
                preLogList.addAll(ObjectFactory.select(LogWarningModel.class).getAll());
                preLogList.addAll(ObjectFactory.select(LogErrorModel.class).getAll());
                break;
        }

        if (preLogList != null && preLogList.size() > 0)
            this.finalLogList.updateFromJson("{\"entries\":" + preLogList.toJSONLocalString() + "}", UpdatePolicy.ForceUpdate);

        for (int i = 0; i < this.finalLogList.size(); i++) {
            boolean isRemove = false;
            LogModel logItem = (LogModel) this.finalLogList.get(i);

            if (!target.equalsIgnoreCase(LogTarget.ALL) && module.equalsIgnoreCase(LogModule.ALL)) {
                if (!logItem.getTarget().equalsIgnoreCase(target)) isRemove = true;
            } else if (target.equalsIgnoreCase(LogTarget.ALL) && !module.equalsIgnoreCase(LogModule.ALL)) {
                if (!logItem.getModule().equalsIgnoreCase(module)) isRemove = true;
            } else if (!target.equalsIgnoreCase(LogTarget.ALL) && !module.equalsIgnoreCase(LogModule.ALL)) {
                if (!logItem.getTarget().equalsIgnoreCase(target) || !logItem.getModule().equalsIgnoreCase(module))
                    isRemove = true;
            }

            if (isRemove) {
                logRemoveFromList.add(logItem);
            } else {
                try {
                    Date dateFromConvert = dateFormat.parse(dateFormat.format(dateFrom));
                    Date dateToConvert = dateFormat.parse(dateFormat.format(dateTo));
                    Date objectDateConvert = dateFormat.parse(dateFormat.format(logItem.getUpdateDate()));
                    if (!(objectDateConvert.after(dateFromConvert) || (objectDateConvert).equals(dateFromConvert))
                            || !(objectDateConvert.before(dateToConvert) || objectDateConvert.equals(dateToConvert))) {
                        logRemoveFromList.add(logItem);
                    }
//                    Log.d("TAG" , "Convert date");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        if (logRemoveFromList.size() > 0)
            this.finalLogList.removeAll(logRemoveFromList);

        Collections.sort(this.finalLogList, new LogComparatorObject(sortBy));
        if (desc) Collections.reverse(this.finalLogList);

        callBack.fetchedLogList(this.finalLogList);
    }

    public void get(iLoggerNotification callBack, LogType level, String target, String module, BaseDateTime startDate, BaseDateTime endDate) {
        get(callBack, level, target, module, startDate, endDate, LogSearchBy.SEARCH_BY_DATE, false);
    }

    public void get(iLoggerNotification callBack, LogType level, String target, String module) {
        get(callBack, level, target, module, new BaseDateTime(0), new BaseDateTime(), LogSearchBy.SEARCH_BY_DATE, false);
    }

    public void get(iLoggerNotification callBack, BaseDateTime startDate, BaseDateTime endDate) {
        get(callBack, LogType.DEFAULT, LogTarget.ALL, LogModule.ALL, startDate, endDate, LogSearchBy.SEARCH_BY_DATE, false);
    }

    public void get(iLoggerNotification callBack, LogType level, String module) {
        get(callBack, level, LogModule.ALL, module, new BaseDateTime(0), new BaseDateTime(), LogSearchBy.SEARCH_BY_DATE, false);
    }

    public void get(iLoggerNotification callBack, LogType level) {
        get(callBack, level, LogTarget.ALL, LogModule.ALL, new BaseDateTime(0), new BaseDateTime(), LogSearchBy.SEARCH_BY_DATE, false);
    }

    public void get(iLoggerNotification callBack, String module) {
        get(callBack, LogType.DEFAULT, LogTarget.ALL, module, new BaseDateTime(0), new BaseDateTime(), LogSearchBy.SEARCH_BY_DATE, false);
    }

    public void getAll(iLoggerNotification callBack) {
        get(callBack, LogType.DEFAULT, LogTarget.ALL, LogModule.ALL, new BaseDateTime(0), new BaseDateTime(), LogSearchBy.SEARCH_BY_DATE, false);
    }

    public BaseArrayList reverseList(LogSearchBy sortBy, boolean reverse) {

        Collections.sort(this.finalLogList, new LogComparatorObject(sortBy));
        if (reverse) Collections.reverse(this.finalLogList);

        return this.finalLogList;
    }

    public void registerLogDBModel() {
        ObjectFactory.register(LogDebugModel.class);
        ObjectFactory.register(LogInfoModel.class);
        ObjectFactory.register(LogWarningModel.class);
        ObjectFactory.register(LogErrorModel.class);
    }

    public BaseArrayList getAllTargetName() {
        if (this.logListCreateFilter == null)
            this.getLogToCreateFilter();

        BaseArrayList<String> targetList = BaseArrayList.Builder(String.class);
        for (int i = 0; i < this.logListCreateFilter.size(); i++) {
            if (!targetList.contains(((LogModel) this.logListCreateFilter.get(i)).getTarget()))
                targetList.add(((LogModel) this.logListCreateFilter.get(i)).getTarget());
        }

        Collections.sort(targetList, String.CASE_INSENSITIVE_ORDER);

        return targetList;
    }

    public BaseArrayList getAllModuleName() {
        if (this.logListCreateFilter == null)
            this.getLogToCreateFilter();

        BaseArrayList<String> moduleList = BaseArrayList.Builder(String.class);
        for (int i = 0; i < this.logListCreateFilter.size(); i++) {
            if (!moduleList.contains(((LogModel) this.logListCreateFilter.get(i)).getModule()))
                moduleList.add(((LogModel) this.logListCreateFilter.get(i)).getModule());
        }

        Collections.sort(moduleList, String.CASE_INSENSITIVE_ORDER);

        return moduleList;
    }

    public BaseArrayList getAllLogTypeName() {
        BaseArrayList<String> typeList = BaseArrayList.Builder(String.class);
        for (int i = 0; i < LogType.values().length; i++) {
            String enumName = LogType.values()[i].name();
            if (!enumName.equalsIgnoreCase("default")) {
                typeList.add(enumName);
//                Log.d("TAG", LogType.values().toString());
            }
        }

        Collections.sort(typeList, String.CASE_INSENSITIVE_ORDER);

        return typeList;
    }

    public BaseArrayList getAllLogDate() {
        if (this.logListCreateFilter == null)
            this.getLogToCreateFilter();

        BaseArrayList<String> dateTimeList = BaseArrayList.Builder(String.class);
        for (int i = 0; i < this.logListCreateFilter.size(); i++) {
            String logDate = dateFormat.format(((LogModel) this.logListCreateFilter.get(i)).getUpdateDate());
            if (!dateTimeList.contains(logDate))
                dateTimeList.add(logDate);
        }

        Collections.sort(dateTimeList, String.CASE_INSENSITIVE_ORDER);

        return dateTimeList;
    }

    public void getFilter(Table[] selectTable, String[] distinctColumn) {
        this.getFilterFromTable = selectTable;
        this.distinctFilter = ObjectFactory.select().getDistinct(selectTable, distinctColumn);
    }

    public BaseArrayList canGetFilterFrom(Table[] tableToSelect) {
        BaseArrayList<String> columnCanSelect = BaseArrayList.Builder(String.class);
        for (int fTable = 0; fTable < tableToSelect.length; fTable++) {
            ArrayList<Column> tableColumn = tableToSelect[fTable].columns;
            for (int fColumnName = 0; fColumnName < tableColumn.size(); fColumnName++) {
                String columnName = tableColumn.get(fColumnName).name;
                if (!columnCanSelect.contains(columnName))
                    columnCanSelect.add(columnName);
            }
//            Log.d("TAG" , tableColumn.hashCode() + "");
        }
        return columnCanSelect;
    }

    public void getLogToCreateFilter() {
        this.logListCreateFilter = BaseArrayList.Builder(LogModel.class);
        this.logListCreateFilter.addAll(ObjectFactory.select(LogDebugModel.class).getAll());
        this.logListCreateFilter.addAll(ObjectFactory.select(LogInfoModel.class).getAll());
        this.logListCreateFilter.addAll(ObjectFactory.select(LogWarningModel.class).getAll());
        this.logListCreateFilter.addAll(ObjectFactory.select(LogErrorModel.class).getAll());

        if (logListCreateFilter.size() > 0)
            this.logListCreateFilter.updateFromJson("{\"entries\":" + logListCreateFilter.toJSONLocalString() + "}", UpdatePolicy.ForceUpdate);
    }
}
