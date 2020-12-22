package io.github.thecarisma;

import java.util.Date;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 * @date 22-Dec-20 05:58 PM
 */
public class UserInfo {

    public static class Location {
        public String planet;
        public String longitude;
        public String latitude;

        @Override
        public String toString() {
            return String.format("[%s, %s, %s]", planet, longitude, latitude);
        }
    }

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

    @ExcelColumn(columnNumber = 7, dateTimeFormat = "dd/MM/yyyy")
    Date dateOfBirth;

    @ExcelColumn(columnNumber = 8, columnName = "Amount")
    double amount;

    @ExcelColumn(columnName = "Is Human")
    boolean isHuman = true;

    @ExcelColumn(ignore = true)
    long barrenField;

    @ExcelColumn(columnName = "Location", converter = LocationConverter.class)
    Location location;

    @Override
    public String toString() {
        return String.format("[%s, %s, %s, %d, %s, %f, %s, %s]",
                fullName, country, profession, age, dateAdded, amount, isHuman, location);
    }

    static class LocationConverter implements ExcelColumnConverter<Location> {

        @Override
        public String convertToCellValue(Location meta) {
            return null;
        }

        @Override
        public Location convertToFieldValue(String cellValue) {
            Location location = new Location();
            String[] parts = cellValue.split(",");
            if (parts.length > 0) {
                location.planet = parts[0].trim();
            }
            if (parts.length > 1) {
                location.longitude = parts[1].trim();
            }
            if (parts.length > 2) {
                location.latitude = parts[2].trim();
            }
            return location;
        }
    }
}
