package com.wjysky.feign.service;

import com.wjysky.feign.hystrix.TestHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName : TestService
 * @Description : TODO
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-08-31 21:02:27
 */
@FeignClient(value = "game-service", fallback = TestHystrix.class)
public interface ITestService {

    @RequestMapping(value = "/test/info", method = RequestMethod.POST)
    String query(@RequestParam(value = "msg") String msg);
}