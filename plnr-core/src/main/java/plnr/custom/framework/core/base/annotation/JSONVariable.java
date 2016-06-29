package plnr.custom.framework.core.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ponlavitlarpeampaisarl on 2/3/15 AD.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONVariable {
    public boolean localOnly() default false;
}
