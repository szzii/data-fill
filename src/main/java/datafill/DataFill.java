package datafill;


import java.lang.annotation.*;

/**
 * @author szz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface DataFill {

    String value() default "";

}
