package com.baizhi.cyf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoPO {

    private String id;
    private String videoTitle;
    private String description;  //描述
    private String cover;   //封面路径
    private String path;  //视频路径
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uploadTime;
    private Integer likeCount;
    private String cateName;
    private String userPhoto;
}
