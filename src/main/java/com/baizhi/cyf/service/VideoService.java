package com.baizhi.cyf.service;

import com.baizhi.cyf.entity.Video;
import com.baizhi.cyf.entity.VideoPO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface VideoService {

    HashMap<String, Object> queryByPage(Integer page, Integer rows);

    String add(Video video);

    void update(Video video);

    void uploadVdieo(MultipartFile path, String id, HttpServletRequest request);

    HashMap<String, Object> delete(Video video);

    List<VideoPO> queryByReleaseTime();
}
