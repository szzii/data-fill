package datafill.annonation;


import datafill.handler.DataFillHandler;

import java.lang.annotation.*;

/**
 * @author szz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
@Documented
public @interface DataFill {

    String value() default "";

    Class<? extends DataFillHandler> handler();
}
