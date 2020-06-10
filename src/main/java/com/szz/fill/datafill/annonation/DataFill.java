package com.szz.fill.datafill.annonation;


import com.szz.fill.datafill.handler.DataFillHandler;

import java.lang.annotation.*;

/**
 * @author szz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface DataFill {

    String value() default "";

    Class<? extends DataFillHandler> handler();
}
