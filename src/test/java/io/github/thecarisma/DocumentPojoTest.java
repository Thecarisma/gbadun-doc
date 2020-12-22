package io.github.thecarisma;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Adewale Azeez <azeezadewale98@gmail.com>
 * @date 22-Dec-20 06:58 PM
 */
public class DocumentPojoTest {

    @Test
    public void testBasicExcelSheet1() throws IOException {
        InputStream stream = getClass().getResourceAsStream("/sample_sheet_1.xlsx");
        List<UserInfo> userInfo = DocumentPojo.fromExcel(stream, UserInfo.class, true);
    }

}
