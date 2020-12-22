package io.github.thecarisma;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 * @date 22-Dec-20 11:23 PM
 */
public interface ExcelColumnConverter<T> {
    String convertToCellValue(T meta);
    T convertToFieldValue(String cellValue);
}
