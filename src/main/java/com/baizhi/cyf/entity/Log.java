package com.baizhi.cyf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    private String id;
    private String adminName;
    private Date time;
    private String action;
    private String status;

}
