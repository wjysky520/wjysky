package com.wjysky.service;

import com.wjysky.entity.db.SysCfg;
import com.wjysky.entity.query.SysCfgQuery;

import java.util.List;

/**
 * @ClassName : ITestService
 * @Description : TODO
 * @Author : 王俊元(www.wjysky.com)
 * @Date : 2022-10-14 15:28:00
 */
public interface ISysService {

    public List<SysCfg> getSysCfgList(SysCfgQuery query);
}
