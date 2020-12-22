package io.github.thecarisma;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 * @date 22-Dec-20 05:47 PM
 */
public class ExcelColumnTest {

    @Test
    public void testExcelColumn1() {
        UserInfo userInfo = new UserInfo();
        Field[] fields = userInfo.getClass().getDeclaredFields();
        for (Field field : fields) {
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            if (field.getName().equals("barrenField")) {
                Assert.assertNull(excelColumn);
            } else {
                Assert.assertNotNull(excelColumn);
            }

        }
    }

}
