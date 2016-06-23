package plnr.custom.framework.core.base.validation;

/**
 * Created by nattapongr on 2/27/15.
 */
public class PhoneValidation extends Validation {
    @Override
    public String getErrorDescription() {
        return "Phone Validation";
    }

    @Override
    public String getRegExp() {
        return "[0-9-() ]+";
    }
}
