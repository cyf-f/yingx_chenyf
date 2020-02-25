package com.baizhi.cyf.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emp {

    @Excel(name = "ID",width = 20,height = 8)
    private String id;

    @Excel(name = "名字")
    private String name;

    @Excel(name = "年龄")
    private Integer age;

    @Excel(name = "生日",format = "yyyy年MM月dd日 hh点mm分ss秒",width = 30)
    private Date birthday;

}
