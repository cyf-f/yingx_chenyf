package com.baizhi.cyf.service;

import com.baizhi.cyf.entity.Category;

import java.util.HashMap;

public interface CategoryService {
    HashMap<String, Object> queryByPage(Integer page, Integer rows);

    HashMap<String, Object> queryByTwoPage(Integer page, Integer rows, String parentId);

    HashMap<String, Object> delete(Category sort);
}
