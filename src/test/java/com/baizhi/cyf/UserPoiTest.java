package com.baizhi.cyf;

import com.baizhi.cyf.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserPoiTest {

    @Autowired
    UserService userService;

    @Test
    public void tests(){

        //创建一个人excel文档
        Workbook workbook=new HSSFWorkbook();
        //创建一个工作表  参数：表单名字
        Sheet sheet=workbook.createSheet("用户信息表");

        //创建一行   参数：行下标(下标从0开始)
        Row row = sheet.createRow(0);

        //创建一个单元格  参数：单元格下标(下表从0开始)
        Cell cell = row.createCell(0);

        //给单元格设置内容
        cell.setCellValue("这是第一行第一个单元格");

        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("D://testUserPoi.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@Test
    public void UserExports(){

        *//*ArrayList<User> users=new ArrayList<>();*//*
        try {
            userService.export(res);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/
}
