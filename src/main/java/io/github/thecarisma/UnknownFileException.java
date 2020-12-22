package io.github.thecarisma;

import java.io.IOException;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 * @date 08-Aug-20 01:47 PM
 */
public class UnknownFileException extends RuntimeException {
    public UnknownFileException(String message) {
        super(message);
    }
}
