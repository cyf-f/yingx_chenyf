package com.baizhi.cyf;

import com.baizhi.cyf.dao.AdminMapper;
import com.baizhi.cyf.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxChenyfApplicationTests {

    @Autowired
    AdminMapper adminDao;

    @Test
    public void contextLoads() {
        Admin admin=adminDao.queryByUsername("admin");
        System.out.println(admin);
    }

}
