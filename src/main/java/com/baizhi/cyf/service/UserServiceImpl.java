package com.baizhi.cyf.service;

import com.baizhi.cyf.annotation.AddLog;
import com.baizhi.cyf.dao.UserMapper;
import com.baizhi.cyf.entity.User;
import com.baizhi.cyf.entity.UserExample;
import com.baizhi.cyf.util.AliyunUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    HttpServletRequest request;

    @AddLog(value = "查询用户信息")
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();
        //总条数 records
        UserExample example = new UserExample();
        Integer records = userMapper.selectCountByExample(example);
        map.put("records", records);

        //总页数  totals    总条数/每页展示条数
        Integer totals = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("totals", totals);

        //当前页  page
        map.put("page", page);
        //数据   rows   分页
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users = userMapper.selectByRowBounds(new User(), rowBounds);
        map.put("rows", users);


        return map;
    }

    @AddLog(value = "添加用户")
    @Override
    public String add(User user) {
        String uid = UUID.randomUUID().toString();
        user.setId(uid); //id
        user.setStatus("1"); //状态
        user.setCreateDate(new Date()); //注册时间
        user.setScore(0.0); //学分
        userMapper.insert(user);
        //返回添加数据的id
        return uid;
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public HashMap<String, Object> delete(User user) {
        HashMap<String, Object> map = new HashMap<>();

        userMapper.deleteByPrimaryKey(user);
        map.put("status", "200");
        map.put("message", "该一级类别下有二级类别");

        return map;
    }

    @Override
    public HashMap<String, Object> getPhone(String phone) {
        HashMap<String, Object> map = new HashMap<>();
        String message = null;
        try {
            //1.获取随机数
            String random = AliyunUtil.getRandom(6);
            System.out.println(random);
            //2.存储随机数     session    redis  key value 设置超时时间
            //3.给用户发送手机验证码
            message = AliyunUtil.getSendPhoneCode(phone, random);
            map.put("status", "200");
            map.put("message", "发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "400");
            map.put("message", "发送失败：" + message);
        }
        return map;
    }

   /* @Override
    public void export(HttpServletResponse res) throws IOException {
        //从数据库中将用户信息查询出来
        List<User> all = userMapper.selectAll();
        //因为数据库中只存了图片的名字，我们在这里需要对于图片路径封装到User对象中
        for (User user : all) {
            String img = user.getPicImg();
            //图片在项目中的存储路径前缀
            String path = "http://localhost:8989/yingx_chenyf/upload/photo/";
            //图片在项目中的存储路径
            String newPath = path + img;
            user.setPicImg(newPath);
        }

        Workbook wb = ExcelExportUtil.exportExcel(new ExportParams("应学App用户信息表", "员工信息表"), User.class, all);

        try {
            //设置下载的文件名，中文需要进行编码处理
            String encode = URLEncoder.encode("用户信息表.xls", "UTF-8");
            //设置响应头信息，以附件形式下载
            res.setHeader("content-disposition", "attachment;filename=" + encode);
            //获取输出流
            ServletOutputStream outputStream = res.getOutputStream();
            wb.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            wb.close();
        }

    }*/
}
