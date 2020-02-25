package com.baizhi.cyf.controller;

import com.baizhi.cyf.entity.Admin;
import com.baizhi.cyf.service.AdminService;
import com.baizhi.cyf.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminservice;

    @RequestMapping("/getImageCode")
    public void getImageCode(HttpServletRequest request, HttpServletResponse response){
        //1.获取验证码随机字符
        String randomCode= ImageCodeUtil.getSecurityCode();
        //2.存储验证码随机字符
        request.getSession().setAttribute("imageCode",randomCode);
        //3.个人那句验证码字符生成图片
        BufferedImage image=ImageCodeUtil.createImage(randomCode);
        //4.将验证码图片响应到页面
        try {
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/login")
    @ResponseBody
    public HashMap<String,String> login(Admin admin, String enCode){
        HashMap<String, String> map = adminservice.login(admin, enCode);
        return map;
    }

    /*安全退出*/
    @RequestMapping("/exit")
    public String exit(HttpSession session)throws Exception{
        session.removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }
}
