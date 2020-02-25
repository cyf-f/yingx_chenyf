package com.baizhi.cyf.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_user") //表名
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Excel(name = "ID")
    @Id
    private String id;

    @Excel(name = "用户名")
    private String username;

    @Excel(name = "手机号")
    private String phone;

    @Excel(name = "头像",type = 2,width = 40 , height = 20)
    @Column(name = "pic_img")
    private String picImg;

    @Excel(name = "签名")
    private String sign;

    @Excel(name = "状态",replace = {"激活_1","冻结_0"})
    private String status;

    @Excel(name = "微信")
    private String wechat;

    @Excel(name = "注册日期",format ="yyyy-MM-dd" )
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date")
    private Date createDate;

    @Excel(name = "学分")
    private Double score;

}