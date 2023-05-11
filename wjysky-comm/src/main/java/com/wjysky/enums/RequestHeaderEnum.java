package com.wjysky.enums;

import lombok.Getter;

/**
 * RequestHeaderEnum
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-05-11 10:48:29
 * @apiNote 请求头参数枚举类
 */
@Getter
public enum RequestHeaderEnum {
    /**
     * 请求唯一标识，与istio x-request-id无关
     */
    REQUEST_ID("request-id"),
    /**
     * 访问令牌,合同系统授权中心颁发的token
     */
    ACCESS_TOKEN("access-token"),
    /**
     * 终端用户，可以作为恢度测试或线上AB测试用户标识，
     * 比如用户账号等
     */
    END_USER("end-user"),
    /**
     * 访问来源系统，比如请求来自前端等或内部系统间的调用，
     * 每个系统有一个唯一名称
     */
    FROM_SYSTEM("from-system");
    private final String headerKey;

    RequestHeaderEnum(String headerKey){
        this.headerKey=headerKey;
    }
}
