package plnr.custom.framework.core.base.validation;

/**
 * Created by nattapongr on 2/27/15.
 */
public class WordBoundariesValidation extends Validation {
    @Override
    public String getErrorDescription() {
        return "WordBoundaries Validation";
    }

    @Override
    public String getRegExp() {
        return "[(?<=\\W)(?=\\w)|(?<=\\w)(?=\\W)]+";
    }
}
