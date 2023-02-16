package com.wjysky.framework.dao.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 数据访问配置类
 *
 * @author duanzhijun24470@talkweb.com.cn
 * @date 2022/04/28
 * @apiNote
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.wjysky.mapper.**"})
public class DaoConfig {

}
