package io.p1jmonitor.telegram;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ObisReference {
    String value();
    int minimumDecimals() default 0;
    int maximumDecimals() default 0;
    int valueIndex() default 0;
}
