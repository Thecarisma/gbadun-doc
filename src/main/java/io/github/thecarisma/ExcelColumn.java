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
 * - columnNumber
 * - columnName
 * - columnNames
 * - Date Time Format
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
    int columnNumber() default -1;

    String columnName() default "";

    String[] columnNames() default {};

    char valueSeparator() default ' ';

    String dateTimeFormat() default "";
}
