package com.baizhi.cyf;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QiniuTests {

    @Test
    public void testUpload() {

        //构造一个带指定Region对象的配置类  参数：指定的区域  华北
        Configuration cfg = new Configuration(Region.region1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        String accessKey = "pCfMTI1HbNZlBgExwnmsdMESoJvvG9hrHqoJ1H5h"; //密钥：你的AK
        String secretKey = "4yBiB-LNDZfjV3dAfhYDhs_IpaaYkhd_w-clEsJb"; //密钥：你的SK
        String bucket = "cyf-video";  //存储空间的名字

        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\cyf\\Desktop\\1581991049337-动画.mp4";  //文件本地路径
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "1581991049337-动画.mp4";
        //根据密钥去做授权
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            //上传   文件上传
            Response response = uploadManager.put(localFilePath, key, upToken);
            //uploadManager.put(bytes, key, upToken)
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);  //文件名
            System.out.println(putRet.hash);  //件内容的hash值
            //http://q5um2in1x.bkt.clouddn.com/照片.jpg  网络路径
            //http://q5um2in1x.bkt.clouddn.com/人民的名义.mp4

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }

    //公开空间下载
    @Test
    public void testDownLoad(){
        String fileName = "人民的名义.mp4";  //文件名
        String domainOfBucket = "http://q5um2in1x.bkt.clouddn.com";   //空间域名
        String finalUrl = String.format("%s/%s", domainOfBucket, fileName);
        System.out.println(finalUrl);  //http://q5um2in1x.bkt.clouddn.com/人民的名义.mp4  网络路径

        String url="D://人民的名义sss.mp4";
        downloadFile(finalUrl,url);

    }

    //私有空间下载
    @Test
    public void testDownLoads() throws UnsupportedEncodingException {

        String fileName = "人民的名义.mp4";  //文件名
        String domainOfBucket = "http://q5um2in1x.bkt.clouddn.com";   //空间域名
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);

        String accessKey = "pCfMTI1HbNZlBgExwnmsdMESoJvvG9hrHqoJ1H5h"; //密钥：你的AK
        String secretKey = "4yBiB-LNDZfjV3dAfhYDhs_IpaaYkhd_w-clEsJb"; //密钥：你的SK
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        System.out.println(finalUrl);

        String url="D://人民的名义aaa.mp4";
        downloadFile(finalUrl,url);
    }

    /**
     * 下载远程文件并保存到本地
     *
     * @param remoteFilePath-远程文件路径
     * @param localFilePath-本地文件路径（带文件名）
     * http://q5qobmi0y.bkt.clouddn.com/01.jpg
     */
    public void downloadFile(String remoteFilePath, String localFilePath) {
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        try {
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection) urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void delete(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        String accessKey = "pCfMTI1HbNZlBgExwnmsdMESoJvvG9hrHqoJ1H5h"; //密钥：你的AK
        String secretKey = "4yBiB-LNDZfjV3dAfhYDhs_IpaaYkhd_w-clEsJb"; //密钥：你的SK
        String bucket = "cyf-video";  //存储空间的名字
        String key = "1.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    @Test
    public void intercept(){
        String ss="1581937007364-抖音视频.mp4";

        String[] split = ss.split("\\.");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            System.out.println(s);
        }


        /*String name="http://q5u1l78s3.bkt.clouddn.com/1581937007364-抖音视频.mp4";
        String[] split = name.split("\\/");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            System.out.println(s);
        }*/
        //System.out.println(split[3]);
        /*
        *
        *
http:

q5u1l78s3.bkt.clouddn.com
1581937007364-抖音视频.mp4
        * */
    }

}
