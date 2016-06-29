package plnr.custom.framework.core.base.validation;

/**
 * Created by nattapongr on 2/27/15.
 */
public class VisibleCharactersAndTheSpaceCharacterValidation extends Validation {
    @Override
    public String getErrorDescription() {
        return "VisibleCharactersAndTheSpaceCharacter Validation";
    }

    @Override
    public String getRegExp() {
        return "[\\x20-\\x7E]+";
    }
}
