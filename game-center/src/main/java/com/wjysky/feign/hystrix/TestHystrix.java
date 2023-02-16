package com.wjysky.feign.hystrix;

import com.wjysky.entity.DataApi;
import com.wjysky.entity.db.SystemConfig;
import com.wjysky.feign.service.ITestService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : TestHystrix
 * @Description : TODO
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-08-31 21:46:21
 */
@Service("testHystrix")
public class TestHystrix implements ITestService {

    @Override
    public DataApi<List<SystemConfig>> query(String msg) {
        return DataApi.generateExceptionMsg("请求已熔断");
    }
}