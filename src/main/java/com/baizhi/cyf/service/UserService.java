package com.baizhi.cyf.service;

import com.baizhi.cyf.entity.User;

import java.util.HashMap;

public interface UserService {

    HashMap<String, Object> queryByPage(Integer page, Integer rows);

    String add(User user);

    void update(User user);

    HashMap<String, Object> delete(User user);

    HashMap<String, Object> getPhone(String phone);

    /*public void export(HttpServletResponse res) throws IOException;*/
}
