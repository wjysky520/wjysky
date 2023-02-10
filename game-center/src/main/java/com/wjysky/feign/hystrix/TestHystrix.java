package com.wjysky.feign.hystrix;

import com.wjysky.feign.service.ITestService;
import org.springframework.stereotype.Service;

/**
 * @ClassName : TestHystrix
 * @Description : TODO
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-08-31 21:46:21
 */
@Service("testHystrix")
public class TestHystrix implements ITestService {

    @Override
    public String query(String msg) {
        return "error";
    }
}