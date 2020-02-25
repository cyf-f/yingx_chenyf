package com.baizhi.cyf.service;

import com.baizhi.cyf.entity.Admin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
public interface AdminService {
    public HashMap<String,String> login(Admin admin, String enCode);
}
