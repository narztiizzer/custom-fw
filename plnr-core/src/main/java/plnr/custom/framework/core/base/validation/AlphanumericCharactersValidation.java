package plnr.custom.framework.core.base.validation;

/**
 * Created by administartor on 2/27/15.
 */
public class AlphanumericCharactersValidation extends Validation {
    @Override
    public String getErrorDescription() {
        return "AlphanumericCharacters Validation";
    }

    @Override
    public String getRegExp() {
        return "[A-Za-z0-9]+";
    }
}
