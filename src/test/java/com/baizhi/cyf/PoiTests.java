package com.baizhi.cyf;

import com.baizhi.cyf.po.Emp;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


/*

    <!--Poijar包-->
    <dependency>
          <groupId>org.apache.poi</groupId>
          <artifactId>poi</artifactId>
          <version>3.15</version>
    </dependency>

    <!--如果上面的不能用，可以试试这个jar包-->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi</artifactId>
        <version>4.0.1</version>
    </dependency>

* */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class PoiTests {

    @Test
    public void testExports() {

        //创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();

        //创建一个工作表   参数：工作表的名字(sheet1,sheet2...)
        //如果不指定工作表的名称 默认按照sheet0,sheet1命名
        Sheet sheet = workbook.createSheet("学生信息1");
        Sheet sheet2 = workbook.createSheet("教师信息2");

        //创建一行   参数：行下标(下标从0开始)
        Row row = sheet.createRow(0);

        //创建一个单元格  参数：单元格下标(下表从0开始)
        Cell cell = row.createCell(0);

        //给单元格设置内容
        cell.setCellValue("这是第一行第一个单元格");

        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("D://test-184Poi.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testExportObject() {

        ArrayList<Emp> emps = new ArrayList<>();
        emps.add(new Emp("1","小蛋黄",23,new Date()));
        emps.add(new Emp("2","小可爱",18,new Date()));
        emps.add(new Emp("3","小狗蛋",16,new Date()));
        emps.add(new Emp("4","小垃圾",7,new Date()));
        emps.add(new Emp("5","小小黑",22,new Date()));

        //创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();

        //创建一个工作表   参数：工作表的名字(sheet1,sheet2...)
        //如果不指定工作表的名称 默认按照sheet0,sheet1命名
        Sheet sheet = workbook.createSheet("学生信息1");

        //设置列宽  参数:列索引,列宽值
        sheet.setColumnWidth(3,20*256);

        //设置字体样式
        //创建字体对象
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)20);  //设置字号
        font.setBold(true); //加粗
        font.setFontName("楷体"); //设置字体
        font.setItalic(true); //字体倾斜
        font.setColor(Font.COLOR_RED); //颜色

        //创建一个样式对象
        CellStyle cellStyle1 = workbook.createCellStyle();
        //将字体样式给样式对象
        cellStyle1.setFont(font);
        //设置居中
        cellStyle1.setAlignment(HorizontalAlignment.CENTER); //设置文字据中

        //创建一行   参数：行下标(下标从0开始)  标题行
        Row rowes = sheet.createRow(0);
        //给标题行设置内容
        Cell cell1 = rowes.createCell(0);
        cell1.setCellStyle(cellStyle1); //设置样式
        cell1.setCellValue("用户信息表"); //设置内容

        //合并标题行  参数： firstRow(起始行), lastRow(结束行), firstColl(起始单元格), lastColl(结束单元格)
        CellRangeAddress range1 = new CellRangeAddress(0,0,0,3);
        //合并标题行  参数： firstRow(起始行), lastRow(结束行), firstColl(起始单元格), lastColl(结束单元格)
        CellRangeAddress range2 = new CellRangeAddress(2,6,4,4);
        //应用于哪个工作表
        sheet.addMergedRegion(range1);
        sheet.addMergedRegion(range2);

        //创建一行   参数：行下标(下标从0开始)  目录行
        Row row = sheet.createRow(1);

        //设置行高
        row.setHeight((short) (30*20));

        //目录行
        String[] title={"ID","名字","年龄","生日"};

        //处理标题行  遍历标题行
        for (int i = 0; i < title.length; i++){
            //遍历创建单元格
            Cell cell = row.createCell(i);

            //给单元格设置样式
            cell.setCellStyle(cellStyle1);

            //并且给单元格赋值
            cell.setCellValue(title[i]);
        }

        //创建日期对象
        DataFormat dataFormat = workbook.createDataFormat();
        //设置日期格式
        short format = dataFormat.getFormat("yyyy年MM月dd日");
        //创建样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        //将日期对象给样式对象
        cellStyle.setDataFormat(format);

        //处理数据行
        for (int i = 0; i < emps.size(); i++) {

            //创建一行   每次遍历创建一行  数据行
            Row rows = sheet.createRow(i+2);

            //遍历创建单元格   并且给单元格赋值
            rows.createCell(0).setCellValue(emps.get(i).getId());
            rows.createCell(1).setCellValue(emps.get(i).getName());
            rows.createCell(2).setCellValue(emps.get(i).getAge());

            //创建日期单元格
            Cell cell = rows.createCell(3);
            //给单元格设置样式
            cell.setCellStyle(cellStyle);
            //给日期单元格设置内容
            cell.setCellValue(emps.get(i).getBirthday());
        }

        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("D://test-184Poi.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test   //导入表格
    public void testImport(){

        try {
            //读取本地Excel表格
            Workbook workbook = new HSSFWorkbook(new FileInputStream(new File("D://test-184Poi.xls")));

            //获取工作表  参数：工作表的名字
            Sheet sheet = workbook.getSheet("学生信息1");

            //获取最后一行的行下标
            int lastRowNum = sheet.getLastRowNum();
            //遍历数据行
            for (int i = 2; i <= lastRowNum; i++) {
                //遍历一次去除一行数据  参数：行下标
                Row row = sheet.getRow(i);
                //获取第一个单元格数据
                String id = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                double num = row.getCell(2).getNumericCellValue();
                Integer age=(int) num; //将小数转为整数
                Date birthday = row.getCell(3).getDateCellValue();

                Emp emp = new Emp(id,name,age,birthday);

                //将对象数据插入数据库
                System.out.println("插入数据库: "+emp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
