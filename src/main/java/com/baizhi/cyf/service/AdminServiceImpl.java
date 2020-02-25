package com.baizhi.cyf.service;

import com.baizhi.cyf.dao.AdminMapper;
import com.baizhi.cyf.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminDao;

    @Autowired
    HttpServletRequest request;

    //登录
    @Override
    public HashMap<String, String> login(Admin admin, String enCode) {

        String imageCode = (String)request.getSession().getAttribute("imageCode");

        HashMap<String, String> map = new HashMap<>();

        //1.判断验证码
        if(imageCode.equals(enCode)){
            //查询管理员
            Admin admins = adminDao.queryByUsername(admin.getUsername());
            //判断管理员是否存在
            if(admins!=null){
                //判断密码是否一致
                if(admins.getPassword().equals(admin.getPassword())){

                    //存一个管理员登陆标记
                    request.getSession().setAttribute("admin",admins);

                    map.put("status","200");
                    map.put("message","登陆成功");
                }else{
                    map.put("status","400");
                    map.put("message","密码错误");
                }
            }else{
                map.put("status","400");
                map.put("message","管理员不存在");
            }
        }else{
            map.put("status","400");
            map.put("message","验证码错误");
        }
        return map;
    }
}
