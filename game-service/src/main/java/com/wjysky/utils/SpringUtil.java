package com.wjysky.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SpringUtil
 * @Description: spring框架辅助工具类
 * @Author: 王俊元（wangjunyuan@newhow.com.cn）
 * @Date: 2019-07-11 14:32
 * @Version 1.0
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    protected static Logger logger = LoggerFactory.getLogger(SpringUtil.class);

    private static ApplicationContext applicationContext = null;

//    public static void setApplicationContext(ApplicationContext applicationContext){
//        if(SpringUtil.applicationContext == null){
//            SpringUtil.applicationContext  = applicationContext;
//            String[] array = SpringUtil.applicationContext.getBeanDefinitionNames();
//            for (String name : array) {
//                logger.info("-----------------------" + name);
//            }
//        }
//    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null){
            SpringUtil.applicationContext = applicationContext;
//            String[] array = SpringUtil.applicationContext.getBeanDefinitionNames();
//            for (String name : array) {
//                logger.info("-----------------------" + name);
//            }
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}
