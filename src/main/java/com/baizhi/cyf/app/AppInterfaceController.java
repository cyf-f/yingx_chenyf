package com.baizhi.cyf.app;

import com.baizhi.cyf.common.CommonResult;
import com.baizhi.cyf.entity.VideoPO;
import com.baizhi.cyf.service.VideoService;
import com.baizhi.cyf.util.AliyunUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("app")
public class AppInterfaceController {

    @Resource
    VideoService videoService;

    @RequestMapping("getPhoneCode")
    public CommonResult getPhoneCode(String phone){
        //发送手机验证码
        try {
            String random = AliyunUtil.getRandom(6);
            System.out.println(random);
            String message = AliyunUtil.getSendPhoneCode(phone, random);
            System.out.println(message);
            return new CommonResult().success("100","验证码发送成功",phone);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed("104","验证码发送失败");
        }
    }

    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime(){
        try {
            List<VideoPO> videoPOS = videoService.queryByReleaseTime();
            return  new CommonResult().success(videoPOS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new CommonResult().failed();
        }
    }
}
