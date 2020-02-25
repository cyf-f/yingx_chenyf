package com.baizhi.cyf.dao;

import com.baizhi.cyf.entity.Video;
import com.baizhi.cyf.entity.VideoPO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {

    List<VideoPO> queryByReleaseTime();
}