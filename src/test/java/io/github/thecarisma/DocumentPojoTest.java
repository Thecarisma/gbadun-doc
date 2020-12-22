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
    public void testBasicExcelSheetXlsx() throws IOException {
        InputStream stream = getClass().getResourceAsStream("/sample_sheet_1.xlsx");
        List<UserInfo> userInfos = DocumentPojo.fromExcel(stream, UserInfo.class);
        for (UserInfo userInfo : userInfos) {
            System.out.println(userInfo);
        }
    }

    @Test
    public void testBasicExcelSheetXls() throws IOException {
        InputStream stream = getClass().getResourceAsStream("/sample_sheet_1.xls");
        List<UserInfo> userInfos = DocumentPojo.fromExcel2003(stream, UserInfo.class);
        for (UserInfo userInfo : userInfos) {
            System.out.println(userInfo);
        }
    }

}
