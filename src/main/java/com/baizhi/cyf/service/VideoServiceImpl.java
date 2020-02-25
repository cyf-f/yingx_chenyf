package com.baizhi.cyf.service;

import com.baizhi.cyf.dao.VideoMapper;
import com.baizhi.cyf.entity.Video;
import com.baizhi.cyf.entity.VideoExample;
import com.baizhi.cyf.entity.VideoPO;
import com.baizhi.cyf.util.InterceptVideoPhotoUtil;
import com.baizhi.cyf.util.QiniuUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Resource
    VideoMapper videoMapper;

    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();
        //总条数   records
        VideoExample example = new VideoExample();
        Integer records = videoMapper.selectCountByExample(example);
        map.put("records",records);

        //总页数  totals    总条数/每页展示条数
        Integer totals=records%rows==0?records/rows:records/rows+1;
        map.put("totals",totals);

        //当前页  page
        map.put("page",page);
        //数据   rows   分页
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Video> videos = videoMapper.selectByRowBounds(new Video(), rowBounds);
        map.put("rows",videos);

        return map;
    }

    @Override
    public String add(Video video) {

        String uid = UUID.randomUUID().toString();

        video.setId(uid); //id
        video.setUploadTime(new Date());
        video.setUserId("1");
        video.setCategoryId("1");
        video.setGroupId("1");

        System.out.println("=业务层插入数据=video=="+video);

        videoMapper.insert(video);

        //返回添加数据的id
        return uid;
    }

    @Override
    public void update(Video video) {
        System.out.println("修改："+video);
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    public void uploadVdieo(MultipartFile path, String id, HttpServletRequest request) {

        //2.获取文件名
        String filename = path.getOriginalFilename();
        //给文件名加时间戳区分
        String newName=new Date().getTime()+"-"+filename;
        //http://q5um2in1x.bkt.clouddn.com/1581991049337-动画.mp4
        /*
         * 1.上传视频至七牛云
         * 参数：
         *   bucket: 存储空间名
         *   file:   文件
         *   fileName:  文件名
         * */
        QiniuUtil.uploadFileQiniu("cyf-video",path,newName);

        /**
         * 2.通过视频截取一张封面
         * 获取指定视频的帧并保存为图片至指定目录
         * @param videofile  源视频文件路径
         * @param framefile  截取帧的图片存放路径
         * @throws Exception
         *
         * http://q5u1l78s3.bkt.clouddn.com/1581991049337-动画.mp4
         * D:\动画sss.jpg
         */
        //拼接远程视频路径
        String viodeFilePath="http://q5um2in1x.bkt.clouddn.com/"+newName;

        //根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/cover");
        //判断文件夹是否存在，不存在创建文件夹
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        //1581991049337-动画.mp4  newName
        String[] snames = newName.split("\\.");
        String sName=snames[0];  //1581991049337-动画
        String photoName=sName+".jpg";  //拼接图片名字   1581991049337-动画.jpg
        String photoPath=realPath+"\\"+photoName;  //频接图片报错的绝对路径

        //截取视频
        try {
            InterceptVideoPhotoUtil.fetchFrame(viodeFilePath,photoPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * 3.上传封面  七牛云
         * 参数：
         *   bucket: 存储空间名
         *   filePath:   文件
         *   fileName:  文件名
         * */
        QiniuUtil.uploadFileQiniu("cyf-cover",photoPath,photoName);

        //4.修改数据
        Video video = new Video();
        video.setPath("http://q5um2in1x.bkt.clouddn.com/"+newName);
        video.setCover("http://q5wdoqoco.bkt.clouddn.com/"+photoName);

        System.out.println("==video  update===="+video);
        //调用修改方法
        try {
            VideoExample example = new VideoExample();
            example.createCriteria().andIdEqualTo(id);
            videoMapper.updateByExampleSelective(video,example);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, Object> delete(Video video) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            //根据id查询视频数据
            VideoExample example = new VideoExample();
            example.createCriteria().andIdEqualTo(video.getId());
            Video videos = videoMapper.selectOneByExample(example);

            //1.删除数据
            videoMapper.deleteByPrimaryKey(video);

            /*
             * 2.删除视频
             * 删除七牛云文件
             * 参数：
             *   bucket: 存储空间名
             *   fileName:  文件名   人民的名义.mp4
             * */
            //http://q5u1l78s3.bkt.clouddn.com/1581937007364-抖音视频.mp4
            String[] pathSplit = videos.getPath().split("/");
            QiniuUtil.deleteFile("cyf-video",pathSplit[3]);

            /*
             * 3.删除封面
             * 删除七牛云文件
             * 参数：
             *   bucket: 存储空间名
             *   fileName:  文件名   人民的名义.mp4
             * */
            String[] coverSplit = videos.getCover().split("/");
            QiniuUtil.deleteFile("cyf-cover",coverSplit[3]);

            map.put("status","200");
            map.put("message","删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status","400");
            map.put("message","删除失败");
        }
        return map;
    }

    @Override
    public List<VideoPO> queryByReleaseTime() {
        List<VideoPO> videoPOS = videoMapper.queryByReleaseTime();
        return videoPOS;
    }
}
