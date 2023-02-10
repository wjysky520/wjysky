package com.wjysky.entity.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * @ClassName : baseDO
 * @Description : 系统配置表
 * @Author : 王俊元(www.wjysky.com)
 * @Date : 2022-10-13 17:41:16
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseDO {

    private Long id;
}
