package com.baizhi.cyf.controller;


import com.baizhi.cyf.entity.User;
import com.baizhi.cyf.service.UserService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    UserService userService;

    @ResponseBody
    @RequestMapping("queryByPage")
    public HashMap<String, Object> queryByPage(Integer page,Integer rows){
        HashMap<String, Object> map = userService.queryByPage(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("edit")
    public Object edit(User user, String oper){

        //1.数据入库   返回数据id
        //2.添加之后  做文件上传
        //3.修改文件路径
        System.out.println("==user=="+user);
        String id=null;
        if(oper.equals("add")){
            id = userService.add(user);
            return id;
        }

        if(oper.equals("edit")){
            userService.update(user);

        }

        if(oper.equals("del")){
            HashMap<String, Object> map = userService.delete(user);
            return map;
        }
        return "";
    }

    @ResponseBody
    @RequestMapping("uploadUserPicImg")
    public void uploadUserPicImg(MultipartFile picImg, String id, HttpServletRequest request){



        //A文件上传
        //1.根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");

        //创建文件夹
        File file = new File(realPath);
        //判断文件夹是否存在
        if(!file.exists()){
            file.mkdirs();  //创建文件夹
        }

        //2.获取文件名
        String filename = picImg.getOriginalFilename();

        //给文件名加时间戳区分
        String newName=new Date().getTime()+"-"+filename;

        //3.文件上传
        try {
            picImg.transferTo(new File(realPath,newName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //B修改数据
        User user = new User();
        user.setId(id);
        user.setPicImg(newName);

        //调用修改方法
        userService.update(user);

    }

    @ResponseBody
    @RequestMapping("uploadUserPicImgQiniu")
    public void uploadUserPicImgQiniu(MultipartFile picImg, String id, HttpServletRequest request){

        //1.向七牛云提交文件

        String filename = picImg.getOriginalFilename();
        String newName=new Date().getTime()+"-"+filename;

        //将文件转为字节数组
        byte[] bytes=null;
        try {
            bytes = picImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //构造一个带指定Region对象的配置类  参数：指定的区域  华北
        Configuration cfg = new Configuration(Region.region1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        String accessKey = "pCfMTI1HbNZlBgExwnmsdMESoJvvG9hrHqoJ1H5h"; //密钥：你的AK
        String secretKey = "4yBiB-LNDZfjV3dAfhYDhs_IpaaYkhd_w-clEsJb"; //密钥：你的SK
        String bucket = "cyf-video";  //存储空间的名字

        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //String localFilePath = "C:\\Users\\NANAN\\Desktop\\video\\人民的名义.mp4";  //文件本地路径
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = newName;
        //根据密钥去做授权
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            //上传   文件上传  文件字节数组
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);  //文件名
            //System.out.println(putRet.hash);  //件内容的hash值
            //http://q5u1l78s3.bkt.clouddn.com/照片.jpg  网络路径
            //http://q5u1l78s3.bkt.clouddn.com/人民的名义.mp4

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

        //1.修改数据
        //B修改数据
        User user = new User();
        user.setId(id);
        user.setPicImg("http://q5um2in1x.bkt.clouddn.com/"+newName);

        //调用修改方法
        userService.update(user);
    }

    @ResponseBody
    @RequestMapping("getPhone")
    public HashMap<String, Object> getPhone(String phone){
        System.out.println("==phone: "+phone);
        HashMap<String, Object> map = userService.getPhone(phone);
        return map;
    }


    /*@RequestMapping("poiOut")
    public void poiOut(HttpServletResponse response){
        try {
            //就是对service的方法进行调用
            userService.export(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
}
