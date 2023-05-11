package com.wjysky.feign.controller;

import com.wjysky.pojo.DataApi;
import com.wjysky.pojo.db.SystemConfig;
import com.wjysky.facade.ISystemConfigFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName : TestController
 * @Description : TODO
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-08-31 21:48:52
 */
@RestController
@RequestMapping(value = "/test")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final ISystemConfigFacade systemConfigFacade;

    @RequestMapping(value = "/info")
    public DataApi<List<SystemConfig>> info(@RequestParam(value = "msg") String msg) {
        log.info("收到了消息：" + msg);
        return DataApi.generateSuccessMsg(systemConfigFacade.getAllSystemConfig());
    }
}