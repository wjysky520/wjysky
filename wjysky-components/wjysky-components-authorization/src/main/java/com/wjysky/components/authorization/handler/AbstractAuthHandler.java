package com.wjysky.components.authorization.handler;

import javax.servlet.http.HttpServletRequest;

/**
 * AbstractAuthHandler
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-05-11 10:50:22
 * @apiNote 权限认证抽象类
 */
public abstract class AbstractAuthHandler {

    public abstract void handle(HttpServletRequest httpRequest);
}
