package plnr.custom.framework.core.base.validation;

/**
 * Created by nattapongr on 2/27/15.
 */
public class NonWhitespaceCharactersValidation extends Validation {
    @Override
    public String getErrorDescription() {
        return "NonWhitespaceCharacters Validation";
    }

    @Override
    public String getRegExp() {
        return "[^ \\t\\r\\n\\\\v\\f]+";
    }
}
