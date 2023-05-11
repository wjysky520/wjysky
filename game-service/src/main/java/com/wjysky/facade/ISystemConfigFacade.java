package com.wjysky.facade;

import com.wjysky.pojo.db.SystemConfig;

import java.util.List;

/**
 * ISystemConfigFacade
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-02-16 18:25:32
 * @apiNote
 */
public interface ISystemConfigFacade {

    public List<SystemConfig> getAllSystemConfig();
}
