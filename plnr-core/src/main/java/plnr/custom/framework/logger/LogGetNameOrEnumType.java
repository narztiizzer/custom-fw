package plnr.custom.framework.logger;

import plnr.custom.framework.logger.enumulation.LogType;
import plnr.custom.framework.logger.model.LogDebugModel;
import plnr.custom.framework.logger.model.LogErrorModel;
import plnr.custom.framework.logger.model.LogInfoModel;
import plnr.custom.framework.logger.model.LogWarningModel;

/**
 * Created by nattapongr on 10/16/15 AD.
 */
public class LogGetNameOrEnumType {
    public static String getNameOfLogType(LogType type) {
        switch (type) {
            case DEBUG:
                return LogDebugModel.TYPE_NAME;
            case WARNING:
                return LogWarningModel.TYPE_NAME;
            case INFO:
                return LogInfoModel.TYPE_NAME;
            case ERROR:
                return LogErrorModel.TYPE_NAME;
            default:
                return "all";
        }
    }

    public static LogType getLogTypeFromLogTypeName(String name) {
        switch (name.toLowerCase()) {
            case LogDebugModel.TYPE_NAME:
                return LogType.DEBUG;
            case LogWarningModel.TYPE_NAME:
                return LogType.WARNING;
            case LogInfoModel.TYPE_NAME:
                return LogType.INFO;
            case LogErrorModel.TYPE_NAME:
                return LogType.ERROR;
            default:
                return LogType.DEFAULT;
        }
    }
}
