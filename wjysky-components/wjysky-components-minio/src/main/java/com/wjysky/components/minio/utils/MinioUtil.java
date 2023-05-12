package com.wjysky.components.minio.utils;

import com.wjysky.components.minio.config.MinioProperties;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;

/**
 * MinioClientUtil
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-03-09 16:36:13
 * @apiNote Minio存储服务客户端
 */
@Slf4j
public class MinioUtil {

    private static MinioClient client;

    private String url;

    private String accessKey;

    private String secretKey;

    private String bucket;

    public MinioUtil(MinioProperties properties) {
        url = properties.getUrl();
        accessKey = properties.getAccessKey();
        secretKey = properties.getSecretKey();
        bucket = properties.getBucket();
    }

    /**
     * @bizName 初始化Minio存储服务客户端
     *
     * @title init
     * @apiNote ${todo}
     * @param
     * @author 王俊元（wangjunyuan@talkweb.com.cn）
     * @date 2023/3/9 16:41
     * @return void
     **/
    public void init() throws Exception {
        if (null != client) {
            return;
        }
        log.info("开始初始化Minio存储服务\nurl：{}\nbucket：{}", url, bucket);
        client = MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
        makeBucket(bucket);
        log.info("Minio存储服务初始化完毕");
    }

    /**
     * @bizName 创建存储桶
     *
     * @title makeBucket
     * @apiNote 如果指定存储桶已存在则直接使用，不存在则创建
     * @param bucket
     * @author 王俊元（wangjunyuan@talkweb.com.cn）
     * @date 2023/3/9 16:41
     * @return void
     **/
    public void makeBucket(String bucket) throws Exception {
        boolean bucketExist = client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!bucketExist) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    /**
     * @bizName 上传文件
     *
     * @title uploadFile
     * @apiNote ${todo} 
     * @param fileName 上传服务器后的文件名
     * @param ins 文件流
     * @param fileSize 文件大小
     * @author 王俊元（wangjunyuan@talkweb.com.cn）
     * @date 2023/3/9 16:45
     * @return String
     **/
    public String uploadFile(String fileName, InputStream ins, long fileSize) throws Exception {
        init();
        PutObjectArgs.Builder putObjectArgsBuilder = PutObjectArgs.builder().bucket(bucket)
                .object(fileName).stream(ins, fileSize, 5 * 1024 * 1024); // minio存储分片除最后一片外其余分片最小限制为5MB
        ObjectWriteResponse response = client.putObject(putObjectArgsBuilder.build());
        return url + File.separator + bucket + File.separator + fileName;
    }

    /**
     * @bizName 下载文件
     *
     * @title downloadFile
     * @apiNote ${todo} 
     * @param fileName 服务器上的文件名，如果除存储桶还有其他路径则需带上剩余路径
     * @author 王俊元（wangjunyuan@talkweb.com.cn）
     * @date 2023/3/9 16:47
     * @return java.io.InputStream
     **/
    public InputStream downloadFile(String fileName) throws Exception {
        init();
        GetObjectArgs.Builder getObjectArgsBuilder = GetObjectArgs.builder().bucket(bucket).object(fileName);
        return client.getObject(getObjectArgsBuilder.build());
    }

    /**
     * @bizName 获取服务器文件的签名url
     *
     * @title getMinioURL
     * @apiNote 有效期最大为7天
     * @param fileName 服务器上的文件名，如果除存储桶还有其他路径则需带上剩余路径
     * @param expTime 有效时间，单位：秒，最大为：7 * 24 * 60 * 60
     * @author 王俊元（wangjunyuan@talkweb.com.cn）
     * @date 2023/3/9 16:52
     * @return java.lang.String
     **/
    public String getMinioURL(String fileName, int expTime) throws Exception {
        init();
        //生成的预签名url可访问的有效时间，最大期限7天
        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucket).object(fileName).expiry(expTime).build();
        return client.getPresignedObjectUrl(build);
    }
}
