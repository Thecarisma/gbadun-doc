package io.github.thecarisma;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 * @date 08-Aug-20 01:47 PM
 */
class ExcelColumnNotFoundException extends RuntimeException {
    public ExcelColumnNotFoundException(String message) {
        super(message);
    }
}
