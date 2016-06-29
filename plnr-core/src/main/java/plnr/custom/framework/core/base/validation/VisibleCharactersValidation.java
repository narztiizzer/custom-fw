package plnr.custom.framework.core.base.validation;

/**
 * Created by nattapongr on 2/27/15.
 */
public class VisibleCharactersValidation extends Validation {
    @Override
    public String getErrorDescription() {
        return "VisibleCharacters Validation";
    }

    @Override
    public String getRegExp() {
        return "[\\x21-\\x7E]+";
    }
}
