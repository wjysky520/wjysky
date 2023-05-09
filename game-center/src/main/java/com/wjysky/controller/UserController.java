package com.wjysky.controller;

import com.alibaba.fastjson.JSON;
import com.wjysky.entity.DataApi;
import com.wjysky.entity.ao.GetSysCfgListAO;
import com.wjysky.entity.db.SysCfg;
import com.wjysky.entity.db.SystemConfig;
import com.wjysky.entity.dto.FileOpenDTO;
import com.wjysky.entity.query.SysCfgQuery;
import com.wjysky.feign.service.ITestService;
import com.wjysky.mq.producer.MQProducerService;
import com.wjysky.service.ISysService;
import com.wjysky.utils.MinioUtil;
import com.wjysky.utils.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user", method = RequestMethod.POST)
@Slf4j
public class UserController {

    private final ITestService testService;

    private final ISysService sysService;

    private final MQProducerService mqProducerService;

    private final MinioUtil minioUtil;

    @Value("${rocketmq.topic[0]}")
    private String topic;

    @Value("${rocketmq.tag[0]}")
    private String tag;

    /**
     *
     * @ClassName UserController
     * @Title test
     * @Description ${todo}
     * @param
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/22 22:42:48
     * @Return
     * @throws
    **/
    @RequestMapping("test")
    public List<SystemConfig> test() throws Exception {
        log.info("---------------");
        DataApi<List<SystemConfig>> dataApi = testService.query("你好，我来了");
        try {
            File file1 = new File("C:\\Users\\admin\\Pictures\\desktop\\8.jpg");
            File file2 = new File("C:\\Users\\admin\\Desktop\\1.jpg");
            minioUtil.uploadFile("2.jpg", new FileInputStream(file1), file1.length());
            ObjectUtil.inputStream2file(minioUtil.downloadFile("1.jpg"), file2);
            log.info(minioUtil.getMinioURL("1.jpg", 7 * 24 * 60 * 60));
            mqProducerService.syncSendMsg(topic, tag, dataApi.getData(), dataApi.getSystemTime() + "");
        } catch (Exception e) {
            log.error("业务异常", e);
        }
        return dataApi.getData();
    }

    @RequestMapping("getSysCfgList")
    public List<SysCfg> getSysCfgList(@RequestBody GetSysCfgListAO ao) {
        return sysService.getSysCfgList(ObjectUtil.convertBean(ao, SysCfgQuery.class));
    }

    @GetMapping("download")
    public void download(HttpServletResponse response) {
        Map mapParam = JSON.parseObject("{\"fileName\":\"测试发布单推送1101.html\",\"filePath\":\"1587263423144525824/20221101/\",\"systemType\":60}");
        FileOpenDTO fileDTO = postData("http://10.125.86.201/ecm/http-adapter/adapter/file/downloadFile", mapParam, FileOpenDTO.class);
//        FileOpenDTO fileDTO = postData("http://127.0.0.1:8289/adapter/logTest/downloadFile", mapParam, FileOpenDTO.class);
//        Map mapHead = new HashMap();
//        mapHead.put("Content-Type", "application/json; charset=UTF-8");
//        mapHead.put("Accept-Encoding", "");
//        mapHead.put("Accept", "application/json");
//        FileOpenDTO fileDTO = client("http://10.124.150.230:8000/api/chinaUnicom/manageCenter/contractSystem/downloadFile/v1", "DOWNLOAD_FILE_REQ", "DOWNLOAD_FILE_RSP", mapParam, mapHead, FileOpenDTO.class);
        byte[] bytes = fileDTO.getData();
        OutputStream toClient = null;
        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=test.html");
            response.addHeader("Content-Length", bytes.length + "");
            toClient = new BufferedOutputStream(response.getOutputStream());
//            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            log.info("Response的字符集为：" + response.getCharacterEncoding());
            toClient.write(bytes);
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            log.error("下载文件异常", e);
        }
    }

    public <T> T postData(String url, Map<String, Object> params, Class<T> clazz) {
        //创建post请求对象
        HttpPost httppost = new HttpPost(url);
        // 获取到httpclient客户端
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //创建参数集合
            List<BasicNameValuePair> list = new ArrayList<>();
            // 设置请求的一些配置设置，主要设置请求超时，连接超时等参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(200000).setConnectionRequestTimeout(200000).setSocketTimeout(200000)
                    .build();
            httppost.setConfig(requestConfig);
            /**
             生产Cookie
             **/
            httppost.setHeader("Cookie","xxxxxx");
            //添加参数
            httppost.setEntity(new StringEntity(JSON.toJSONString(params), ContentType.create("application/json", "utf-8")));
            // 请求结果
            String resultString = "";
            //启动执行请求，并获得返回值
            CloseableHttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 获取请求响应结果
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // 将响应内容转换为指定编码的字符串
                    resultString = EntityUtils.toString(entity);
                    log.info("Response content:{}", resultString);
                    return JSON.parseObject(resultString, clazz);
                }
            } else {
                System.out.println("请求失败！");
                return null;
            }
        } catch (Exception e) {
            log.error("POST请求异常", e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error("关闭httpclient异常", e);
            }
        }
        return null;
    }

    public <T> T client(String url, String apiREQ, String apiRSP, Object contentMap, Map mapHead, Class<T> clazz) {
        String result = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            //设置请求天擎的请求体
            Map map = new HashMap<>();
            Map headMap = new HashMap<>();
            Map bodyMap = new HashMap<>();
            //设置报文体（业务数据），key为API名称每个大写字母前加_，且所有字母改为大写
            bodyMap.put(apiREQ, contentMap);
            //设置报文附加信息
            //将报文头体附加信息 封装到请求体中
            map.put("UNI_BSS_HEAD", headMap);
            map.put("UNI_BSS_BODY", bodyMap);

            //循环增加header
            Iterator headerIterator = mapHead.entrySet().iterator();
            while (headerIterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) headerIterator.next();
                post.addHeader(elem.getKey(), elem.getValue());
            }
            //添加请求体
            if (map != null && map.size() > 0) {
                StringEntity requestentity = new StringEntity(JSON.toJSONString(map), StandardCharsets.UTF_8);
                requestentity.setContentType(ContentType.APPLICATION_JSON.toString());
                post.setEntity(requestentity);
            }
            log.error("与天擎对接日志" + apiREQ + JSON.toJSONString(map));
            //发送请求并接收返回数据
            response = httpClient.execute(post);

            log.info("与天擎对接响应状态码"
                    + response.getStatusLine().getStatusCode() + "  "
                    + response.getStatusLine().getReasonPhrase() + "    "
                    + response.getStatusLine().getProtocolVersion());

            if (response == null || response.getStatusLine().getStatusCode() != 200) {
                log.info("与天擎对接响应异常response:{}", response);
                throw new Exception("与天擎对接响应异常:" + response.getStatusLine().getReasonPhrase() + "-" +
                        response.getStatusLine().getStatusCode());
            }
            //获取response的body部分
            HttpEntity entity = response.getEntity();
            //读取reponse的body部分并转化成字符串
            result = EntityUtils.toString(entity);

            log.info("与天擎对接响应日志" + apiRSP + result);
            return JSON.parseObject(result, clazz);
        } catch (Exception e) {
            log.error("请求失败", e);
            return null;
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
