package io.github.thecarisma;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 * @date 22-Dec-20 05:44 PM
 *
 * Order Of Prevalence
 * - ignore
 * - columnNumber
 * - columnName
 * - columnNames
 * - converter
 * - Date Time Format
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
    boolean ignore() default false;

    int columnNumber() default -1;

    String columnName() default "";

    String[] columnNames() default {};

    char valueSeparator() default ' ';

    String dateTimeFormat() default "dd-MM-yyyy hh:mm:ss";

    Class<?> converter() default void.class;

    boolean failIfAbsent() default true;
}
