package com.wjysky.components.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * PermissionAnnotation
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-05-11 11:12:20
 * @apiNote 权限认证注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionAnnotation {
    String value() default "";//这个是存储权限的变量
}
