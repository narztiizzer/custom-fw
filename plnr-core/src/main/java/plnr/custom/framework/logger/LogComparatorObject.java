package plnr.custom.framework.logger;

import java.util.Comparator;

import plnr.custom.framework.logger.enumulation.LogSearchBy;
import plnr.custom.framework.logger.model.LogModel;

/**
 * Created by narztiizzer on 16/10/2558.
 */
public class LogComparatorObject implements Comparator<LogModel> {

    private int result = 0;
    private LogSearchBy searchBy = LogSearchBy.SEARCH_BY_TYPE;

    public LogComparatorObject(LogSearchBy type) {
        this.searchBy = type;
    }

    @Override
    public int compare(LogModel modelFirst, LogModel modelSecond) {

        switch (searchBy) {
            case SEARCH_BY_DATE:
                if (modelFirst.getUpdateDate().after(modelSecond.getUpdateDate())) this.result = -1;
                else if (modelFirst.getUpdateDate().before(modelSecond.getUpdateDate()))
                    this.result = 1;
                else this.result = 0;
                break;
            case SEARCH_BY_TYPE:
                this.result = modelFirst.getType().compareTo(modelSecond.getType());
                break;
            case SEARCH_BY_MODULE:
                this.result = modelFirst.getModule().compareTo(modelSecond.getModule());
                break;
            default:
                this.result = 0;
        }
        return this.result;
    }

}
