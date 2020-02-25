package com.baizhi.cyf.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    //@Excel(name = "id",needMerge = true)
    @ExcelIgnore
    private String id;
    @Excel(name = "教师名",needMerge = true)
    private String name;

    @ExcelCollection(name = "学生")
    private List<Emp> students;

}
