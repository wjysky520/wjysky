package com.wjysky.feign.service;

import com.wjysky.pojo.DataApi;
import com.wjysky.pojo.db.SystemConfig;
import com.wjysky.feign.hystrix.TestHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName : TestService
 * @Description : TODO
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-08-31 21:02:27
 */
@FeignClient(value = "game-service", fallback = TestHystrix.class)
public interface ITestService {

    @RequestMapping(value = "/game-service/test/info", method = RequestMethod.POST)
    DataApi<List<SystemConfig>> query(@RequestParam(value = "msg") String msg);
}