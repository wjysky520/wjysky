package com.wjysky.feign.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : TestController
 * @Description : TODO
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-08-31 21:48:52
 */
@RestController
@RequestMapping(value = "/test")
@Slf4j
public class TestController {



    @RequestMapping(value = "/info")
    public String info(@RequestParam(value = "msg") String msg) {
        log.info("收到了消息：" + msg);
        return "welcome to demo-springboot: " + msg;
    }
}