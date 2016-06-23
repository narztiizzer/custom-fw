package plnr.custom.framework.core.base.validation;

/**
 * Created by administartor on 2/27/15.
 */
public class LowercaseLettersValidation extends Validation {
    @Override
    public String getErrorDescription() {
        return "LowercaseLetters Validation";
    }

    @Override
    public String getRegExp() {
        return "[a-z]+";
    }
}
