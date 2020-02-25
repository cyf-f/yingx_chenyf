package com.baizhi.cyf.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_user") //表名
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Excel(name = "ID",width = 20,height = 8)
    private String id;

    @Excel(name = "用户名")
    private String username;

    @Excel(name = "手机号")
    private String phone;

   @Excel(name = "头像",type = 2)
    private String picImg;

    @Excel(name = "签名")
    private String sign;

    @Excel(name = "状态")
    private String status;

    @Excel(name = "微信")
    private String wechat;

   @Excel(name = "创建时间",format ="yyyy年MM月dd日 hh点mm分ss秒",width = 30 )
    private Date createDate;

    @Excel(name = "学分")
    private Double score;

}