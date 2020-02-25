package com.baizhi.cyf.controller;


import com.baizhi.cyf.entity.Video;
import com.baizhi.cyf.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("video")
public class VideoController {

    @Resource
    VideoService videoService;

    @ResponseBody
    @RequestMapping("queryByPage")
    public HashMap<String, Object> queryByPage(Integer page,Integer rows){
        HashMap<String, Object> map = videoService.queryByPage(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("edit")
    public Object edit(Video video, String oper){

        //1.数据入库   返回数据id
        //2.添加之后  做文件上传
        //3.修改文件路径
        System.out.println("==video=="+video);
        if(oper.equals("add")){
            String id = videoService.add(video);
            return id;
        }

        if(oper.equals("edit")){
            videoService.update(video);
        }

        if(oper.equals("del")){
            HashMap<String, Object> map = videoService.delete(video);
            return map;
        }

        return "";
    }

    @ResponseBody
    @RequestMapping("uploadVdieo")
    public void uploadVdieo(MultipartFile path, String id, HttpServletRequest request){
        videoService.uploadVdieo(path,id,request);
    }
}
