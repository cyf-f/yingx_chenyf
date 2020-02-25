package com.baizhi.cyf;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.cyf.po.Emp;
import com.baizhi.cyf.po.Phonto;
import com.baizhi.cyf.po.Teacher;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EasyPoiTests {

    @Test
    public void testExports() {

        ArrayList<Emp> emps = new ArrayList<>();
        emps.add(new Emp("1","小蛋黄",23,new Date()));
        emps.add(new Emp("2","小可爱",18,new Date()));
        emps.add(new Emp("3","小狗蛋",16,new Date()));
        emps.add(new Emp("4","小垃圾",7,new Date()));
        emps.add(new Emp("5","小小黑",22,new Date()));

        //配置导出参数  参数：标题,工作表名称
        ExportParams exportParams = new ExportParams("学生信息表", "184班");

        //使用导出工具类中方法做导出  参数：导出参数对象,实体类对象,要导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Emp.class, emps);

        //导出Excel到本地
        try {
            workbook.write(new FileOutputStream(new File("D://EasyPoi-184.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExportss() {

        //学生数据
        ArrayList<Emp> emps = new ArrayList<>();
        emps.add(new Emp("1","小蛋黄",23,new Date()));
        emps.add(new Emp("2","小可爱",18,new Date()));
        emps.add(new Emp("3","小狗蛋",16,new Date()));
        emps.add(new Emp("4","小垃圾",7,new Date()));
        emps.add(new Emp("5","小小黑",22,new Date()));

        //教师数据
        ArrayList<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher("1","suns",emps));
        teachers.add(new Teacher("2","yesji",emps));

        //配置导出参数  参数：标题,工作表名称
        ExportParams exportParams = new ExportParams("教师学生信息","学生信息", "184班");
        //使用导出工具类中方法做导出  参数：导出参数对象,实体类对象,要导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Teacher.class, teachers);

        //导出Excel到本地
        try {
            workbook.write(new FileOutputStream(new File("D://EasyPoi-184.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testImport() {
        //配置导入参数
        ImportParams params = new ImportParams();
        params.setTitleRows(2);  //标题占几行 默认0
        params.setHeadRows(2);  //表头占几行  默认1

        try {
            //获取导入的文件
            FileInputStream inputStream = new FileInputStream(new File("D://EasyPoi-184.xls"));
            //导入    参数：读入流,实体类映射
            List<Teacher> teacherList = ExcelImportUtil.importExcel(inputStream,Teacher.class, params);

            //遍历
            for (Teacher teacher : teacherList) {
                System.out.println("教师： "+teacher);

                for (Emp e : teacher.getStudents()) {
                    System.out.println("学生: "+e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testExportsss() {

        ArrayList<Phonto> phontoList = new ArrayList<>();
        phontoList.add(new Phonto("1","D:\\184_code\\yingx_zhangcn184\\src\\main\\webapp\\upload\\cover\\1581996447528-2020宣传视频.jpg",23,new Date()));
        phontoList.add(new Phonto("2","src/main/webapp/upload/cover/1581996037844-a_THbifTHY0u8h1577348929.jpg",18,new Date()));
        phontoList.add(new Phonto("3","src/main/webapp/upload/cover/1581996037844-a_THbifTHY0u8h1577348929.jpg",16,new Date()));
        phontoList.add(new Phonto("4","D:\\184_code\\yingx_zhangcn184\\src\\main\\webapp\\upload\\cover\\1581996447528-2020宣传视频.jpg",7,new Date()));
        phontoList.add(new Phonto("5","http://q5vlef24j.bkt.clouddn.com/1581996037844-a_THbifTHY0u8h1577348929.jpg",22,new Date()));

        //配置导出参数  参数：标题,工作表名称
        ExportParams exportParams = new ExportParams("学生信息表", "184班");

        //使用导出工具类中方法做导出  参数：导出参数对象,实体类对象,要导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Phonto.class, phontoList);

        //导出Excel到本地
        try {
            workbook.write(new FileOutputStream(new File("D://EasyPoi-184s.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testImportPhoto() {
        //配置导入参数
        ImportParams params = new ImportParams();
        params.setTitleRows(1);  //标题占几行 默认0
        params.setHeadRows(1);  //表头占几行  默认1
        params.setSaveUrl("D://photo-184");
        //params.setNeedSave(true);

        try {
            //获取导入的文件
            FileInputStream inputStream = new FileInputStream(new File("D://EasyPoi-184s.xls"));
            //导入    参数：读入流,实体类映射
            List<Phonto> teacherList = ExcelImportUtil.importExcel(inputStream,Phonto.class, params);

            //遍历
            for (Phonto p : teacherList) {
                System.out.println("图片对象： "+p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
