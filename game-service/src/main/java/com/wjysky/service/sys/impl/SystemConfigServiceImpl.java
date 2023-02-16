package com.wjysky.service.sys.impl;

import com.wjysky.entity.db.SystemConfig;
import com.wjysky.framework.dao.base.service.impl.WjyMybatisServiceImpl;
import com.wjysky.mapper.SystemConfigMapper;
import com.wjysky.service.sys.ISystemConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * SystemConfigServiceImpl
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-02-16 18:19:40
 * @apiNote
 */
@Slf4j
@Component(value = "systemConfigService")
@RequiredArgsConstructor
public class SystemConfigServiceImpl extends WjyMybatisServiceImpl<SystemConfigMapper, SystemConfig> implements ISystemConfigService {
}
