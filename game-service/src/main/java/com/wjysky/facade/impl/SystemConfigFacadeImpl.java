package com.wjysky.facade.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wjysky.entity.db.SystemConfig;
import com.wjysky.facade.ISystemConfigFacade;
import com.wjysky.service.sys.ISystemConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SystemConfigFacadeImpl
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-02-16 18:26:01
 * @apiNote
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SystemConfigFacadeImpl implements ISystemConfigFacade {

    private final ISystemConfigService systemConfigService;

    @Override
    public List<SystemConfig> getAllSystemConfig() {
        LambdaQueryWrapper<SystemConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SystemConfig::getDeleted, 0);
        List<SystemConfig> dataList = systemConfigService.list(wrapper);
        return dataList;
    }
}
