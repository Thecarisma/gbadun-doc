package io.github.thecarisma;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 * @date 22-Dec-20 06:34 PM
 */
public class DocumentPojo {
    
    // Excel 
    public static <T> List<T> fromExcel(File excelFile, Class<T> type) throws IOException {
        return fromExcel(excelFile, type, false);
    }

    public static <T> List<T> fromExcel(File excelFile, Class<T> type, boolean noDuplicate) throws IOException {
        if (excelFile.getName().endsWith(".xls") || excelFile.getName().endsWith(".xlsx")) {
            return readXSSFData(new FileInputStream(excelFile), type, noDuplicate);
        } else {
            throw new UnknownFileException("Invalid file extension (" + excelFile.getName() + "), expected .xls or .xlsx");
        }
    }

    public static <T> List<T> fromExcel(InputStream excelInputStream,
                                        Class<T> type, boolean noDuplicate) throws IOException {

        return readXSSFData(excelInputStream, type, noDuplicate);
    }

    public static <T> List<T> readXSSFData(InputStream excelInputStream,
                                           Class<T> type, boolean noDuplicate) throws IOException {

        Map<String, Integer> cellIndexes = new HashMap<>();
        List<T> rowsEntries = new ArrayList<>();
        XSSFWorkbook wb = new XSSFWorkbook(excelInputStream);
        for (int i = 0; i < wb.getNumberOfSheets(); ++i) {
            XSSFSheet sheet = wb.getSheetAt(i);
            XSSFRow row;
            int rowCount, r;

            for (r = 0; r < 1; r++) {
                row = sheet.getRow(r);
                for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
                    XSSFCell cell = row.getCell((short) c);
                    if (cell != null) {
                        cellIndexes.put(cell.getRichStringCellValue().toString().trim(), c);
                    }
                }
            }
            rowCount = sheet.getPhysicalNumberOfRows();
            for (; r < rowCount; r++) {
                row = sheet.getRow(r);
                if (row != null) {
                    try {
                        final T t = type.newInstance();
                        Map<Integer, String> valueMap = new HashMap<>();
                        for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
                            XSSFCell cell = row.getCell(c);
                            valueMap.put(c, new DataFormatter().formatCellValue(row.getCell(c)));
                        }
                        Arrays.stream(t.getClass().getDeclaredFields())
                        .forEach(field -> {
                            Arrays.stream(field.getAnnotationsByType(ExcelColumn.class))
                                .forEach(column -> {
                                    if (column.ignore()) {
                                        return;
                                    }
                                    StringBuilder value = null;
                                    if (column.columnNumber() != -1) {
                                        if (valueMap.get(column.columnNumber()) == null) {
                                            throw new ExcelColumnNotFoundException("The column index '" +
                                                    column.columnName() + "' is out of bound");
                                        }
                                        value = new StringBuilder(valueMap.get(column.columnNumber()));

                                    } else if (!column.columnName().isEmpty()) {
                                        if (cellIndexes.get(column.columnName()) == null) {
                                            throw new ExcelColumnNotFoundException("The column '" +
                                                    column.columnName() + "' not found in the sheet");
                                        }
                                        value = new StringBuilder(valueMap.get(cellIndexes.get(column.columnName())));

                                    } else if (column.columnNames().length > 0) {
                                        value = new StringBuilder();
                                        for (int index = 0; index < column.columnNames().length; ++index) {
                                            String columnName = column.columnNames()[index];
                                            if (cellIndexes.get(columnName) == null) {
                                                throw new ExcelColumnNotFoundException("The column '" +
                                                        column.columnName() + "' not found in the sheet");
                                            }
                                            String columnValue = valueMap.get(cellIndexes.get(columnName));
                                            if (columnValue != null) {
                                                value.append(columnValue);
                                                if (index < column.columnNames().length-1) {
                                                    value.append(column.valueSeparator());
                                                }
                                            }
                                        }

                                    }
                                    if (value == null) {
                                        return;
                                    }
                                    try {
                                        if (objectsAreSameType(Integer.class, field.getType()) ||
                                                objectsAreSameType(int.class, field.getType())) {

                                            if (value.length() == 0) {
                                                value.append("0");
                                            }
                                            field.setAccessible(true);
                                            field.set(t, Integer.parseInt(value.toString()));

                                        } else if (objectsAreSameType(Long.class, field.getType()) ||
                                                objectsAreSameType(long.class, field.getType())) {

                                            if (value.length() == 0) {
                                                value.append("0");
                                            }
                                            field.setAccessible(true);
                                            field.set(t, Long.parseLong(value.toString()));

                                        } else if (objectsAreSameType(Float.class, field.getType()) ||
                                                objectsAreSameType(float.class, field.getType())) {

                                            if (value.length() == 0) {
                                                value.append("0.0");
                                            }
                                            field.setAccessible(true);
                                            field.set(t, Float.parseFloat(value.toString()));

                                        } else if (objectsAreSameType(Double.class, field.getType()) ||
                                                objectsAreSameType(double.class, field.getType())) {

                                            if (value.length() == 0) {
                                                value.append("0.0");
                                            }
                                            field.setAccessible(true);
                                            field.set(t, Double.parseDouble(value.toString()));

                                        } else if (objectsAreSameType(Boolean.class, field.getType()) ||
                                                objectsAreSameType(boolean.class, field.getType())) {

                                            if (value.length() == 0) {
                                                value.append("false");
                                            }
                                            field.setAccessible(true);
                                            field.set(t, Boolean.valueOf(value.toString()));

                                        } else if (objectsAreSameType(String.class, field.getType())) {

                                            if (value.length() == 0) {
                                                value.append("false");
                                            }
                                            field.setAccessible(true);
                                            field.set(t, value.toString());

                                        } else if (objectsAreSameType(Date.class, field.getType())) {

                                            field.setAccessible(true);
                                            if (value.length() == 0) {
                                                field.set(t, new Date());
                                            } else {
                                                Date date = new SimpleDateFormat(column.dateTimeFormat(),
                                                        Locale.ENGLISH).parse(value.toString());
                                                field.set(t, date);
                                            }

                                        } else if (column.converter() != void.class) {

                                            ExcelColumnConverter<?> excelColumnConverter =
                                                    (ExcelColumnConverter<?>) column.converter().newInstance();
                                            field.setAccessible(true);
                                            field.set(t, excelColumnConverter.convertToFieldValue(value.toString()));

                                        }
                                    } catch (IllegalAccessException | ParseException | InstantiationException e) {
                                        e.printStackTrace();
                                    }
                                });
                        });
                        if (noDuplicate && rowsEntries.stream().anyMatch(o -> o.toString().equals(t.toString()))) {
                            continue;
                        }
                        rowsEntries.add(t);
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return rowsEntries;
    }

    private static boolean objectsAreSameType(Class<?> objectTypeCheck, Class<?>... objectTypes) {
        for (Class<?> objectType : objectTypes) {
            if (objectType != objectTypeCheck) {
                return false;
            }
        }
        return true;
    }
    
}
