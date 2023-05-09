package com.wjysky.config.resttemplat;


import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @ClassName : RestTemplateConfiguration
 * @Description : TODO
 * @company : 杭州远昊科技有限公司
 * @Author : 王俊元（wangjunyuan@newhow.com.cn）
 * @Date: 2020-09-21 14:23
 */
@Configuration
@ConditionalOnClass(value = {RestTemplate.class, HttpClient.class})
public class RestTemplateConfiguration {

//    @Value("${remote.maxTotalConnect}")
//    private int maxTotalConnect; //连接池的最大连接数默认为0
//    @Value("${remote.maxConnectPerRoute}")
//    private int maxConnectPerRoute; //单个主机的最大连接数
//    @Value("${remote.connectTimeout}")
//    private int connectTimeout; //连接超时默认2s
//    @Value("${remote.readTimeout}")
//    private int readTimeout; //读取超时默认30s

    public RestTemplateConfiguration() {
    }

    private ClientHttpRequestFactory createFactory()throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        SSLContextBuilder sslContextBuilder = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//                    信任所有站点 直接返回true
                return true;
            }
        });
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);

        // 支持HTTP、HTTPS
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslConnectionSocketFactory)
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(100);
        connectionManager.setValidateAfterInactivity(2000);
        RequestConfig requestConfig = RequestConfig.custom()
                // 服务器返回数据(response)的时间，超时抛出read timeout
                .setSocketTimeout(65000)
                // 连接上服务器(握手成功)的时间，超时抛出connect timeout
                .setConnectTimeout(5000)
                // 从连接池中获取连接的超时时间，超时抛出ConnectionPoolTimeoutException
                .setConnectionRequestTimeout(1000)
                .build();
//        if (this.maxTotalConnect <= 0) {
//            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//            factory.setConnectTimeout(5000);
//            factory.setReadTimeout(5000);
//            return factory;
//        }
        HttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(sslConnectionSocketFactory).setDefaultRequestConfig(requestConfig).setConnectionManager(connectionManager).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                httpClient);
        //factory.setConnectTimeout(5000);
       // factory.setReadTimeout(5000);
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = null;


        try {
            restTemplate = new RestTemplate(this.createFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();

        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (StringHttpMessageConverter.class == item.getClass()) {
                converterTarget = item;
                break;
            }
        }
        if (null != converterTarget) {
            converterList.remove(converterTarget);
        }
        converterList.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        converterList.add(new FastJsonHttpMessageConverter4());
        return restTemplate;
    }
}
