package com.baizhi.cyf.dao;

import com.baizhi.cyf.entity.Admin;
import tk.mybatis.mapper.common.Mapper;

public interface AdminMapper extends Mapper<Admin> {

    Admin queryByUsername(String username);
}