package com.wjysky.components.authorization.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * PermissionAdvice
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-05-11 11:14:43
 * @apiNote 权限认证切面
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class PermissionAdvice implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("==================================================");
    }

    @Pointcut("@annotation(com.wjysky.components.authorization.annotation.PermissionAnnotation) " +
            "|| @within(com.wjysky.components.authorization.annotation.PermissionAnnotation)")
    public void permissionCheck() {
        //Do nothing by point cut
    }

    @Before("permissionCheck()")
    public void permissionCheck(JoinPoint joinPoint) {
        log.info("--------------------------------------------------");
    }
}
