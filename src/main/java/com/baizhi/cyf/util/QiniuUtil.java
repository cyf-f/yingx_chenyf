package com.baizhi.cyf.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class QiniuUtil {


    private static String accessKey = "pCfMTI1HbNZlBgExwnmsdMESoJvvG9hrHqoJ1H5h"; //密钥：你的AK
    private static String secretKey = "4yBiB-LNDZfjV3dAfhYDhs_IpaaYkhd_w-clEsJb"; //密钥：你的SK


    /*
    * 上传视频至七牛云
    * 参数：
    *   bucket: 存储空间名
    *   file:   文件
    *   fileName:  文件名
    * */
    public static void uploadFileQiniu(String bucket, MultipartFile file,String fileName){
        //将文件转为字节数组
        byte[] bytes=null;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //构造一个带指定Region对象的配置类  参数：指定的区域  华北
        Configuration cfg = new Configuration(Region.region1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        //String bucket = "184-video";  //存储空间的名字

        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //String localFilePath = "C:\\Users\\NANAN\\Desktop\\video\\人民的名义.mp4";  //文件本地路径
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        //String key = fileName;
        //根据密钥去做授权
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            //上传   文件上传  文件字节数组
            Response response = uploadManager.put(bytes, fileName, upToken);
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
    }

    /*
     * 上传文件至七牛云
     * 参数：
     *   bucket: 存储空间名
     *   filePath:   文件
     *   fileName:  文件名
     * */
    public static void uploadFileQiniu(String bucket, String filePath,String fileName){

        //构造一个带指定Region对象的配置类  参数：指定的区域  华北
        Configuration cfg = new Configuration(Region.region1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        //String bucket = "184-video";  //存储空间的名字

        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //String localFilePath = "C:\\Users\\NANAN\\Desktop\\video\\人民的名义.mp4";  //文件本地路径
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        //String key = fileName;
        //根据密钥去做授权
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            //上传   文件上传  文件字节数组
            Response response = uploadManager.put(filePath, fileName, upToken);
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
    }

    /*
     * 删除七牛云文件
     * 参数：
     *   bucket: 存储空间名
     *   fileName:  文件名
     * */
    public static void deleteFile(String bucket,String fileName){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        //String bucket = "184-video";  //存储空间的名字
        //String key = "人民的名义.mp4";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, fileName);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

}
