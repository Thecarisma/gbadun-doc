package io.github.thecarisma;

import java.util.Date;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 * @date 22-Dec-20 05:58 PM
 */
public class Sheet {
    @ExcelColumn(columnNames = {"Firstname", "Middlename", "Lastname"}, valueSeparator = '-')
    String fullName;

    @ExcelColumn(columnName = "Country")
    String country;

    @ExcelColumn(columnNumber = 4)
    String profession;

    @ExcelColumn(columnNumber = 5)
    int age;

    @ExcelColumn(columnName = "Date Added", dateTimeFormat = "dd-MM-yyyy hh:mm:ss")
    Date dateAdded;

    long barrenField;

}
