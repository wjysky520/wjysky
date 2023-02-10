package com.wjysky.pojo.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName : playerInfoBO
 * @Description : 玩家信息实体类
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-09-06 22:56:44
 */
@Getter
@Setter
public class PlayerInfoBO {

    private String userId; // 用户ID

    private String username; // 用户名称

    private String order; // 顺序
}