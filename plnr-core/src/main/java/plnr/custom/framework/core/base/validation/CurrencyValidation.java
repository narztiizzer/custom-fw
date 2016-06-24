package plnr.custom.framework.core.base.validation;

/**
 * Created by nattapongr on 4/7/15.
 */
public class CurrencyValidation extends Validation {
    @Override
    public String getErrorDescription() {
        return "Currency Validation";
    }

    @Override
    public String getRegExp() {
        return "[0-9,.]+";
    }
}
