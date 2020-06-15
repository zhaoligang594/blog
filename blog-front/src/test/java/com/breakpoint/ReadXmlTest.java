package com.breakpoint;

import com.breakpoint.dao.YouhuijuanMapper;
import com.breakpoint.entity.Youhuijuan;
import jxl.Sheet;
import jxl.Workbook;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/04/08
 */
public class ReadXmlTest extends BlogFrontApplicationTests {

    @Resource
    private YouhuijuanMapper youhuijuanMapper;


    private String excelPath = "/Users/breakpoint/Downloads/";

    private String excelName = "精选优质商品清单(内含优惠券)-2019-06-12.xls";


    @Test
    public void test() throws Exception {


        File file = new File(excelPath + excelName);
        InputStream is = new FileInputStream(file.getAbsolutePath());

        Workbook wb = Workbook.getWorkbook(is);

        int sheet_size = wb.getNumberOfSheets();

        Sheet sheet = wb.getSheet(0);

        for (int i = 0; i < sheet.getRows(); i++) {

            Date date=new Date();
            String id = sheet.getCell(0, i).getContents();
            String name = sheet.getCell(1, i).getContents();
            String price = sheet.getCell(6, i).getContents();
            String priceYouhui = sheet.getCell(17, i).getContents();
            String url = sheet.getCell(2, i).getContents();
            String youhuijuan = sheet.getCell(21, i).getContents();


            System.out.println(id+"  "+name+" "+url+" "+youhuijuan);
            Youhuijuan youhuijuan1=new Youhuijuan();
            youhuijuan1.setFoodId(id);
            youhuijuan1.setFoodName(name);
            youhuijuan1.setFoodPriPicture(url);
            youhuijuan1.setFoodMonUrl(youhuijuan);
            youhuijuan1.setFoodPrice(price);
            youhuijuan1.setFoodYouhui(priceYouhui);
            youhuijuan1.setGmtCreate(date);
            youhuijuan1.setGmtUpdate(date);
            youhuijuanMapper.insert(youhuijuan1);

        }


    }


}
