package com.wjysky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wjysky.dao.SysCfgMapper;
import com.wjysky.entity.db.SysCfg;
import com.wjysky.entity.query.SysCfgQuery;
import com.wjysky.service.ISysService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : SysServiceImpl
 * @Description : TODO
 * @Author : 王俊元(www.wjysky.com)
 * @Date : 2022-10-14 15:28:00
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class SysServiceImpl implements ISysService {

    private final SysCfgMapper sysCfgMapper;

    @Override
    public List<SysCfg> getSysCfgList(SysCfgQuery query) {
        LambdaQueryWrapper<SysCfg> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysCfg::getDeleted, query.getDeleted());
        return sysCfgMapper.selectList(wrapper);
    }
}
